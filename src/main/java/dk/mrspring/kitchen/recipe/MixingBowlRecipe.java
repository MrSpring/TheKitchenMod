package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.item.ItemMixingBowl;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created on 21-09-2015 for TheKitchenMod.
 */
public class MixingBowlRecipe extends BasicRecipe
{
    public MixingBowlRecipe(String input, ItemStack stack)
    {
        super(ItemMixingBowl.getMixingBowlStack(input, 3), stack);
    }

    public MixingBowlRecipe(String input, Item output)
    {
        super(ItemMixingBowl.getMixingBowlStack(input, 3), output);
    }

    @Override
    public boolean doesInputMatch(ItemStack stack)
    {
        return ItemUtils.areStacksEqual(stack, input, false);
    }

    @Override
    public boolean doesExpectedInputMatch(ItemStack otherInput)
    {
        return ItemUtils.areStacksEqual(otherInput, input, false);
    }
}
