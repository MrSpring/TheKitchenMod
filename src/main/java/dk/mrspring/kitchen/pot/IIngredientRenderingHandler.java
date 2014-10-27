package dk.mrspring.kitchen.pot;

import net.minecraft.client.model.ModelBase;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public interface IIngredientRenderingHandler
{
    public ModelBase getModel(int boilTime, Ingredient ingredient);
    public boolean useColorModifier(int boilTime,Ingredient ingredient);
    public float[] getColorModifier(int boilTime,Ingredient ingredient);
}
