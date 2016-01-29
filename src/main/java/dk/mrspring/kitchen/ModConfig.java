package dk.mrspring.kitchen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.config.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class ModConfig
{
    private static BaseConfig[] configs;

    public static void load(File baseFolder, Side side)
    {
        List<String> names = new ArrayList<String>();
        Collections.addAll(names, "Kitchen", "Knife", "Oven", "Sandwichable", "Combo");
        if (side.isClient()) names.add("Client");
        configs = new BaseConfig[names.size()];
        File configFolder = new File(baseFolder, "TheKitchenMod");

        for (int i = 0; i < configs.length; i++)
        {
            try
            {
                String name = names.get(i);
                Class configClass = Class.forName("dk.mrspring.kitchen.config." + name + "Config");
                File configFile = new File(configFolder, name + ".json");
                ModLogger.print(ModLogger.INFO, "Loading " + name + " config from JSON at " + configFile.getPath());
                BaseConfig config = (BaseConfig) configClass.getConstructor(File.class, String.class).newInstance(configFile, name);
                configs[i] = config.readFromFile();
                configs[i].writeToFile();
                configs[i].addDefaults();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static KitchenConfig getKitchenConfig()
    {
        return (KitchenConfig) configs[0];
    }

    public static KnifeConfig getKnifeConfig()
    {
        return (KnifeConfig) configs[1];
    }

    public static OvenConfig getOvenConfig()
    {
        return (OvenConfig) configs[2];
    }

    public static SandwichableConfig getSandwichConfig()
    {
        return (SandwichableConfig) configs[3];
    }

    public static ComboConfig getComboConfig()
    {
        return (ComboConfig) configs[4];
    }

    @SideOnly(Side.CLIENT)
    public static ClientConfig getClientConfig()
    {
        return (ClientConfig) configs[configs.length - 1];
    }
}
