package dk.mrspring.kitchen;

import net.minecraft.util.ResourceLocation;

public class ModInfo
{
    // The mod-id of the Mod
    public static final String modid = "kitchen";
    // The name of the Mod
    public static final String name = "Kitchen";
    // The version of the Mod
    public static final String version = "1.3.19";

    public static String toTexture(String name)
    {
        return modid + ":" + name;
    }

    public static ResourceLocation toLocation(String name)
    {
        return new ResourceLocation(modid, name);
    }
}
