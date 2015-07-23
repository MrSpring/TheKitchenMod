package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.item.ItemJamJar;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 19-06-2015.
 */
public class JamRecipe extends BasicRecipe
{
    String jamOutput;
    int amount = 6;

    public JamRecipe(Item input, String jam)
    {
        this(new ItemStack(input), jam);
    }

    public JamRecipe(ItemStack input, String jam)
    {
        super(input, ItemJamJar.getJamJarStack(jam, 6));
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
        resetOutput();
        return this;
    }

    private void resetOutput()
    {
        this.output = ItemJamJar.getJamJarStack(jamOutput, amount);
    }

    public String getJamOutput(ItemStack input)
    {
        return jamOutput;
    }

    public void setJamOutput(String jamOutput)
    {
        this.jamOutput = jamOutput;
        resetOutput();
    }
}
