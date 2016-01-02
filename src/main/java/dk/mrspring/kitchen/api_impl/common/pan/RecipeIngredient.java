package dk.mrspring.kitchen.api_impl.common.pan;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.recipe.FryingPanRecipes;
import dk.mrspring.kitchen.recipe.IRecipe;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 02-06-2015.
 */
public class RecipeIngredient implements IIngredient
{
    public static final String RECIPE_INPUT = "RecipeInput";
    public static final String RECIPE_OUTPUT = "RecipeOutput";

    @Override
    public String getName()
    {
        return "basic_recipe";
    }

    @Override
    public String getDisplayName(IFryingPan pan)
    {
        NBTTagCompound compound = pan.getSpecialInfo();
        NBTTagCompound itemCompound = compound.getCompoundTag(RECIPE_OUTPUT);
        ItemStack stack = ItemStack.loadItemStackFromNBT(itemCompound);
        return ItemUtils.name(stack);
    }

    @Override
    public boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return FryingPanRecipes.instance().hasOutput(clicked);
    }

    @Override
    public boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player)
    {
        return true;
    }

    @Override
    public void onAdded(IFryingPan pan, ItemStack input, EntityPlayer player)
    {
        pan.playSound(ModInfo.toTexture("sizzle"), 1F, 1F, false);
        IRecipe recipe = FryingPanRecipes.instance().getRecipeFor(input);
        ItemStack output = recipe.getOutput(input).copy();
        NBTTagCompound compound = pan.getSpecialInfo();

        NBTTagCompound inputCompound = new NBTTagCompound();
        NBTTagCompound outputCompound = new NBTTagCompound();
        input.writeToNBT(inputCompound);
        output.writeToNBT(outputCompound);

        compound.setTag(RECIPE_INPUT, inputCompound);
        compound.setTag(RECIPE_OUTPUT, outputCompound);

        recipe.reduceInputSize(input);
//        input.stackSize--;
    }

    @Override
    public boolean onRightClicked(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        return false;
    }

    @Override
    public int getCookTime(IFryingPan pan)
    {
        return 150;
    }

    @Override
    public boolean canBeRemoved(IFryingPan pan, EntityPlayer player)
    {
        return pan.isFinished();
    }

    @Override
    public ItemStack onRemoved(IFryingPan pan, ItemStack clicked, EntityPlayer player)
    {
        NBTTagCompound compound = pan.getSpecialInfo();
        NBTTagCompound itemCompound = compound.getCompoundTag(RECIPE_OUTPUT);
        return ItemStack.loadItemStackFromNBT(itemCompound);
    }

    @Override
    public boolean readyToCook(IFryingPan pan)
    {
        return true;
    }
}
