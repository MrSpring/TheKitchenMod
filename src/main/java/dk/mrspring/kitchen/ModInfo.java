package dk.mrspring.kitchen;

import net.minecraft.util.ResourceLocation;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class ModInfo
{
    public static final String MOD_ID = "kitchen";
    public static final String NAME = "The Kitchen Mod";
    public static final String VERSION = "1.4.0";
    public static final String CLIENT_PROXY = "dk.mrspring.kitchen.client.ClientProxy";
    public static final String COMMON_PROXY = "dk.mrspring.kitchen.common.CommonProxy";

    public static String toResource(String name)
    {
        return String.format("%s:%s", MOD_ID, name);
    }

    public static ResourceLocation toLocation(String name)
    {
        return new ResourceLocation(toResource(name));
    }
}
