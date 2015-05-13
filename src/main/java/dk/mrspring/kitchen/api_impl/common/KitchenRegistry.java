package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.IKitchenRegistry;
import dk.mrspring.kitchen.api.RecipeType;
import dk.mrspring.kitchen.api.ingredient.Ingredient;
import dk.mrspring.kitchen.api.ingredient.IngredientRegistry;
import dk.mrspring.kitchen.api.stack.LinkedStack;
import dk.mrspring.kitchen.config.SandwichableConfig;
import dk.mrspring.kitchen.pan.Jam;
import dk.mrspring.kitchen.pan.JamBaseRenderingHandler;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import dk.mrspring.kitchen.recipe.OvenRecipes;
import dk.mrspring.kitchen.recipe.ToasterRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 03-11-2014 for TheKitchenMod.
 */
public class KitchenRegistry implements IKitchenRegistry
{
    String registrant;

    public KitchenRegistry(String modThatIsRegistering)
    {
        this.registrant = modThatIsRegistering;
    }

    public void makeSandwichable(ItemStack itemStack, int healthAmount, boolean isBread, boolean hideInformation, boolean dropItem)
    {
        SandwichableRegistry.getInstance().makeItemSandwichable(itemStack, healthAmount, isBread, hideInformation, dropItem);
    }

    public String addJam(String jamName, Item jamItem, int jamColor)
    {
        Jam newJam = new Jam(jamName, jamColor, jamItem);
        Jam.registerJam(newJam);
        IngredientRegistry.getInstance().registerIngredient(new Ingredient("jam_" + jamName, new JamBaseRenderingHandler(jamColor), jamName));
        return "jam_" + jamName;
    }

    public void linkItemToIngredient(String ingredientName, ItemStack linked)
    {
        if (IngredientRegistry.getInstance().isIngredientRegistered(ingredientName))
            IngredientRegistry.getInstance().linkToIngredient(new LinkedStack(linked, ingredientName));
    }

    public void addRecipe(String recipeType, ItemStack input, ItemStack output)
    {
        try
        {
            RecipeType type = RecipeType.valueOf(recipeType);
            addRecipe(type, input, output);
        } catch (IllegalArgumentException e)
        {
//            ModLogger.print(ModLogger.ERROR, "Failed to register a recipe. Requested type: " + recipeType, e);
            System.err.println("An error occurred while registering a recipe.");
            System.err.println(String.format("- Registering mod: %1$s", registrant));
            System.err.println(String.format("- Recipe input: %1$s", input.toString()));
            System.err.println(String.format("- Recipe output: %1$s", output.toString()));
            System.err.println(String.format("- Recipe type: %1$s", recipeType));
            System.err.println("Error that occurred:");
            e.printStackTrace(System.err);
        }
    }

    public void addRecipe(RecipeType type, ItemStack input, ItemStack output)
    {
        switch (type)
        {
            case KNIFE:
                KnifeRecipes.instance().addRecipe(input, output);
                break;
            case FRYING_PAN:
                IngredientRegistry.registerItemPanRecipe(output, input.getItem(), output.getItem(), input);
                break;
            case OVEN:
                OvenRecipes.instance().addRecipe(input, output);
                break;
            case TOASTER:
                ToasterRecipes.instance().addRecipe(input, output);
                break;
            default:
                ModLogger.print(ModLogger.WARNING, "Unknown recipe type: " + type.toString());
        }
    }
    /*public static void makeSandwichable(SandwichableConfig.SandwichableEntry entry)
    {
        ModConfig.getSandwichConfig().makeSandwichable(entry);
    }

    public static void makeSandwichable(Item item, int healAmount, boolean isBread, boolean hideInformation)
    {
        SandwichableConfig.SandwichableEntry entry = new SandwichableConfig.SandwichableEntry(GameRegistry.findUniqueIdentifierFor(item).toString(), healAmount, isBread);
        if (hideInformation)
            entry.hideInformation();
        makeSandwichable(entry);
    }

    public static void makeSandwichable(Item item, int healAmount, boolean isBread)
    {
        makeSandwichable(item, healAmount, isBread, false);
    }

    public static void makeSandwichable(Item item, int healAmount)
    {
        makeSandwichable(item, healAmount, false);
    }


    public static void linkItemToIngredient(Item item, int metadata, String ingredientName)
    {
        IngredientRegistry.getInstance().linkToIngredient(new IngredientRegistry.Stack(item, metadata), ingredientName);
    }

    public static void linkItemToIngredient(Item item, String ingredientName)
    {
        linkItemToIngredient(item, -1, ingredientName);
    }*/
}
