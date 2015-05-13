package dk.mrspring.kitchen.util;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api_impl.common.SandwichableRegistry;
import dk.mrspring.kitchen.item.render.SandwichRender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 10-05-2015.
 */
public class SandwichUtils
{
    public static ItemStack makeSandwich(ItemStack[] layers)
    {
        return null; // TODO: Make Sandwich from layers
    }

    public static boolean isAllSandwichable(ItemStack[] layers)
    {
        for (ItemStack stack : layers)
            if (!SandwichableRegistry.getInstance().isSandwichable(stack))
                return false;
        return true;
    }

    public static boolean isAllSandwichable(List<ItemStack> layers)
    {
        return isAllSandwichable(layers.toArray(new ItemStack[layers.size()]));
    }

    public static List<ItemStack> getSandwichAsLayerList(ItemStack sandwich)
    {
        List<ItemStack> layers = new ArrayList<ItemStack>();

        if (sandwich.getTagCompound() != null)
        {
            NBTTagList layersTagList = sandwich.getTagCompound().getTagList("SandwichLayers", 10);
            if (layersTagList != null)
                for (int i = 0; i < layersTagList.tagCount(); i++)
                {
                    NBTTagCompound layerCompound = layersTagList.getCompoundTagAt(i);
                    ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);
                    layers.add(layer);
                }
        }
        return layers;
    }
}
