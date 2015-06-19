package dk.mrspring.kitchen.util;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
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
    public static ItemStack makeSandwich(ItemStack[] layers) // TODO: Re-implement Combo system
    {
        NBTTagList layersList = new NBTTagList();
        ItemStack finishedSandwich = GameRegistry.findItemStack(ModInfo.modid, "sandwich", 1);

        for (ItemStack layer : layers)
        {
            NBTTagCompound layerCompound = new NBTTagCompound();
            layer.writeToNBT(layerCompound);
            layersList.appendTag(layerCompound);
        }

        finishedSandwich.setTagInfo("SandwichLayers", layersList);
        return finishedSandwich;
        //return null; // TODO: Determine combo/Fix combo system
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

    public static boolean isSandwichReady(List<ItemStack> layers)
    {
        if (isAllSandwichable(layers))
        {
            int lc = layers.size();
            ISandwichable bottom = SandwichableRegistry.getInstance().getSandwichableForItem(layers.get(0));
            ISandwichable top = SandwichableRegistry.getInstance().getSandwichableForItem(layers.get(lc - 1));
            return bottom.getIsBread() && top.getIsBread();
        } else return false;
    }
}
