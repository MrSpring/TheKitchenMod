package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created on 17-11-2015 for TheKitchenMod.
 */
public class GrinderRecipe extends BasicRecipe
{
    ItemStack grinderOpening;

    public GrinderRecipe(ItemStack input, ItemStack opening, ItemStack output)
    {
        super(input, output);
        this.grinderOpening = opening;
    }

    public GrinderRecipe(ItemStack input, Item opening, ItemStack output)
    {
        this(input, new ItemStack(opening), output);
    }

    public GrinderRecipe(Item input, Item opening, ItemStack output)
    {
        this(new ItemStack(input), opening, output);
    }

    public GrinderRecipe(Item input, ItemStack opening, ItemStack output)
    {
        this(new ItemStack(input), opening, output);
    }

    public boolean doesInputAndMouthMatch(ItemStack input, ItemStack mouth)
    {
        return this.doesInputMatch(input) && ItemUtils.areStacksEqual(mouth, grinderOpening, isStrict());
    }

    public ItemStack getOutput(ItemStack input, ItemStack mouth)
    {
        return this.getOutput(input);
    }

    public ItemStack getDrop(ItemStack input, ItemStack inGrinder)
    {
        return ItemUtils.item(inGrinder, KitchenItems.mixing_bowl) ? null : inGrinder;
    }
}
