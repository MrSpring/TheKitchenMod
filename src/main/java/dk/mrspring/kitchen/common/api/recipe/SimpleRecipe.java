package dk.mrspring.kitchen.common.api.recipe;

import dk.mrspring.kitchen.common.util.ItemUtils;
import net.minecraft.item.ItemStack;

/**
 * Created on 26-04-2016 for TheKitchenMod.
 */
public class SimpleRecipe implements IRecipe
{
    ItemStack input;
    ItemStack[] output;
    int defaultOutput = 0;

    public SimpleRecipe(ItemStack input, ItemStack... output)
    {
        this.input = input.copy();
        this.output = new ItemStack[output.length];
        for (int i = 0; i < output.length; i++)
            this.output[i] = output[i].copy();
    }

    public SimpleRecipe setDefaultOutput(int output)
    {
        this.defaultOutput = output;
        return this;
    }

    @Override
    public boolean acceptsInput(ItemStack stack)
    {
        return ItemUtils.equalStacks(stack, this.input);
    }

    @Override
    public ItemStack[] getOutput(ItemStack input)
    {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getExpectedInput()
    {
        return input.copy();
    }

    @Override
    public ItemStack getExpectedInput(ItemStack[] output)
    {
        return getExpectedInput();
    }

    @Override
    public ItemStack getExpectedOutput()
    {
        return output[defaultOutput];
    }

    @Override
    public ItemStack getExpectedOutput(ItemStack input)
    {
        return getExpectedOutput();
    }
}
