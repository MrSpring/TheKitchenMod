package dk.mrspring.kitchen.pan;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 19-10-2014 for TheKitchenMod.
 */
public class Ingredient
{
    public static Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    /*EMPTY(new JamBaseRenderingHandler(new float[]{0, 0, 0}), Jam.EMPTY),
    STRAWBERRY(new JamBaseRenderingHandler(new float[]{255F, 60, 53}), Jam.STRAWBERRY),
	APPLE(new JamBaseRenderingHandler(new float[]{224, 255, 163}), Jam.APPLE),
	PEANUT(new JamBaseRenderingHandler(new float[]{147, 101, 41}), Jam.PEANUT),
	BACON(new IIngredientRenderingHandler(){
		ModelBase rawBaconModel = new ModelBaconRaw();
		ModelBase cookedBaconModel = new ModelBaconCooked();

		@Override
		public ModelBase getModel(int boilTime, Ingredient ingredient)
		{
			if (boilTime >= 400)
				return cookedBaconModel;
			else return rawBaconModel;
		}

		@Override
		public boolean useColorModifier(int boilTime, Ingredient ingredient)
		{
			return false;
		}

		@Override
		public float[] getColorModifier(int boilTime, Ingredient ingredient)
		{
			return new float[0];
		}

		@Override
		public boolean scaleOnPan(int boilTime, Ingredient ingredient)
		{
			return true;
		}
	}, new ItemStack(KitchenItems.bacon, 1));*/

    final String name;
    final IIngredientRenderingHandler renderingHandler;
    final boolean isJam;
    final String jResult;
    final ItemStack iResult;

    public Ingredient(String name, IIngredientRenderingHandler handler, boolean jam, String jamResult, ItemStack itemResult)
    {
        this.name = name;
        this.renderingHandler = handler;
        this.isJam = jam;
        this.jResult = jamResult;
        this.iResult = itemResult;
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, ItemStack iResult)
    {
        this(name, handler, false, "empty", iResult);
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, String jResult)
    {
        this(name, handler, true, jResult, null);
    }

    public static void registerIngredient(Ingredient ingredient)
    {
        if (ingredient != null)
            if (!ingredients.containsKey(ingredient.getName()))
                ingredients.put(ingredient.getName(), ingredient);
    }

    public static Ingredient getIngredient(String name)
    {
        if (name != null)
            if (ingredients.containsKey(name))
                return ingredients.get(name);
        return ingredients.get("empty");
    }

    public String getName()
    {
        return name;
    }

    public IIngredientRenderingHandler getRenderingHandler()
    {
        return this.renderingHandler;
    }

    public boolean isJam()
    {
        return isJam;
    }

    public Jam getJamResult()
    {
        return Jam.getJam(this.jResult);
    }

    public ItemStack getItemResult()
    {
        return iResult.copy();
    }
}
