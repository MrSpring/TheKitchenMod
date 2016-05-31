package dk.mrspring.kitchen;

import dk.mrspring.kitchen.config.*;

public class CommonProxy
{
	public static KitchenConfig kitchenConfig;
	public static KnifeConfig knifeConfig;
	public static OvenConfig ovenConfig;
	public static SandwichableConfig sandwichableConfig;

	public void getConfigs()
	{
		kitchenConfig = ModConfig.getKitchenConfig();
		knifeConfig = ModConfig.getKnifeConfig();
		ovenConfig = ModConfig.getOvenConfig();
		sandwichableConfig= ModConfig.getSandwichConfig();
	}

	public void registerRenderers()
	{
		
	}
}
