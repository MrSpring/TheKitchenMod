package dk.mrspring.kitchen.api_impl.client.ingredient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingHandler;

/**
 * Created by Konrad on 20-05-2015.
 */
@SideOnly(Side.CLIENT)
public class FallbackIngredientRenderingHandler implements IIngredientRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient)
    {
        return true;
    }

    @Override
    public void render(IFryingPan fryingPan, IIngredient ingredient)
    {
        //TODO: Render
    }
}
