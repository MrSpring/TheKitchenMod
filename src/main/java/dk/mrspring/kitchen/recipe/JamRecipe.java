package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.api.stack.JamJarStack;
import dk.mrspring.kitchen.api.stack.Stack;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.util.StackUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 19-06-2015.
 */
public class JamRecipe implements IRecipe, INEIRecipeHelper
{
    Stack input;
    String jamOutput;
    int amount = 6;

    public JamRecipe(Item input, String jam)
    {
        this(StackUtils.fromItemStack(new ItemStack(input)), jam);
    }

    public JamRecipe(ItemStack input, String jam)
    {
        this(StackUtils.fromItemStack(input), jam);
    }

    public JamRecipe(Stack input, String jam)
    {
        this.input = input;
        this.jamOutput = jam;
    }

    public int getAmount()
    {
        return amount;
    }

    public JamRecipe setAmount(int amount)
    {
        this.amount = amount;
        return this;
    }

    @Override
    public boolean doesInputMatch(Stack stack)
    {
        return stack.areStacksEqual(input, Stack.Type.ITEM, Stack.Type.METADATA);
    }

    @Override
    public boolean doesInputMatch(ItemStack stack)
    {
        return doesInputMatch(StackUtils.fromItemStack(stack));
    }

    @Override
    public ItemStack getOutput(ItemStack stack)
    {
        return ItemJamJar.getJamJarStack(jamOutput, getAmount());
    }

    @Override
    public Stack getOutput(Stack stack)
    {
        return new JamJarStack(this.jamOutput, getAmount());
    }

    @Override
    public boolean isValid()
    {
        return jamOutput != null && !jamOutput.equals("empty");
    }

    public String getJamOutput(ItemStack input)
    {
        return this.jamOutput;
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
        return ItemJamJar.getJamJarStack(jamOutput, getAmount());
    }

    @Override
    public boolean doesExpectedInputMatch(ItemStack otherInput)
    {
        return getExpectedInput().isItemEqual(otherInput);
    }

    @Override
    public boolean doesExpectedOutputMatch(ItemStack otherOutput)
    {
        return jamOutput.equals(ItemJamJar.getJamFromStack(otherOutput));
    }
}
