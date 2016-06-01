package dk.mrspring.kitchen.pan;

import dk.mrspring.kitchen.model.jam.ModelJam0;
import dk.mrspring.kitchen.model.jam.ModelJam1;
import dk.mrspring.kitchen.model.jam.ModelJam2;
import dk.mrspring.kitchen.model.jam.ModelJam3;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public class JamBaseRenderingHandler implements IIngredientRenderingHandler
{
    float[] baseColor;

    ModelJam0 modelJam0 = new ModelJam0();
    ModelJam1 modelJam1 = new ModelJam1();
    ModelJam2 modelJam2 = new ModelJam2();
    ModelJam3 modelJam3 = new ModelJam3();

    public JamBaseRenderingHandler(float[] colors)
    {
        this.baseColor = colors;
    }

    @Override
    public boolean translateModel(int cookTime, Ingredient ingredient)
    {
        return false;
    }

    @Override
    public void render(int cookTime, Ingredient ingredient)
    {
        colorRGB(baseColor);

        rotate(180, 0, 0, 1);
        translate(-0.5F, -1.5F, 0.5F);

        if (cookTime >= 300) modelJam3.simpleRender(0F);
        else if (cookTime > 200) modelJam2.simpleRender(0F);
        else if (cookTime > 100) modelJam1.simpleRender(0F);
        else modelJam0.simpleRender(0F);
    }
}
