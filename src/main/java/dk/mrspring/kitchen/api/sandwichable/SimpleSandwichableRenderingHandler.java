package dk.mrspring.kitchen.api.sandwichable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by MrSpring on 04-11-2014 for TheKitchenMod.
 */
public class SimpleSandwichableRenderingHandler implements ISandwichableRenderingHandler
{
    @Override
    public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound specialTagInfo)
    {
        return null;
    }

    @Override
    public int getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound specialTagInfo)
    {
        return 0;
    }
}
