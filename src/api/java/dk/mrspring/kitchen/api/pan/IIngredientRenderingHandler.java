package dk.mrspring.kitchen.api.pan;

import net.minecraft.client.model.ModelBase;

/**
 * Created by MrSpring on 27-10-2014 for TheKitchenMod.
 */
public interface IIngredientRenderingHandler
{
    boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient);

    void render(IFryingPan fryingPan, IIngredient ingredient);
//    public ModelBase getModel(int boilTime, Ingredient ingredient);
//    public boolean useColorModifier(int boilTime,Ingredient ingredient);
//    public float[] getColorModifier(int boilTime,Ingredient ingredient);
//	public boolean scaleOnPan(int boilTime, Ingredient ingredient);
}
