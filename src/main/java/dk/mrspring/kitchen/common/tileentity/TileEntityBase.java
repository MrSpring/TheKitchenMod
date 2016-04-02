package dk.mrspring.kitchen.common.tileentity;

import dk.mrspring.kitchen.api.ISoundPlayer;
import dk.mrspring.kitchen.api.ISpawner;
import dk.mrspring.kitchen.common.util.WorldUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import static dk.mrspring.kitchen.common.util.WorldUtils.sound;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityBase extends TileEntity implements ISoundPlayer, ISpawner
{
    boolean hasSpecialClient = true;
    String clientSuffix = "-c";

    @Override
    public EntityItem spawnItem(ItemStack stack)
    {
        return WorldUtils.spawnItemAt(stack, worldObj, xCoord, yCoord, zCoord);
    }

    public void markForUpdate()
    {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void playSound(String sound, float f1, float f2)
    {
        sound(worldObj, sound, 0.5D + xCoord, 0.5D + yCoord, 0.5D + zCoord, f1, f2);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        super.writeToNBT(compound);
        this.writeDataToClient(compound);
        if (hasSpecialClient) compound.setString("id", getClientID(compound.getString("id")));
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }

    public String getClientID(String commonID)
    {
        return commonID + clientSuffix;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.writeDataToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.readDataFromNBT(compound);
    }

    public void writeDataToClient(NBTTagCompound compound)
    {
        this.writeToNBT(compound);
        this.writeDataToNBT(compound);
    }

    public abstract void writeDataToNBT(NBTTagCompound compound);

    public abstract void readDataFromNBT(NBTTagCompound compound);
}
