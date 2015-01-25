package dk.mrspring.kitchen;

import dk.mrspring.kitchen.config.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class ModConfig
{
    private static BaseConfig[] configs;

    public static void load(File baseFolder)
    {
        String base = baseFolder.getPath();
        String[] names = new String[]{"Kitchen", "Knife", "Oven", "Sandwichable", "Combo", "Toaster"};
        configs = new BaseConfig[names.length];

        for (int i = 0; i < configs.length; i++)
        {
            try
            {
                Class configClass = Class.forName("dk.mrspring.kitchen.config." + names[i] + "Config");
                String locationForJson = base + File.separator + "TheKitchenMod" + File.separator + names[i] + ".json";
                ModLogger.print(ModLogger.INFO, "Loading " + names[i] + " from JSON @ " + locationForJson);
                BaseConfig config = (BaseConfig) configClass.getConstructor(File.class, String.class).newInstance(new File(locationForJson), names[i]);
                configs[i] = config.readFromFile();
                configs[i].writeToFile();
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

    public static ToasterConfig getToasterConfig()
    {
        return (ToasterConfig) configs[5];
    }
}
