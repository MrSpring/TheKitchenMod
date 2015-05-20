package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api.stack.Stack;
import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import dk.mrspring.kitchen.util.StackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipe implements IRecipe, INEIRecipeHelper
{
    Stack input, output;

    public BasicRecipe(Item input, Item output)
    {
        this(new Stack(input, 0), new Stack(output, 0));
    }

    public BasicRecipe(ItemStack input, ItemStack output)
    {
        this(StackUtils.fromItemStack(input), StackUtils.fromItemStack(output));
    }

    public BasicRecipe(Stack input, Stack output)
    {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean doesInputMatch(ItemStack stack)
    {
        return this.doesInputMatch(StackUtils.fromItemStack(stack));
    }

    @Override
    public boolean doesInputMatch(Stack stack)
    {
        return this.input.areStacksEqual(stack, Stack.Type.ITEM, Stack.Type.METADATA);
    }

    @Override
    public ItemStack getOutput(ItemStack input)
    {
        return this.getOutput(StackUtils.fromItemStack(input)).toItemStack();
    }

    @Override
    public Stack getOutput(Stack stack)
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
        return input.toItemStack();
    }

    @Override
    public ItemStack getExpectedOutput(ItemStack input)
    {
        return getExpectedOutput();
    }

    @Override
    public ItemStack getExpectedOutput()
    {
        return output.toItemStack();
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

    /*ItemStack input, output;

    public BasicRecipe(Item input, Item output)
    {
        this(new ItemStack(input), new ItemStack(output));
    }

    public BasicRecipe(Item input, ItemStack output)
    {
        this(new ItemStack(input), output);
    }

    public BasicRecipe(ItemStack input, Item output)
    {
        this(input, new ItemStack(output));
    }

    public BasicRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    public BasicRecipe(String input, String output)
    {
        if (input.contains(":"))
        {
            String modId = input.split(":")[0];
            String itemName = input.split(":")[1];
            this.input = GameRegistry.findItemStack(modId, itemName, 1);
        }

        if (output.contains(":"))
        {
            String modId = output.split(":")[0];
            String itemName = output.split(":")[1];
            this.output = GameRegistry.findItemStack(modId, itemName, 1);
        }
    }

    public BasicRecipe(JsonBasicRecipe jsonRecipe)
    {
        this(jsonRecipe.getInput().toItemStack(), jsonRecipe.getOutput().toItemStack());
    }

    public ItemStack getInput()
    {
        return input;
    }

    public BasicRecipe setInput(ItemStack input)
    {
        this.input = input;
        return this;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public BasicRecipe setOutput(ItemStack output)
    {
        this.output = output;
        return this;
    }

    public boolean isValid()
    {
        return this.input != null && this.output != null;
    }*/
}
