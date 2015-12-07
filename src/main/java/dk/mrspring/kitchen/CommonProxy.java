package dk.mrspring.kitchen;

import dk.mrspring.kitchen.config.*;
import net.minecraft.world.World;

public class CommonProxy
{
    public static KitchenConfig kitchenConfig;
    public static KnifeConfig knifeConfig;
    public static OvenConfig ovenConfig;
    public static SandwichableConfig sandwichableConfig;
    public static ComboConfig comboConfig;
    public static ToasterConfig toasterConfig;

    public void getConfigs()
    {
        kitchenConfig = ModConfig.getKitchenConfig();
        knifeConfig = ModConfig.getKnifeConfig();
        ovenConfig = ModConfig.getOvenConfig();
        sandwichableConfig= ModConfig.getSandwichConfig();
        comboConfig = ModConfig.getComboConfig();
        toasterConfig = ModConfig.getToasterConfig();
    }

    public String versionHighlights = "";

    public void registerRenderers()
    {

    }

    public void spawnDingParticle(World world, double posX, double posY, double posZ)
    {
    }
}
