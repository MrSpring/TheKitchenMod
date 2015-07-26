package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 11-07-2015.
 */
public class RecipeOvenItem implements IOvenItem
{
    public static final String RECIPE_INPUT = "RecipeInput";
    public static final String RECIPE_OUTPUT = "RecipeOutput";

    @Override
    public String getName()
    {
        return "basic_recipe";
    }

    @Override
    public String getDisplayName()
    {
        return "Recipe";
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return OvenRecipes.instance().hasOutput(item);
    }

    @Override
    public boolean canAdd(IOven oven, ItemStack adding, EntityPlayer player, boolean[] freeSlots)
    {
        return true;
    }

    @Override
    public void onAdded(IOven oven, ItemStack clicked, EntityPlayer player, int[] slots)
    {
        int slot = slots[0];
        ItemStack input = clicked.copy();
        input.stackSize = 1;
        NBTTagCompound slotCompound = oven.getSpecialInfo(slot);
        ItemStack output = OvenRecipes.instance().getOutputFor(input);
        NBTTagCompound outputCompound = new NBTTagCompound(), inputCompound = new NBTTagCompound();
        input.writeToNBT(inputCompound);
        output.writeToNBT(outputCompound);
        slotCompound.setTag(RECIPE_INPUT, inputCompound);
        slotCompound.setTag(RECIPE_OUTPUT, outputCompound);
        clicked.stackSize--;
        System.out.println("On added, input: " + ItemUtils.name(input) + ", clicked: " + ItemUtils.name(clicked));
    }

    @Override
    public boolean readyToCook(IOven oven, int slot)
    {
        return true;
    }

    @Override
    public int getSize(IOven oven)
    {
        return 1;
    }

    @Override
    public boolean consecutive(IOven oven)
    {
        return false;
    }

    @Override
    public int getCookTime(IOven oven)
    {
        return 200;
    }

    @Override
    public boolean onRightClicked(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
//        return false;
        NBTTagCompound slotCompound = oven.getSpecialInfo(slot);
        NBTTagCompound inputCompound = slotCompound.getCompoundTag(RECIPE_INPUT);
        ItemStack input = ItemStack.loadItemStackFromNBT(inputCompound);
        if (input == null)
        {
            input = clicked.copy();
            input.stackSize = 1;
        }
        if (ItemUtils.areStacksEqual(clicked, input, false) && input.stackSize < 4)
        {
            input.stackSize++;
            clicked.stackSize--;
            ItemStack output = OvenRecipes.instance().getOutputFor(input);
            if (output != null)
            {
                System.out.println("output: " + ItemUtils.name(output));
                output.stackSize *= input.stackSize;
                NBTTagCompound outputCompound = new NBTTagCompound();
                output.writeToNBT(outputCompound);
                slotCompound.setTag(RECIPE_OUTPUT, outputCompound);
            }
            NBTTagCompound newInputCompound = new NBTTagCompound();
            input.writeToNBT(newInputCompound);
            slotCompound.setTag(RECIPE_INPUT, newInputCompound);
            return true;
        } else return false;
    }

    @Override
    public boolean canBeRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
        return true;
    }

    @Override
    public ItemStack onRemoved(IOven oven, ItemStack clicked, EntityPlayer player, int slot)
    {
        NBTTagCompound slotCompound = oven.getSpecialInfo(slot);
        boolean done = oven.isFinished();
        NBTTagCompound resultCompound = slotCompound.getCompoundTag(done ? RECIPE_OUTPUT : RECIPE_INPUT);
        ItemStack dropping = ItemStack.loadItemStackFromNBT(resultCompound);
        System.out.println(ItemUtils.name(dropping));
        return dropping;
    }
}