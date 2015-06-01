package dk.mrspring.kitchen.api_impl.client.ingredient;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 20-05-2015.
 */
public class BaconRenderingHandler implements IIngredientRenderingHandler
{
    private static ModelBaconRaw raw = new ModelBaconRaw();
    private static ModelBaconCooked cooked = new ModelBaconCooked();

    @Override
    public boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient)
    {
        return ingredient.getName().equals("kitchen:raw_bacon");
    }

    @Override
    public void render(IFryingPan fryingPan, IIngredient ingredient)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTranslatef(0, 0.84F, 0);
        float s = 0.4F;
        GL11.glScalef(s, s, s);
        if (fryingPan.isFinished())
            cooked.render(null, 0, 0, 0, 0, 0, 0.0625F);
        else raw.render(null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }
}
