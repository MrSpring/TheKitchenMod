package dk.mrspring.kitchen;

import com.google.gson.Gson;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.api.KitchenRegistry;
import dk.mrspring.kitchen.config.ComboConfig;
import dk.mrspring.kitchen.config.SandwichableConfig;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.ItemBaseRenderingHandler;
import dk.mrspring.kitchen.pan.Jam;
import dk.mrspring.kitchen.pan.JamBaseRenderingHandler;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Method;

/**
 * Created by MrSpring on 04-11-2014.
 */
public class IMCHandler
{
    public static void handleMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.key.equalsIgnoreCase("linkItemAndIngredient"))
            handleLinkMessage(message);
        else if (message.key.equalsIgnoreCase("makeItemSandwichable"))
            handleMakeSandwichableMessage(message);
        else if (message.key.equalsIgnoreCase("addOvenRecipe"))
            handleOvenRecipeMessage(message);
        else if (message.key.equalsIgnoreCase("addPanRecipe"))
            handlePanRecipeMessage(message);
        else if (message.key.equalsIgnoreCase("addJam"))
            handleJamMessage(message);
        else if (message.key.equalsIgnoreCase("addSandwichCombo"))
            handleComboMessage(message);
    }

    public static void handleComboMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isStringMessage())
        {
            String jsonCode = message.getStringValue();

            Gson gson = new Gson();
            ComboConfig.SandwichCombo combo = gson.fromJson(jsonCode, ComboConfig.SandwichCombo.class);

            if (combo != null)
                ModConfig.getComboConfig().registerCombo(combo);
        }
    }

    public static void handleLinkMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isStringMessage())
        {
            String messageText = message.getStringValue();
            if (messageText.contains(","))
            {
                String itemName = messageText.split(",")[0];
                String ingredientName = messageText.split(",")[1];

                KitchenRegistry.linkItemToIngredient(GameRegistry.findItem(itemName.split(":")[0], itemName.split(":")[1]), ingredientName);
            }
        } else if (message.isNBTMessage())
        {
            NBTTagCompound infoCompound = message.getNBTValue();
            String ingredientName;
            String itemName;
            if (infoCompound.hasKey("IngredientName", 8))
            {
                ingredientName = infoCompound.getString("IngredientName");
            } else return;

            if (infoCompound.hasKey("Item", 8))
            {
                itemName = infoCompound.getString("Item");
                if (!itemName.contains(":"))
                    return;
            } else if (infoCompound.hasKey("Item", 10))
            {
                NBTTagCompound itemCompound = infoCompound.getCompoundTag("Item");

                if (itemCompound == null)
                    return;

                ItemStack compoundItem = ItemStack.loadItemStackFromNBT(itemCompound);
                if (compoundItem == null)
                    return;

                itemName = GameRegistry.findUniqueIdentifierFor(compoundItem.getItem()).toString();
            } else return;

            KitchenRegistry.linkItemToIngredient(itemName, ingredientName);
        }
    }

    public static void handleMakeSandwichableMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isStringMessage())
        {
            String jsonCode = message.getStringValue();

            Gson gson = new Gson();
            SandwichableConfig.SandwichableEntry entry = gson.fromJson(jsonCode, SandwichableConfig.SandwichableEntry.class);
            ModConfig.getSandwichConfig().makeSandwichable(entry);
        } else if (message.isNBTMessage())
        {
            NBTTagCompound entryCompound = message.getNBTValue();
            String itemName;
            int healAmount;
            boolean isBread = false;
            boolean hideInformation = false;
            if (entryCompound.hasKey("Item", 8))
            {
                itemName = entryCompound.getString("Item");
            } else if (entryCompound.hasKey("Item", 10))
            {
                NBTTagCompound itemCompound = entryCompound.getCompoundTag("Item");
                if (itemCompound == null)
                    return;
                ItemStack compoundItem = ItemStack.loadItemStackFromNBT(itemCompound);
                if (compoundItem == null)
                    return;
                itemName = GameRegistry.findUniqueIdentifierFor(compoundItem.getItem()).toString();
            } else return;

            if (entryCompound.hasKey("HealAmount", 3))
                healAmount = entryCompound.getInteger("HealAmount");
            else return;
            if (entryCompound.hasKey("IsBread", 1))
                isBread = entryCompound.getBoolean("IsBread");
            if (entryCompound.hasKey("HideInformation", 1))
                hideInformation = entryCompound.getBoolean("HideInformation");

            SandwichableConfig.SandwichableEntry entry = new SandwichableConfig.SandwichableEntry(itemName, healAmount, isBread);
            if (hideInformation)
                entry.hideInformation();
            ModConfig.getSandwichConfig().makeSandwichable(entry);
        }
    }

    public static void handleOvenRecipeMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isNBTMessage())
        {
            NBTTagCompound messageCompound = message.getNBTValue();

            ItemStack input;
            ItemStack output;

            if (messageCompound.hasKey("Input", 10))
            {
                NBTTagCompound inputCompound = messageCompound.getCompoundTag("Input");
                input = ItemStack.loadItemStackFromNBT(inputCompound);
            } else return;

            if (messageCompound.hasKey("Output", 10))
            {
                NBTTagCompound outputCompound = messageCompound.getCompoundTag("Output");
                output = ItemStack.loadItemStackFromNBT(outputCompound);
            } else return;

            OvenRecipes.addRecipe(input, output);
        } else if (message.isStringMessage())
        {
            String messageText = message.getStringValue();
            if (messageText.contains(","))
            {
                String inputName = messageText.split(",")[0];
                String outputName = messageText.split(",")[1];

                if (!inputName.contains(":") || !outputName.contains(":"))
                    return;

                ItemStack input = new ItemStack(GameRegistry.findItem(inputName.split(":")[0], inputName.split(":")[1]));
                ItemStack output = new ItemStack(GameRegistry.findItem(outputName.split(":")[0], outputName.split(":")[1]));

                OvenRecipes.addRecipe(input, output);
            }
        }
    }

    public static void handlePanRecipeMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isNBTMessage())
        {
            NBTTagCompound messageCompound = message.getNBTValue();

            ItemStack input;
            ItemStack output;

            if (messageCompound.hasKey("Input", 10))
            {
                NBTTagCompound inputCompound = messageCompound.getCompoundTag("Input");
                input = ItemStack.loadItemStackFromNBT(inputCompound);
            } else return;

            if (messageCompound.hasKey("Output", 10))
            {
                NBTTagCompound outputCompound = messageCompound.getCompoundTag("Output");
                output = ItemStack.loadItemStackFromNBT(outputCompound);
            } else return;

            String ingredientName = GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + "-" + GameRegistry.findUniqueIdentifierFor(output.getItem()).toString();

            Ingredient.registerIngredient(new Ingredient(ingredientName, new ItemBaseRenderingHandler(input, output), output));
            KitchenItems.linkToIngredient(input.getItem(), ingredientName);
        } else if (message.isStringMessage())
        {
            String messageText = message.getStringValue();
            if (messageText.contains(","))
            {
                String inputName = messageText.split(",")[0];
                String outputName = messageText.split(",")[1];

                if (!inputName.contains(":") || !outputName.contains(":"))
                    return;

                ItemStack input = new ItemStack(GameRegistry.findItem(inputName.split(":")[0], inputName.split(":")[1]));
                ItemStack output = new ItemStack(GameRegistry.findItem(outputName.split(":")[0], outputName.split(":")[1]));

                String ingredientName = GameRegistry.findUniqueIdentifierFor(input.getItem()).toString() + "-" + GameRegistry.findUniqueIdentifierFor(output.getItem()).toString();

                Ingredient.registerIngredient(new Ingredient(ingredientName, new ItemBaseRenderingHandler(input, output), output));
                KitchenItems.linkToIngredient(input.getItem(), ingredientName);
            }
        }
    }

    public static void handleJamMessage(FMLInterModComms.IMCMessage message)
    {
        if (message.isNBTMessage())
        {
            NBTTagCompound messageCompound = message.getNBTValue();

            String jamName;
            int color;
            String itemName;

            if (messageCompound.hasKey("JamName", 8))
                jamName = messageCompound.getString("JamName");
            else return;
            if (messageCompound.hasKey("Color", 3))
                color = messageCompound.getInteger("Color");
            else return;
            if (messageCompound.hasKey("Item", 8))
                itemName = messageCompound.getString("Item");
            else if (messageCompound.hasKey("Item", 10))
            {
                NBTTagCompound itemCompound = messageCompound.getCompoundTag("ItemName");
                ItemStack stackFromCompound = ItemStack.loadItemStackFromNBT(itemCompound);
                itemName = GameRegistry.findUniqueIdentifierFor(stackFromCompound.getItem()).toString();
            } else return;

            float red = ((color >> 16) & 0xFF);
            float green = ((color >> 8) & 0xFF);
            float blue = (color & 0xFF);

            Jam jam = new Jam(jamName, color, itemName);
            Jam.registerJam(jam);

            String ingredientName = itemName + "-" + "jam_" + jamName;
            Ingredient ingredient = new Ingredient(ingredientName, new JamBaseRenderingHandler(new float[]{(red * 255), (green * 255), (blue * 255)}), jamName);
            Ingredient.registerIngredient(ingredient);

            KitchenItems.linkToIngredient(itemName, ingredientName);
        }
    }
}
