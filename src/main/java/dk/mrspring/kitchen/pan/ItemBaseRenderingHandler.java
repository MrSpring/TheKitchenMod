package dk.mrspring.kitchen.pan;

import dk.mrspring.kitchen.model.ModelItemBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 04-11-2014.
 */
public class ItemBaseRenderingHandler implements IIngredientRenderingHandler
{
    ModelBase preFried;
    ModelBase postFried;

    public ItemBaseRenderingHandler(ItemStack pre, ItemStack post)
    {
        this.preFried = new ModelItemBase(pre);
        this.postFried = new ModelItemBase(post);
    }

    @Override
    public ModelBase getModel(int boilTime, Ingredient ingredient)
    {
        if (boilTime >= 300)
            return postFried;
        else return preFried;
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
        return false;
    }
}
