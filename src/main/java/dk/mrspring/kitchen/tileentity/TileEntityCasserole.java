package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.tileentity.casserole.Casserole;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Konrad on 27-01-2015.
 */
public class TileEntityCasserole extends TileEntity
{
    Casserole casserole;

    public TileEntityCasserole()
    {
        casserole = new Casserole();
    }

    public Casserole getCasserole()
    {
        return casserole;
    }

    public void onRightClicked(ItemStack clicked)
    {
        if (clicked != null)
            casserole.addItem(clicked);
    }

    public ItemStack[] removeTopLayer()
    {
        return casserole.removeTopLayer();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagCompound casseroleCompound = compound.getCompoundTag("Casserole");
        casserole = new Casserole();
        casserole.readFromNBT(casseroleCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound casseroleCompound = new NBTTagCompound();
        casserole.writeToNBT(casseroleCompound);
        compound.setTag("Casserole", casseroleCompound);
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
