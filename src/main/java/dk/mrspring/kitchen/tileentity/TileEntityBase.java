package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.ModLogger;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created on 01-06-2016 for TheKitchenMod.
 */
public abstract class TileEntityBase extends TileEntity
{
    public static final String NBT_LEVEL = "NBTLevel";
    public static final String NBT_DATA = "NBTData";

    public abstract void readDataFromNBT(NBTTagCompound compound);

    public abstract void writeDataToNBT(NBTTagCompound compound);

    public abstract int getNBTLevel();

    public abstract void readDataFromOldNBT(int oldLevel, int newLevel, NBTTagCompound compound);

    public void spawn(ItemStack... stacks)
    {
        spawn(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, stacks);
    }

    public void spawn(double x, double y, double z, ItemStack... stacks)
    {
        for (ItemStack stack : stacks)
            if (stack != null && stack.stackSize > 0)
                getWorldObj().spawnEntityInWorld(
                        new EntityItem(getWorldObj(), x, y, z, stack)
                );
    }

    public void warnNBTLevelChange(int oldLevel, int newLevel)
    {
        ModLogger.print(ModLogger.WARNING, "Reading from unknown NBT level: " + oldLevel + "! Things might go wrong...");
    }

    public void mark()
    {
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (!compound.hasKey(NBT_LEVEL, 3)) this.readDataFromNBT(compound);
        else
        {
            int compoundLevel = compound.getInteger(NBT_LEVEL), currentLevel = getNBTLevel();
            if (compoundLevel != currentLevel)
                this.readDataFromOldNBT(compoundLevel, currentLevel, compound.getCompoundTag(NBT_DATA));
            else this.readDataFromNBT(compound.getCompoundTag(NBT_DATA));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger(NBT_LEVEL, getNBTLevel());
        NBTTagCompound data = new NBTTagCompound();
        this.writeDataToNBT(data);
        compound.setTag(NBT_DATA, data);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }
}
