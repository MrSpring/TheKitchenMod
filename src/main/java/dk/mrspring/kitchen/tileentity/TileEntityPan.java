package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityPan extends TileEntity
{
    Ingredient ingredient = Ingredient.getIngredient("empty");
    int cookTime = 0;
    boolean isFunctional = true, firstRun = true;
    final int FINISH_TIME = 300;

    /**
     * Handles the block being right-clicked. Adds the item if the pan is
     * empty, finishes the current ingredient if it's ready and clicked
     * is null.
     *
     * @param clicked The ItemStack the block is being right-clicked with.
     * @return Returns true if clicked's stack-size should be subtracted.
     */
    public boolean rightClicked(ItemStack clicked)
    {
        if (clicked != null)
        {
            if (isFinished() && clicked.getItem() == KitchenItems.jam_jar && this.ingredient.isJam() && clicked.getItemDamage() == 0)
            {
                this.finishItem(clicked);
                return true;
            } else if (this.ingredient == Ingredient.getIngredient("empty"))
            {
                return this.setIngredient(clicked);
            }
        } else
        {
            if (isFinished())
            {
                this.finishItem(null);
                return false;
            }
        }

        return false;
    }

    private void finishItem(ItemStack clicked)
    {
        if (isFinished())
        {
            ItemStack result = null;
            if (clicked != null && clicked.getItem() == KitchenItems.jam_jar && this.ingredient.isJam())
            {
                Jam resultJam = this.ingredient.getJamResult();
                result = Kitchen.getJamJarItemStack(resultJam, 6);
            } else if (!this.ingredient.isJam())
            {
                result = this.ingredient.getItemResult();
            }
            if (result != null)
            {
                this.cookTime = 0;
                this.ingredient = Ingredient.getIngredient("empty");


                worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, result));
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    /**
     * Sets the pan's ingredient to whatever the item links to.
     *
     * @param clicked The item the pan is being clicked with.
     * @return Returns true if the ingredient was set successfully.
     */
    private boolean setIngredient(ItemStack clicked)
    {
        if (this.ingredient == Ingredient.getIngredient("empty") && this.cookTime == 0)
        {
            Ingredient ingredientFromItem = KitchenItems.valueOf(clicked.getItem());
            if (ingredientFromItem != Ingredient.getIngredient("empty"))
            {
                this.ingredient = ingredientFromItem;
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public void updateEntity()
    {
        if (this.firstRun)
        {
            this.checkIsFunctional();
            this.firstRun = false;
        }

        if (this.getIngredient() != Ingredient.getIngredient("empty") && isFunctional)
        {
            if (this.cookTime <= FINISH_TIME)
                this.cookTime++;
        } else this.cookTime = 0;
    }

    public void checkIsFunctional()
    {
        if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == KitchenBlocks.oven)
            this.makeFunctional();
        else
            this.makeNonFunctional();
    }

    public void makeNonFunctional()
    {
        this.isFunctional = false;
    }

    public void makeFunctional()
    {
        this.isFunctional = true;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public Ingredient getIngredient()
    {
        return ingredient;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("Ingredient", this.getIngredient().getName());
        compound.setInteger("CookTime", this.getCookTime());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        String string = compound.getString("Ingredient");
        try
        {
            this.ingredient = Ingredient.getIngredient(string);
        } catch (IllegalArgumentException e)
        {
            ModLogger.print(ModLogger.WARNING, "There was a problem loading pan @ X:" + this.xCoord + ", Y:" + this.yCoord + ", Z:" + this.zCoord + ", the ingredient " + string + " could not be found!");
            e.printStackTrace();
        }
        this.cookTime = compound.getInteger("CookTime");
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

    public int getAngle()
    {
        return getWorldObj().getBlockMetadata(xCoord, yCoord - 1, zCoord);
    }

    public boolean isOnOven()
    {
        return getWorldObj().getBlock(xCoord, yCoord - 1, zCoord) == KitchenBlocks.oven;
    }

    public boolean isFinished()
    {
        return getCookTime() >= FINISH_TIME;
    }
}
