package dk.mrspring.kitchen.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class OvenConfig extends BaseConfig
{
	public String[] custom_oven_recipes_input = new String[] { "kitchen:raw_roast_beef", "kitchen:chicken_fillet_raw", "kitchen:bacon_raw" };
	public String[] custom_oven_recipes_output = new String[] { "kitchen:roast_beef", "kitchen:chicken_fillet_cooked", "kitchen:bacon_cooked" };

	public OvenConfig(File location, String name)
	{
		super(location, name);
	}
}
