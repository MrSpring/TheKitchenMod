package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class TileEntityWaffleIron extends TileEntityTimeable // TODO: Rewrite using IWaffleIron and IDough
{
    static Map<String, ItemStack[]> recipes;

    public static void load()
    {
        recipes = new HashMap<String, ItemStack[]>();
        recipes.put("waffle_dough", new ItemStack[]{new ItemStack(KitchenItems.waffle, 2), new ItemStack(KitchenItems.burnt_waffle, 2)});
    }

    public static void registerWaffleRecipe(String mixName, ItemStack result, ItemStack burntResult)
    {
        recipes.put(mixName, new ItemStack[]{result, burntResult});
    }

    boolean isOpen = false;
    float lidAngle = 0;
    int cookTime = 0;
    public String dough = "";

    public float getLidAngle()
    {
        return lidAngle;
    }

    public void setLidAngle(float lidAngle)
    {
        this.lidAngle = lidAngle;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public boolean addDough(String dough)
    {
        if (this.isOpen() && this.dough.isEmpty() && recipes.containsKey(dough))
        {
            this.dough = dough;
            return true;
        } else return false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.isOpen()) // TODO: Only do on Client
        {
            if (this.lidAngle + 0.1F < 1.0)
                this.lidAngle += 0.1F;
        } else
        {
            if (this.lidAngle - 0.1F > 0.0)
                this.lidAngle -= 0.1F;

            if (!this.dough.isEmpty())
            {
                if (cookTime < 610)
                    this.cookTime++;
            }
        }
    }

    public int getWaffleState()
    {
        if (!this.dough.isEmpty())
        {
            if (this.cookTime < 400)
                return 1;
            else if (this.cookTime > 400 && this.cookTime < 600)
                return 2;
            else if (this.cookTime > 600)
                return 3;
            else return 0;
        } else return 0;
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
        this.dough = compound.getString("Dough");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("CookTime", this.cookTime);
        compound.setBoolean("IsOpen", this.isOpen);
        compound.setString("Dough", this.dough);
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
                result = recipes.get(this.dough)[1].copy();
            else result = recipes.get(this.dough)[0].copy();

            this.dough = "";
            this.cookTime = 0;

            worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, result));
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return false;
    }

    @Override
    public int getTime()
    {
        return this.getCookTime();
    }

    @Override
    public int getDoneTime()
    {
        return 400;
    }
}
