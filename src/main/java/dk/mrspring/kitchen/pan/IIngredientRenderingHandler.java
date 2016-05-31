package dk.mrspring.kitchen.pan;

import net.minecraft.client.model.ModelBase;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public interface IIngredientRenderingHandler
{
    ModelBase getModel(int boilTime, Ingredient ingredient);

    boolean useColorModifier(int boilTime, Ingredient ingredient);

    float[] getColorModifier(int boilTime, Ingredient ingredient);

    boolean scaleOnPan(int boilTime, Ingredient ingredient);
}
