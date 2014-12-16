package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.api.event.IOnAddedToBoardEvent;
import dk.mrspring.kitchen.api.event.IOnBoardRightClickedEvent;
import dk.mrspring.kitchen.api.event.ITopItemEvent;
import dk.mrspring.kitchen.config.ComboConfig;
import dk.mrspring.kitchen.config.SandwichableConfig;
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
            IOnAddedToBoardEvent onAddedToBoardEvent = (IOnAddedToBoardEvent) BoardEventRegistry.getOnAddedToBoardEventFor(toAdd.getItem());
            ITopItemEvent topItemEvent = (ITopItemEvent) BoardEventRegistry.getTopItemEventFor(this.getTopItem());

            if (!callEvents)
            {
                onAddedToBoardEvent = BoardEventRegistry.getDefaultOnAddedToBoardEvent();
                topItemEvent = BoardEventRegistry.getDefaultTopItemEvent();
            }

            NBTTagCompound compoundCopy = this.getSpecialInfo();

            if (ModConfig.getSandwichConfig().canAdd(toAdd) && topItemEvent.canAddItemOnTop(this.getLayers(), toAdd, compoundCopy) && onAddedToBoardEvent.canAdd(this.getLayers(), toAdd, compoundCopy))
            {
                ItemStack temp = onAddedToBoardEvent.addedToBoard(this.getLayers(), toAdd, this.getSpecialInfo());
                temp.stackSize = 1;
                this.layers.add(temp);
                this.setSpecialInfo(new NBTTagCompound());
                compoundCopy = this.getSpecialInfo();
                onAddedToBoardEvent.onAdded(this.getLayers(), toAdd, compoundCopy);
                this.setSpecialInfo(compoundCopy);
                return onAddedToBoardEvent.decrementStackSize(this.getLayers(), toAdd, this.getSpecialInfo());
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
            ItemStack removed = this.layers.remove(this.layers.size() - 1);
            ITopItemEvent topItemEvent = (ITopItemEvent) BoardEventRegistry.getTopItemEventFor(removed);
            SandwichableConfig.SandwichableEntry itemEntry = ModConfig.getSandwichConfig().findEntry(removed);
            if (itemEntry.dropItem())
                return topItemEvent.getDroppeditem(this.layers, removed, this.getSpecialInfo());
        }
        return null;
    }

    public List<ItemStack> getLayers()
    {
        return layers;
    }

    public ItemStack finishSandwich()
    {
        if (this.layers.size()<1)
            return null;

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
        ComboConfig.SandwichCombo combo = ModConfig.getComboConfig().getComboMatching(sandwich);
        String comboName="none";

        if (combo!=null)
            comboName=combo.getUnlocalizedName();

        comboCompound.setString("ComboName", comboName);
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

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound layerCompound = list.getCompoundTagAt(i);
            ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);

            this.rightClicked(layer, false);
        }

        this.setSpecialInfo(compound.getCompoundTag("SpecialInfo"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList list = new NBTTagList();
        for (ItemStack layer : this.layers)
        {
            if (layer != null)
            {
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
}
