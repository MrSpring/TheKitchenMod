package dk.mrspring.kitchen.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 28-07-2015.
 */
public class OvenRecipe extends BasicRecipe
{
    ItemStack burntResult;

    public OvenRecipe(Item input, Item output, Item burnt)
    {
        super(input, output);
        this.burntResult = new ItemStack(burnt);
    }

    public OvenRecipe(ItemStack input, Item output)
    {
        super(input, output);
    }

    public OvenRecipe(Item input, ItemStack output)
    {
        super(input, output);
    }

    public OvenRecipe(ItemStack input, ItemStack output, ItemStack burnt)
    {
        super(input, output);
        this.burntResult = burnt;
    }

    public ItemStack getBurntResult(ItemStack input)
    {
        return burntResult;
    }
}
