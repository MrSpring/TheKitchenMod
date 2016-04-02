package dk.mrspring.kitchen.client.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityClient extends TileEntity
{
    @Override
    public Packet getDescriptionPacket()
    {
        return super.getDescriptionPacket();
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.readDataFromNBT(compound);
    }

    public abstract void readDataFromNBT(NBTTagCompound compound);
}
