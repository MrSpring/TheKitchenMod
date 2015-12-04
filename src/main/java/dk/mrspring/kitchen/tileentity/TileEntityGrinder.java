package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.recipe.GrinderRecipe;
import dk.mrspring.kitchen.tileentity.grinder.GrateMouthHandler;
import dk.mrspring.kitchen.tileentity.grinder.PlateMouthHandler;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16-11-2015 for TheKitchenMod.
 */
public class TileEntityGrinder extends TileEntityBase
{
    public static final String INGREDIENT = "IngredientStack", MOUTH = "MouthStack", GRIND_COUNT = "GrindCount";

    public static final List<IMouthHandler> mouthHandlers = new ArrayList<IMouthHandler>();

    static
    {
        mouthHandlers.add(new PlateMouthHandler());
        mouthHandlers.add(new GrateMouthHandler());
    }

    public interface IMouthHandler
    {
        boolean isAcceptableMouth(ItemStack stack);
    }

    public ItemStack ingredient, mouth;
    public int grindCount;
    int maxGrindCount = 4;

    @SideOnly(Side.CLIENT)
    public int animGrindCount;
    @SideOnly(Side.CLIENT)
    public boolean animUsePartial;

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (worldObj.isRemote)
        {
            if (animGrindCount != grindCount)
                if (!animUsePartial)
                    animUsePartial = true;
                else
                {
                    animGrindCount = grindCount;
                    animUsePartial = false;
                }
            else animUsePartial = false;
        }
    }

    public boolean rightClick(ItemStack stack, boolean sneaking)
    {
        System.out.println("Clicked with: " + ItemUtils.name(stack) + ", grind: " + grindCount + ", sneaky: " + sneaking);
        if (stack == null)
        {
            if (sneaking) return this.updateGrindCount();
            else return this.dropFirst();
        } else return this.updateFirst(stack);
    }

    public boolean updateFirst(ItemStack stack)
    {
        if (mouth == null)
        {
            if (this.isMouth(stack))
            {
                this.mouth = stack.copy();
                this.mouth.stackSize = 1;
                System.out.println("Updated mouth");
                stack.stackSize--;
            } else
            {
                System.out.println("Is not mouth!");
                return false;
            }
        } else if (ingredient == null/* && this.isIngredient(stack)*/)
        {
            GrinderRecipe recipe = Kitchen.instance.grinderRecipes.getRecipeFor(stack, mouth);
            if (recipe == null) return false;
            System.out.println("Updated ingredient");
            this.ingredient = stack.copy();
            this.ingredient.stackSize = 1;
            recipe.reduceInputSize(stack);
            /*this.ingredient = stack.copy();
            this.ingredient.stackSize = 1;
            System.out.println("Updated ingredient");
            stack.stackSize--;*/
        } else
        {
            System.out.println("Updated nothing from...");
            return false;
        }
        return true;
    }

    public boolean isIngredient(ItemStack stack)
    {
        return Kitchen.instance.grinderRecipes.hasOutput(stack, mouth);
    }

    public boolean dropFirst()
    {
        if (ingredient != null)
        {
            if (ItemUtils.canDrop(ingredient))
                BlockContainerBase.spawnItemInWorld(ingredient, worldObj, xCoord, yCoord, zCoord);
            System.out.println("Dropped ingredient: " + ItemUtils.name(ingredient));
            this.ingredient = null;
        } else if (mouth != null)
        {
            BlockContainerBase.spawnItemInWorld(mouth, worldObj, xCoord, yCoord, zCoord);
            System.out.println("Dropped mouth: " + ItemUtils.name(ingredient));
            this.mouth = null;
        } else
        {
            System.out.println("Dropped nothing");
            return false;
        }
        this.grindCount = 0;
        return true;
    }


    public boolean isMouth(ItemStack stack)
    {
        for (IMouthHandler handler : mouthHandlers)
            if (handler.isAcceptableMouth(stack)) return true;
        return false;
    }

    public boolean updateGrindCount()
    {
        if (ingredient == null || mouth == null) return false;
        grindCount++;
        if (grindCount >= 4)
        {
            grindCount = 0;
            ItemStack output = Kitchen.instance.grinderRecipes.getOutput(ingredient, mouth);
            BlockContainerBase.spawnItemInWorld(output, worldObj, xCoord, yCoord, zCoord);
            this.ingredient = null;
        }
        return true;
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        if (ingredient != null) compound.setTag(INGREDIENT, ingredient.writeToNBT(new NBTTagCompound()));
        if (mouth != null) compound.setTag(MOUTH, mouth.writeToNBT(new NBTTagCompound()));
        compound.setInteger(GRIND_COUNT, this.grindCount);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        ingredient = (mouth = null);
        if (compound.hasKey(INGREDIENT, 10))
            this.ingredient = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(INGREDIENT));
        if (compound.hasKey(MOUTH, 10))
            this.mouth = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(MOUTH));
        this.grindCount = compound.getInteger(GRIND_COUNT);
    }
}
