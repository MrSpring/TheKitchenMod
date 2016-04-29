package dk.mrspring.kitchen.common.api.oven.item;

import dk.mrspring.kitchen.common.api.registry.RegistryBase;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public class OvenItemRegistry extends RegistryBase<String, OvenItem>
{
    public final OvenItem recipe_item = new RecipeOvenItem();

    public void register()
    {
        register("kitchen:recipes", recipe_item);
    }
}
