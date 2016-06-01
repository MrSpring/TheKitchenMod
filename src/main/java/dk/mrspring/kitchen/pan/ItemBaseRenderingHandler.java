package dk.mrspring.kitchen.pan;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created by MrSpring on 04-11-2014.
 */
public class ItemBaseRenderingHandler implements IIngredientRenderingHandler
{
    ItemStack preFried, postFried;

    public ItemBaseRenderingHandler(ItemStack pre, ItemStack post)
    {
        this.preFried = pre.copy();
        this.postFried = post.copy();
    }

    public ItemBaseRenderingHandler(Item pre, Item post)
    {
        this(new ItemStack(pre), new ItemStack(post));
    }

    @Override
    public boolean translateModel(int cookTime, Ingredient ingredient)
    {
        return false;
    }

    @Override
    public void render(int cookTime, Ingredient ingredient)
    {
        translate(0.5F, 0.075F, 0.5F);
        rotate(90F, 1F, 0F, 0F);
        scale(0.74F);
        renderItemStack(cookTime >= 300 ? postFried : preFried);
    }
}
