package dk.mrspring.kitchen.pan;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.model.ModelItemBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 04-11-2014.
 */
public class ItemBaseRenderingHandler implements IIngredientRenderingHandler // TODO: Fix
{
    @Override
    public boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient)
    {
        return false;
    }

    @Override
    public void render(IFryingPan fryingPan, IIngredient ingredient)
    {

    }
    /*ModelBase preFried;
    ModelBase postFried;

    public ItemBaseRenderingHandler(Item pre, Item post)
    {
        this(new ItemStack(pre), new ItemStack(post));
    }

    public ItemBaseRenderingHandler(ItemStack pre, Item post)
    {
        this(pre, new ItemStack(post));
    }

    public ItemBaseRenderingHandler(Item pre, ItemStack post)
    {
        this(new ItemStack(pre), post);
    }

    public ItemBaseRenderingHandler(ItemStack pre, ItemStack post)
    {
        this.preFried = new ModelItemBase(pre);
        this.postFried = new ModelItemBase(post);
    }

    @Override
    public ModelBase getModel(int boilTime, Ingredient ingredient)
    {
        if (boilTime >= 250)
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
    }*/
}
