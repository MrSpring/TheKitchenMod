package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRegistry;
import dk.mrspring.kitchen.api_impl.common.pan.BasicIngredient;
import dk.mrspring.kitchen.api_impl.common.pan.JamIngredient;
import dk.mrspring.kitchen.api_impl.common.pan.RecipeIngredient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 25-03-2015.
 */
public class IngredientRegistry implements IIngredientRegistry
{
    private static final IngredientRegistry ourInstance = new IngredientRegistry();
    private static final IIngredient defaultIngredient = new BasicIngredient();
    private List<IIngredient> ingredients = new ArrayList<IIngredient>();

    private IngredientRegistry()
    {
        registerIngredient(new RecipeIngredient());
        registerIngredient(new JamIngredient());
    }

    public static IngredientRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerIngredient(IIngredient ingredient)
    {
        if (ingredient != null)
            ingredients.add(ingredient);
    }

    @Override
    public IIngredient getIngredientFor(IFryingPan fryingPan, ItemStack stack, EntityPlayer player)
    {
        for (IIngredient ingredient : ingredients)
            if (ingredient.isForItem(fryingPan, stack, player))
                return ingredient;
        return defaultIngredient;
    }

    @Override
    public IIngredient getIngredientFromName(String ingredientName)
    {
        for (IIngredient ingredient : ingredients)
            if (ingredient.getName().equals(ingredientName))
                return ingredient;
        return null; // Returns null because it's not from an item.
    }
}
