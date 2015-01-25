package dk.mrspring.kitchen;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.item.*;
import dk.mrspring.kitchen.pan.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;

public class KitchenItems
{
    // A HashMap storing the name if the item (key) and the name of the associated ingredient (value).
    private static HashMap<String, String> ingredientRelations = new HashMap<String, String>();

    // All the Item variables
    public static final Item knife = new ItemKnife().setMaxStackSize(1);
    public static final Item mixing_bowl = new ItemMixingBowl("mixing_bowl");
    public static final Item mortar_and_pestle = new ItemMandP().setMaxStackSize(1);
    public static final Item mortar = new ItemBase("mortar", true);
    public static final Item pestle = new ItemBase("pestle", true);
    public static final Item jam_jar = new ItemJamJar("jam_jar");

    public static final Item raw_bacon = new ItemBase("bacon_raw", Kitchen.instance.foodTab);
    public static final Item bacon = new ItemBase("bacon_cooked", Kitchen.instance.foodTab);

    public static final Item raw_roast_beef = new ItemBase("raw_roast_beef", ModInfo.toTexture("beef_slice"), Kitchen.instance.foodTab);
    public static final Item roast_beef = new ItemBase("roast_beef", Kitchen.instance.foodTab);

    public static final Item raw_chicken_fillet = new ItemBase("chicken_fillet_raw", Kitchen.instance.foodTab);
    public static final Item chicken_fillet = new ItemBase("chicken_fillet_cooked", Kitchen.instance.foodTab);

    public static final Item bread_slice = new ItemBase("bread_slice", Kitchen.instance.foodTab);
    public static final Item flour = new ItemBase("flour", Kitchen.instance.foodTab);
    public static final Item toast = new ItemBase("toast", Kitchen.instance.foodTab);
    public static final Item toasted_toast = new ItemFoodBase("toasted_toast", 3, false, Kitchen.instance.foodTab);

    public static final Item tomato = new ItemSeedBase("tomato", KitchenBlocks.tomato_crop, Kitchen.instance.foodTab);
    public static final Item tomato_slice = new ItemBase("tomato_slice", Kitchen.instance.foodTab);

    public static final Item lettuce = new ItemSeedBase("lettuce", KitchenBlocks.lettuce_crop, Kitchen.instance.foodTab);
    public static final Item lettuce_leaf = new ItemBase("lettuce_leaf", Kitchen.instance.foodTab);

    public static final Item peanut = new ItemSeedBase("peanut", KitchenBlocks.peanut_crop, Kitchen.instance.foodTab);
    public static final Item peanuts_in_shell = new ItemBase("peanuts_in_shell", ModInfo.toTexture("peanuts"), Kitchen.instance.foodTab);

    public static final Item strawberry = new ItemSeedBase("strawberry", KitchenBlocks.strawberry_crop, Kitchen.instance.foodTab);
    public static final Item jammable_strawberry = new ItemFoodBase("sugared_strawberry_slices", ModInfo.toTexture("strawberry_slices_sugared"), 1, false, Kitchen.instance.foodTab);
    public static final Item cut_strawberry = new ItemFoodBase("strawberry_slices", 1, false, Kitchen.instance.foodTab);

    public static final Item potato_slice = new ItemBase("potato_slice", Kitchen.instance.foodTab);
    public static final Item carrot_slice = new ItemBase("carrot_slice", Kitchen.instance.foodTab);
    public static final Item cut_apple = new ItemFoodBase("apple_slice", 1, false, Kitchen.instance.foodTab);

    public static final Item cheese = new ItemFoodBase("cheese", 3, false, Kitchen.instance.foodTab);
    public static final Item cheese_slice = new ItemBase("cheese_slice", Kitchen.instance.foodTab);

    public static ItemStack basic_sandwich = getSandwichItemStackWithNBTTags(new ItemStack[]{new ItemStack(bread_slice, 1, 0), new ItemStack(raw_bacon, 1, 0), new ItemStack(bread_slice, 1, 0)});

