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
public class JamRecipe implements IRecipe
{
    Stack input;
    String jamOutput;

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
        return ItemJamJar.getJamJarStack(jamOutput, 6);
    }

    @Override
    public Stack getOutput(Stack stack)
    {
        return new JamJarStack(this.jamOutput, 6);
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
}
