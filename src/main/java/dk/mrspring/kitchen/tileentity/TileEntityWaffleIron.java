package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
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
    int cookTime = 0;
    public boolean hasDough = false;

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

    public int getCookTime()
    {
        return cookTime;
    }

    public boolean addWaffleDough()
    {
        if (this.isOpen() && !this.hasDough)
        {
            this.hasDough = true;
            return true;
        } else return false;
    }

    @Override
    public void updateEntity()
    {
//        System.out.println("isOpen = " + isOpen);
//        System.out.println("cookTime = " + cookTime);
//        System.out.println("hasDough = " + hasDough);

        if (this.isOpen())
        {
            if (this.lidAngle + 0.1F < 1.0)
                this.lidAngle += 0.1F;
        } else
        {
            if (this.lidAngle - 0.1F > 0.0)
                this.lidAngle -= 0.1F;

            if (this.hasDough)
            {
                if (cookTime < 610)
                    this.cookTime++;
            }
        }
    }

    public int getWaffleState()
    {
        if (this.hasDough)
        {
            if (this.cookTime < 400)
                return 1;
            else if (this.cookTime > 400 && this.cookTime < 600)
                return 2;
            else if (this.cookTime > 600)
                return 3;
            else return 0;
        } else return 0;

//        if ()
//        if (this.cookTime < 400)
//            return 0;
//        else if (this.cookTime > 400 && this.cookTime < 600)
//            return 1;
//        else if (this.cookTime > 600)
//            return 2;
//        else return 0;
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

        this.cookTime = compound.getInteger("CookTime");
        this.isOpen = compound.getBoolean("IsOpen");
        this.hasDough = compound.getBoolean("HasDough");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("CookTime", this.cookTime);
        compound.setBoolean("IsOpen", this.isOpen);
        compound.setBoolean("HasDough", this.hasDough);
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

    public boolean finishWaffle()
    {
        if (this.isOpen() && this.cookTime > 400)
        {
            ItemStack result;

            if (this.cookTime > 600)
                result = new ItemStack(KitchenItems.burnt_waffle);
            else result = new ItemStack(KitchenItems.waffle);

            this.hasDough = false;
            this.cookTime = 0;

            worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, result));
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return false;
    }
}
