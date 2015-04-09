package dk.mrspring.kitchen.api.ingredient;

import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 27-03-2015.
 */
public class IngredientMixingBowl extends Ingredient
{
    final String mixType;

    public IngredientMixingBowl(String name, IIngredientRenderingHandler handler, ItemStack iResult, String mixType)
    {
        super(name, handler, iResult);
        this.mixType = mixType;
    }

    @Override
    public boolean canAdd(ItemStack stack)
    {
        if (stack.getTagCompound() != null && stack.getItemDamage() > 0)
        {
            String mixType = stack.getTagCompound().getString("MixType");
            if (mixType != null)
                return mixType.equals(this.mixType);
        }
        return false;
    }

    @Override
    public void onAdded(ItemStack clickedStack)
    {
        clickedStack.setItemDamage(clickedStack.getItemDamage() - 1);
    }
}
