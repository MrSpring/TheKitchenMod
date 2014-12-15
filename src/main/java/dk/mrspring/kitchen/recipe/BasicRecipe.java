package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.config.JsonBasicRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipe
{
    ItemStack input, output;

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
        } else this.input = new ItemStack(Blocks.air);

        if (output.contains(":"))
        {
            String modId = output.split(":")[0];
            String itemName = output.split(":")[1];
            this.output = GameRegistry.findItemStack(modId, itemName, 1);
        } else this.output = new ItemStack(Blocks.air);
    }

    public BasicRecipe(JsonBasicRecipe jsonRecipe)
    {
        this(jsonRecipe.getInput(), jsonRecipe.getOutput());
    }

    public BasicRecipe setInput(ItemStack input)
    {
        this.input = input;
        return this;
    }

    public BasicRecipe setOutput(ItemStack output)
    {
        this.output = output;
        return this;
    }

    public ItemStack getInput()
    {
        return input;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
