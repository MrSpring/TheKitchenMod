package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.pot.Jam;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class TileEntityJamJar extends TileEntity
{
	private Jam jam;
	private int usesLeft;

	public TileEntityJamJar()
	{
		this(Jam.EMPTY, 0);
	}

	public TileEntityJamJar(Jam jam, int left)
	{
		super();

		this.jam = jam;
		this.usesLeft = left;
	}

	public static TileEntityJamJar create(ItemStack jarItem)
	{
		Jam jam = Jam.EMPTY;
		int uses = 0;

		if (jarItem.getTagCompound() != null)
		{
			NBTTagCompound compound = jarItem.getTagCompound();
			if (compound.hasKey("JamInfo"))
			{
				NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
				jam = Jam.valueOf(jamInfo.getString("JamType"));
				uses = jamInfo.getInteger("UsesLeft");
			}
		}

		return create(jam, uses);
	}

	public Jam getJam()
	{
		return jam;
	}

	public int getUsesLeft()
	{
		return usesLeft;
	}

	public static TileEntityJamJar create(Jam jam, int usesLeft)
	{
		return new TileEntityJamJar(jam, usesLeft);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		System.out.println("Reading from NBT");

		super.readFromNBT(compound);

		if (compound != null)
		{
			System.out.println("Compound is not null");
			NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");

			if (jamInfo != null)
			{
				System.out.println("JamInfo is not null, jam is: " + jamInfo.getString("JamType"));
				this.jam = Jam.valueOf(jamInfo.getString("JamType").toUpperCase());
				this.usesLeft = jamInfo.getInteger("UsesLeft");
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		System.out.println("Writing to NBT");

		super.writeToNBT(compound);

		NBTTagCompound jamInfo = new NBTTagCompound();

		jamInfo.setString("JamType", this.jam.name().toLowerCase());
		jamInfo.setInteger("UsesLeft", this.usesLeft);

		compound.setTag("JamInfo", jamInfo);
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