    public static final Item jam_strawberry = new ItemBase("strawberry_jam", false).setLocalizableName("jam.strawberry.name");
    public static final Item jam_apple = new ItemBase("apple_jam", false).setLocalizableName("jam.apple.name");
    public static final Item jam_peanut = new ItemBase("peanut_jam", false).setLocalizableName("jam.peanut.name");
    public static final Item jam_cocoa = new ItemBase("cocoa_jam", false).setLocalizableName("jam.cocoa.name");

    public static final Item waffle = new ItemIceCreamableBase("waffle", 5, false, Kitchen.instance.foodTab);
    public static final Item burnt_waffle = new ItemFoodBase("burnt_waffle", 1, false, Kitchen.instance.foodTab);
    public static final Item pancake = new ItemIceCreamableBase("pancake", 4, false, Kitchen.instance.foodTab);

    public static final Item butter = new ItemBase("butter", Kitchen.instance.foodTab);
    public static final Item burnt_meat = new ItemFoodBase("burnt_meat", 1, false, Kitchen.instance.foodTab);
    public static final Item chicken_leg = new ItemFoodBase("chicken_leg", 4, true, Kitchen.instance.foodTab);
    public static final Item timer = new ItemBase("timer", true);
    public static final Item cooking_book = new ItemCookingBook();

    public static final Item crushed_ice = new ItemBase("crushed_ice", Kitchen.instance.foodTab);

    public static final Item raw_vanilla = new ItemSeedBase("raw_vanilla", KitchenBlocks.vanilla_crop, Kitchen.instance.foodTab);
    public static final Item dried_vanilla = new ItemFoodBase("dried_vanilla", 3, false).setCreativeTab(Kitchen.instance.foodTab);
    public static final Item crushed_vanilla = new ItemFoodBase("crushed_vanilla", 1, false).setCreativeTab(Kitchen.instance.foodTab);

    public static final Item raw_burger_bun = new ItemBase("raw_burger_bun", Kitchen.instance.foodTab);
    public static final Item burger_bun = new ItemFoodBase("burger_bun", 2, false, Kitchen.instance.foodTab);
    public static final Item sliced_burger_bun = new ItemFoodBase("sliced_burger_bun", 1, false, Kitchen.instance.foodTab);

    public static final Item fried_egg = new ItemFoodBase("fried_egg", 3, false, Kitchen.instance.foodTab);

    // Pre-loads the sandwich ItemStack with some NBT-Data.
    private static ItemStack getSandwichItemStackWithNBTTags(ItemStack[] layers)
    {
        ItemSandwich sandwich = new ItemSandwich();
        GameRegistry.registerItem(sandwich, "sandwich");

        ItemStack basicSandwich = new ItemStack(sandwich, 1, 0);

        NBTTagList layersList = new NBTTagList();

        for (ItemStack layer : layers)
        {
            NBTTagCompound layerCompound = new NBTTagCompound();
            layer.writeToNBT(layerCompound);
            layersList.appendTag(layerCompound);
        }

        basicSandwich.setTagInfo("SandwichLayers", layersList);

        return basicSandwich;
    }

    public static void linkToIngredient(Item item, String ingredientName)
    {
        if (item != null)
        {
            GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(item);
            if (identifier != null)
                linkToIngredient(identifier.toString(), ingredientName);
        }
    }

    public static void linkToIngredient(String itemName, String ingredientName)
    {
        if (!ingredientRelations.containsKey(itemName))
        {
            ModLogger.print(ModLogger.DEBUG, "Registering: " + itemName + " to ingredient: " + ingredientName);
            ingredientRelations.put(itemName, ingredientName);
        } else
            ModLogger.print(ModLogger.DEBUG, "Tried to register: " + itemName + ", but it is already bound to: " + ingredientRelations.get(itemName));
    }

    public static Ingredient valueOf(String itemName)
    {
        if (ingredientRelations.containsKey(itemName))
            return Ingredient.getIngredient(ingredientRelations.get(itemName));
        else return Ingredient.getIngredient("empty");
    }

    public static Ingredient valueOf(Item item)
    {
        return valueOf(GameRegistry.findUniqueIdentifierFor(item).toString());
    }

    public static HashMap<String, String> getIngredientRelations()
    {
        return ingredientRelations;
    }
}
