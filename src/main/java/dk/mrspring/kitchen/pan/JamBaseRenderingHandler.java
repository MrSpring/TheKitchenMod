package dk.mrspring.kitchen.pan;

import dk.mrspring.kitchen.api.ingredient.IIngredientRenderingHandler;
import dk.mrspring.kitchen.api.ingredient.Ingredient;
import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import dk.mrspring.kitchen.model.jam.ModelJam0;
import dk.mrspring.kitchen.model.jam.ModelJam1;
import dk.mrspring.kitchen.model.jam.ModelJam2;
import dk.mrspring.kitchen.model.jam.ModelJam3;
import net.minecraft.client.model.ModelBase;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public class JamBaseRenderingHandler implements IIngredientRenderingHandler
{
    float[] baseColor;

    ModelJam0 modelJam0;
    ModelJam1 modelJam1;
    ModelJam2 modelJam2;
    ModelJam3 modelJam3;

    public JamBaseRenderingHandler(int color)
    {
        this(ItemMixingBowlRenderer.intAsFloatArray(color));
    }

    public JamBaseRenderingHandler(float[] colors)
    {
        this.baseColor = colors;
        modelJam0 = new ModelJam0();
        modelJam1 = new ModelJam1();
        modelJam2 = new ModelJam2();
        modelJam3 = new ModelJam3();
    }

    @Override
    public ModelBase getModel(int boilTime, Ingredient ingredient)
    {
        if (boilTime >= 250)
            return modelJam3;
        else if (boilTime > 125)
            return modelJam2;
        else if (boilTime > 75)
            return modelJam1;
        else return modelJam0;
    }

    @Override
    public boolean useColorModifier(int boilTime, Ingredient ingredient)
    {
        return true;
    }

    @Override
    public float[] getColorModifier(int boilTime, Ingredient ingredient)
    {
        return baseColor;
    }

    @Override
    public boolean scaleOnPan(int boilTime, Ingredient ingredient)
    {
        return false;
    }
}
