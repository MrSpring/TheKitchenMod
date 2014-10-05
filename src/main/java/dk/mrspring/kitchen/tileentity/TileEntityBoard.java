package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.api.event.IOnAddedToBoardEvent;
import dk.mrspring.kitchen.api.event.IOnBoardRightClickedEvent;
import dk.mrspring.kitchen.api.event.ITopItemEvent;
import dk.mrspring.kitchen.combo.SandwichCombo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TileEntityBoard extends TileEntity
{
    private List<ItemStack> layers = new ArrayList<ItemStack>();
    private NBTTagCompound specialInfo = new NBTTagCompound();

    /**
     * @param toAdd The ItemStack trying to get added to the Board.
     * @return Returns true if the ItemStack was added, and should therefore decrement it's stackSize, false if not.
     */
    public boolean rightClicked(ItemStack toAdd, boolean callEvents)
    {
        if (toAdd != null)
        {
            //System.out.println("To Add is not null! Ir is: " + toAdd.getDisplayName());

            IOnAddedToBoardEvent onAddedToBoardEvent = (IOnAddedToBoardEvent) BoardEventRegistry.getOnAddedToBoardEventFor(toAdd.getItem());
            ITopItemEvent topItemEvent = (ITopItemEvent) BoardEventRegistry.getTopItemEventFor(this.getTopItem());

            //System.out.println(callEvents);
            if (!callEvents)
            {
                //System.out.println("Getting default events because callEvents is false!");
                onAddedToBoardEvent = BoardEventRegistry.getDefaultOnAddedToBoardEvent();
                topItemEvent = BoardEventRegistry.getDefaultTopItemEvent();
            }

            NBTTagCompound compoundCopy = this.getSpecialInfo();

            if (ModConfig.getSandwichConfig().canAdd(toAdd) && topItemEvent.canAddItemOnTop(this.getLayers(), toAdd, compoundCopy) && onAddedToBoardEvent.canAdd(this.getLayers(), toAdd, compoundCopy))
            {
                ItemStack temp = toAdd.copy();
                temp.stackSize = 1;
                this.layers.add(temp);
                this.setSpecialInfo(new NBTTagCompound());
                compoundCopy = this.getSpecialInfo();
                onAddedToBoardEvent.onAdded(this.getLayers(), temp, compoundCopy);
                this.setSpecialInfo(compoundCopy);
                return true;
            } else
            {
                IOnBoardRightClickedEvent onBoardRightClickedEvent = (IOnBoardRightClickedEvent) BoardEventRegistry.getOnBoardRightClickedEventFor(toAdd.getItem());
                if (!callEvents)
                    onBoardRightClickedEvent = BoardEventRegistry.getDefaultOnBoardRightClickedEvent();
                compoundCopy = this.getSpecialInfo();
                onBoardRightClickedEvent.onRightClicked(this.getLayers(), toAdd, compoundCopy);
                this.setSpecialInfo(compoundCopy);
                return false;
            }
        }
        return false;
    }

    public ItemStack getTopItem()
    {
        if (this.getLayers().size() > 0)
            return this.getLayers().get(this.getLayers().size() - 1);
        else return null;
    }

    public NBTTagCompound getSpecialInfo()
    {
        if (specialInfo == null)
            this.specialInfo = new NBTTagCompound();

        return specialInfo;
    }

    public void setSpecialInfo(NBTTagCompound specialInfo)
    {
        this.specialInfo = specialInfo;
    }

    public ItemStack removeTopItem()
    {
        if (this.layers.size() > 0)
        {
            ITopItemEvent topItemEvent = (ITopItemEvent) BoardEventRegistry.getTopItemEventFor(this.layers.get(this.layers.size() - 1));
            return topItemEvent.getDroppeditem(this.layers, this.layers.get(this.layers.size()-1), this.getSpecialInfo());
        } else return null;
    }

    public List<ItemStack> getLayers()
    {
        return layers;
    }

    public ItemStack finishSandwich()
    {
        if (!(ModConfig.getSandwichConfig().isBread(this.layers.get(0)) && ModConfig.getSandwichConfig().isBread(this.layers.get(this.layers.size() - 1))) || this.layers.size() < 2)
            return null;

        NBTTagList layersList = new NBTTagList();
        ItemStack sandwich = GameRegistry.findItemStack(ModInfo.modid, "sandwich", 1);

        for (ItemStack layer : this.layers)
        {
            NBTTagCompound layerCompound = new NBTTagCompound();
            layer.writeToNBT(layerCompound);
            layersList.appendTag(layerCompound);
        }

        sandwich.setTagInfo("SandwichLayers", layersList);


        NBTTagCompound comboCompound = new NBTTagCompound();
        byte combo = (byte) SandwichCombo.getComboID(sandwich);

        comboCompound.setByte("Id", combo);
        sandwich.setTagInfo("Combo", comboCompound);

        this.resetLayers();

        return sandwich;
    }

    public void resetLayers()
    {
        this.layers = new ArrayList<ItemStack>();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.resetLayers();
        NBTTagList list = compound.getTagList("Items", 10);

        System.out.println("Reading from NBT");

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound layerCompound = list.getCompoundTagAt(i);
            ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);

            System.out.println("Reading " + layer.getDisplayName() + " from NBT");

            this.rightClicked(layer, false);
        }

        this.setSpecialInfo(compound.getCompoundTag("SpecialInfo"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList list = new NBTTagList();
        System.out.println("Writing to NBT");

        for (ItemStack layer : this.layers)
        {
            if (layer != null)
            {
                System.out.println("Writing " + layer.getDisplayName() + " to NBT");
                NBTTagCompound layerCompound = new NBTTagCompound();
                layer.writeToNBT(layerCompound);
                list.appendTag(layerCompound);
            }
        }

        compound.setTag("Items", list);
        compound.setTag("SpecialInfo", this.getSpecialInfo());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

	/*private ItemStack[] layers = new ItemStack[10];
    private ItemStack lastRemoved;
	int layerIndex = 0;
	
	public void resetLayers()
	{
		this.layers = new ItemStack[10];
		this.lastRemoved = null;
		this.layerIndex = 0;
	}
	
	public boolean rightClicked(ItemSandwichable par1)
	{
		if (this.layerIndex + 1 <= 10)
		{
			this.layers[this.layerIndex] = new ItemStack(par1, 1, 0);
			this.layerIndex++;
			
			return true;
		}
		else
			return false;
	}
	
	public ItemStack[] getLayers()
	{
		return this.layers;
	}
	
	public boolean removeTopLayer()
	{
		if (layerIndex - 1 >= 0)
		{
			this.lastRemoved = this.layers[this.layerIndex - 1];
			this.layers[this.layerIndex - 1] = null;
			--this.layerIndex;
			return true;
		}
		else
			return false;
	}
	
	public ItemStack getLastRemoved()
	{
		return this.lastRemoved;
	}
	
	public boolean isAcceptableSandwich()
	{
		if (this.layers[0] != null)
			if (this.layers[0].getItem() instanceof ItemSandwichBread && this.layers[this.layerIndex - 1].getItem() instanceof ItemSandwichBread)
				return true;
			else
				return false;
		else
			return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		this.resetLayers();
		NBTTagList list = compound.getTagList("Items", 10);
		this.layers = new ItemStack[10];
		
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound layerCompound = list.getCompoundTagAt(i);
			
			this.rightClicked((ItemSandwichable) ItemStack.loadItemStackFromNBT(layerCompound).getItem());
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
		
		for (int i = 0; i < this.layers.length; ++i)
		{
			if (this.layers[i] != null)
			{
				NBTTagCompound layerCompound = new NBTTagCompound();
				this.layers[i].writeToNBT(layerCompound);
				list.appendTag(layerCompound);
			}
		}
		
		compound.setTag("Items", list);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.func_148857_g());
	}*/
}
