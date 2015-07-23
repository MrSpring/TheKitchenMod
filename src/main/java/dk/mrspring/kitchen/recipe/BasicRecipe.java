package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipe implements IRecipe, INEIRecipeHelper
{
    ItemStack input, output;
    boolean strict = false;

    public BasicRecipe(Item input, Item output)
    {
        this(new ItemStack(input), new ItemStack(output));
    }

    public BasicRecipe(ItemStack input, Item output)
    {
        this(input, new ItemStack(output));
    }

    public BasicRecipe(Item input, ItemStack output)
    {
        this(new ItemStack(input), output);
    }

    public BasicRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    public void setStrict(boolean strict)
    {
        this.strict = strict;
    }

    public boolean isStrict()
    {
        return strict;
    }

    @Override
    public boolean doesInputMatch(ItemStack stack)
    {
        return ItemUtils.areStacksEqual(stack, input, isStrict());
    }

    @Override
    public ItemStack getOutput(ItemStack input)
    {
        return output;
    }

    @Override
    public boolean isValid()
    {
        return this.input != null && this.output != null;
    }

    @Override
    public ItemStack getExpectedInput(ItemStack output)
    {
        return getExpectedInput();
    }

    @Override
    public ItemStack getExpectedInput()
    {
        return input;
    }

    @Override
    public ItemStack getExpectedOutput(ItemStack input)
    {
        return getExpectedOutput();
    }

    @Override
    public ItemStack getExpectedOutput()
    {
        return output;
    }

    @Override
    public boolean doesExpectedOutputMatch(ItemStack otherOutput)
    {
        return getExpectedOutput().isItemEqual(otherOutput);
    }

    @Override
    public boolean doesExpectedInputMatch(ItemStack otherInput)
    {
        return getExpectedInput().isItemEqual(otherInput);
    }
}
