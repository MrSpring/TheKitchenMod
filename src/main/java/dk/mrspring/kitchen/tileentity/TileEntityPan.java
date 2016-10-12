package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.pan.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityPan extends TileEntityBase
{
    Ingredient ingredient = Ingredient.EMPTY;
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
        if (ingredient != Ingredient.EMPTY)
        {
            spawn(finishItem(clicked));
            return true;
        } else if (clicked != null)
        {
            return this.setIngredient(clicked);
        }
        return false;
    }

    private ItemStack[] finishItem(ItemStack clicked)
    {
        if (ingredient.canRemove(this, clicked))
        {
            mark();
            ItemStack[] stacks = ingredient.onRemoved(this, clicked);
            ingredient = Ingredient.EMPTY;
            return stacks;
        } else return new ItemStack[0];
    }

    /**
     * Sets the pan's ingredient to whatever the item links to.
     *
     * @param clicked The item the pan is being clicked with.
     * @return Returns true if the ingredient was set successfully.
     */
    private boolean setIngredient(ItemStack clicked)
    {
        if (this.ingredient == Ingredient.EMPTY)
        {
            Ingredient ingredientFromItem = KitchenItems.valueOf(clicked.getItem());
            if (ingredientFromItem != Ingredient.EMPTY)
            {
                cookTime = 0;
                this.ingredient = ingredientFromItem;
                clicked.stackSize--;
                mark();
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity()
    {
        if (this.firstRun)
        {
            this.checkIsFunctional();
            this.firstRun = false;
        }

        if (this.getIngredient() != Ingredient.EMPTY && isFunctional)
        {
            if (this.cookTime <= FINISH_TIME)
                this.cookTime++;
        } else this.cookTime = 0;
    }

    public void checkIsFunctional()
    {
        this.setFunctional(isOnOven());
    }

    public void setFunctional(boolean functional)
    {
        this.isFunctional = functional;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public Ingredient getIngredient()
    {
        return ingredient;
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

    @Override
    public int getNBTLevel()
    {
        return 1;
    }

    @Override
    public void readDataFromOldNBT(int oldLevel, int newLevel, NBTTagCompound compound)
    {
        warnNBTLevelChange(oldLevel, newLevel);
        readDataFromNBT(compound);
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        compound.setString("Ingredient", this.getIngredient().getName());
        compound.setInteger("CookTime", this.getCookTime());
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        String string = compound.getString("Ingredient");
        try
        {
            this.ingredient = Ingredient.getIngredient(string);
        } catch (IllegalArgumentException e)
        {
            ModLogger.print(ModLogger.WARNING, "There was a problem loading pan at X:" + this.xCoord + ", Y:" + this.yCoord + ", Z:" + this.zCoord + ", the ingredient " + string + " could not be found!");
            e.printStackTrace();
        }
        this.cookTime = compound.getInteger("CookTime");
    }
}
