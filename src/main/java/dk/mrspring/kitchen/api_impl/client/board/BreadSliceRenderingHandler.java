package dk.mrspring.kitchen.api_impl.client.board;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BreadSliceRenderingHandler implements IBoardRenderingHandler
{
    ModelBreadSliceTop top = new ModelBreadSliceTop();
    ModelBreadSliceBottom bottom = new ModelBreadSliceBottom();

    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return rendering.getItem() == KitchenItems.bread_slice;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        if (indexInList + 1 >= layers.size())
            top.render(null, 0, 0, 0, 0, 0, 0.0625F);
        else bottom.render(null, 0, 0, 0, 0, 0, 0.0625F);
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 0;
    }
}
