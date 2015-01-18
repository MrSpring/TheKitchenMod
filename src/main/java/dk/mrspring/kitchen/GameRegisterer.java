package dk.mrspring.kitchen;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by MrSpring on 18-01-2015 for TheKitchenMod.
 */
public class GameRegisterer
{
    public static void registerItem(Item item, String itemName, ModelResourceLocation modelResourceLocation)
    {
        GameRegistry.registerItem(item, itemName);
        TheKitchenMod.proxy.registerItemRenderer(item, 0, modelResourceLocation);
    }

    public static void registerItem(Item item, String itemName, String modelLocation)
    {
        registerItem(item, itemName, new ModelResourceLocation(ModInfo.MOD_ID + ":" + modelLocation, "inventory"));
    }


}
