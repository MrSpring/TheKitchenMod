package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.item.ItemSandwichBread;
import dk.mrspring.kitchen.item.ItemSandwichable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityBoard extends TileEntity
{
	private ItemStack[] layers = new ItemStack[10];
	private ItemStack lastRemoved;
	int layerIndex = 0;
	
	public void resetLayers()
	{
		this.layers = new ItemStack[10];
		this.lastRemoved = null;
		this.layerIndex = 0;
	}
	
	public boolean addLayer(ItemSandwichable par1)
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
			
			this.addLayer((ItemSandwichable) ItemStack.loadItemStackFromNBT(layerCompound).getItem());
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
	}
}
