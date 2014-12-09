package dk.mrspring.kitchen.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class TileEntityWaffleIron extends TileEntity
{
    boolean isOpen = false;
    float lidAngle = 0;

    public float getLidAngle()
    {
        return lidAngle;
    }

    public void setLidAngle(float lidAngle)
    {
        this.lidAngle = lidAngle;
    }

    public void printStatus()
    {
        System.out.println("Updating!");
        System.out.println("isOpen = " + isOpen);
        System.out.println("lidAngle = " + lidAngle);
    }

    @Override
    public void updateEntity()
    {
        if (this.isOpen())
        {
            if (this.lidAngle + 0.1F < 1.0)
                this.lidAngle += 0.1F;
        } else
        {
            if (this.lidAngle - 0.1F > 0.0)
                this.lidAngle -= 0.1F;
        }
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void setOpen(boolean isOpen)
    {
        this.isOpen = isOpen;
    }

    public void toggleOpen()
    {
        this.isOpen = !isOpen;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.isOpen = compound.getBoolean("IsOpen");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("IsOpen", this.isOpen);
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
