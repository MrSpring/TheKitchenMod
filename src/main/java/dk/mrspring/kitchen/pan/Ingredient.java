package dk.mrspring.kitchen.pan;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 19-10-2014 for TheKitchenMod.
 */
public enum Ingredient
{
	EMPTY(new JamBaseRenderingHandler(new float[]{0, 0, 0}), Jam.EMPTY),
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
	}, new ItemStack(KitchenItems.bacon, 1));

	final IIngredientRenderingHandler renderingHandler;
	final boolean isJam;
	final Jam jResult;
	final ItemStack iResult;

	private Ingredient(IIngredientRenderingHandler handler, boolean jam, Jam jamResult, ItemStack itemResult)
	{
		this.renderingHandler = handler;
		this.isJam = jam;
		this.jResult = jamResult;
		this.iResult = itemResult;
	}

	private Ingredient(IIngredientRenderingHandler handler, ItemStack iResult)
	{
		this(handler, false, Jam.EMPTY, iResult);
	}

	private Ingredient(IIngredientRenderingHandler handler, Jam jResult)
	{
		this(handler, true, jResult, null);
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
		return jResult;
	}

	public ItemStack getItemResult()
	{
		return iResult.copy();
	}
}
