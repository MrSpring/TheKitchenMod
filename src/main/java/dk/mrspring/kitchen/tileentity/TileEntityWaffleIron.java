package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
    @SideOnly(Side.CLIENT)
    int lidAngle, lidDirection;
    int cookTime = 0;
    public String dough = "";

    public int getLidAngle()
    {
        return lidAngle;
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

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {// TODO: Optimise
            if (isOpen())
                if (lidAngle < 15)
                {
                    lidAngle++;
                    lidDirection = -1;
                } else lidDirection = 0;
            else if (lidAngle > 0)
            {
                lidAngle--;
                lidDirection = 1;
            } else
                lidDirection = 0;
        }
//            if (this.isOpen())
//                if (this.lidAngle < 10)
//                    this.lidAngle += 1F;
//                else if (this.lidAngle - 0.1F > 0.0)
//                    this.lidAngle -= 0.1F;


        if (!this.isOpen() && !this.dough.isEmpty())
            if (cookTime <= 600)
                this.cookTime++;
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

    @Override
    public float[] getTimerLocalPosition()
    {
        final float P = 0.0625F;
        float[][] poses = new float[][]{
                new float[]{P * 5.5F, P, 3 * P},
                new float[]{1 - P * 5, P, 2 * P},
                new float[]{1 - 3 * P, P, P * 5.5F},
                new float[]{1 - 2 * P, P, 1 - P * 5},
                new float[]{1F - P * 5.5F, P, 1 - 3 * P},
                new float[]{P * 5, P, 1 - 2 * P},
                new float[]{3 * P, P, 1F - P * 5.5F},
                new float[]{2 * P, P, P * 5}
        };
        int metadata = worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord);
        return metadata >= 0 && metadata < poses.length ? poses[metadata] : super.getTimerLocalPosition();
    }

    public int getLidDirection()
    {
        return lidDirection;
    }
}
