package dk.mrspring.kitchen.api.sandwichable;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public class SandwichableRenderingRegistry
{
    static Map<String, ISandwichableRenderingHandler> renderers = new HashMap<String, ISandwichableRenderingHandler>();

    /**
     * @param itemName The UniqueIdentifier for the item.
     * @return Returns the rendering handler associated with the itemName.
     */
    public static ISandwichableRenderingHandler getRenderingHandlerFor(String itemName)
    {
        if (renderers.containsKey(itemName))
            return renderers.get(itemName);
        else return new ISandwichableRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return null;
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return 0.0325 * 1.4;
            }
        };
    }

    /**
     * @param item The item to get rendering handler for.
     * @return Returns the rendering handler associated with the item.
     */
    public static ISandwichableRenderingHandler getRenderingHandlerFor(ItemStack item)
    {
        String key = GameRegistry.findUniqueIdentifierFor(item.getItem()).toString();
        return getRenderingHandlerFor(key);
    }

    /**
     * Register a rendering handler for itemName.
     *
     * @param itemName         The UniqueIdentifier for the item.
     * @param renderingHandler The rendering handler to register to the item.
     */
    public static void registerRenderingHandler(String itemName, ISandwichableRenderingHandler renderingHandler)
    {
        if (!renderers.containsKey(itemName))
            renderers.put(itemName, renderingHandler);
    }

    /**
     * Register a rendering handler for the item.
     *
     * @param item             The Item to register the handler for.
     * @param renderingHandler The rendering handler to register to the item.
     */
    public static void registerRenderingHandler(Item item, ISandwichableRenderingHandler renderingHandler)
    {
        registerRenderingHandler(GameRegistry.findUniqueIdentifierFor(item).toString(), renderingHandler);
    }

    /**
     * Register a rendering handler for the item.
     *
     * @param block            The Block to register the handler for.
     * @param renderingHandler The rendering handler to register to the item.
     */
    public static void registerRenderingHandler(Block block, ISandwichableRenderingHandler renderingHandler)
    {
        registerRenderingHandler(GameRegistry.findUniqueIdentifierFor(block).toString(), renderingHandler);
    }
}
