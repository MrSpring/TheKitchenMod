package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemFilter;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.recipe.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Konrad on 11-03-2015.
 */
public class NEIKitchenConfig implements IConfigureNEI
{
    private static final List<Item> BANNED = new ArrayList<Item>();

    static
    {
        BANNED.add(KitchenItems.jam_apple);
        BANNED.add(KitchenItems.jam_cocoa);
        BANNED.add(KitchenItems.jam_peanut);
        BANNED.add(KitchenItems.jam_strawberry);
        BANNED.add(KitchenItems.jam_ketchup);
    }

    @Override
    public void loadConfig()
    {
        ItemStack newSandwich = KitchenItems.basic_sandwich.copy();

        NBTTagList layers = new NBTTagList();
        NBTTagCompound itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bread_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bacon).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.lettuce_leaf).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.tomato_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        itemCompound = new NBTTagCompound();
        new ItemStack(KitchenItems.bread_slice).writeToNBT(itemCompound);
        layers.appendTag(itemCompound);
        newSandwich.setTagInfo("SandwichLayers", layers);

        API.addItemListEntry(newSandwich);
        API.addItemFilter(new ItemFilter.ItemFilterProvider()
        {
            @Override
            public ItemFilter getFilter()
            {
                return new ItemFilter()
                {
                    @Override
                    public boolean matches(ItemStack item)
                    {
                        return !BANNED.contains(item.getItem());
                    }
                };
            }
        });
//        API.registerRecipeHandler(new SimpleCraftingHandler("kitchen.frying_pan", "tile.frying_pan.name", FryingPanRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.frying_pan)));
//        API.registerUsageHandler(new SimpleCraftingHandler("kitchen.frying_pan", "tile.frying_pan.name", FryingPanRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.frying_pan))); // TODO: Add NEI handler interface to for Ingredients

//        API.registerRecipeHandler(new SimpleCraftingHandler("kitchen.oven", "tile.oven.name", OvenRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.oven), false));
//        API.registerUsageHandler(new SimpleCraftingHandler("kitchen.oven", "tile.oven.name", OvenRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.oven), false));

//        API.registerRecipeHandler(new SimpleCraftingHandler("kitchen.toaster", "tile.toaster.name", ToasterRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.toaster)));
//        API.registerUsageHandler(new SimpleCraftingHandler("kitchen.toaster", "tile.toaster.name", ToasterRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.toaster)));

//        API.registerRecipeHandler(new MuffinCupCraftingHandler());
//        API.registerUsageHandler(new MuffinCupCraftingHandler());

//        API.registerRecipeHandler(new WaffleIronCraftingHandler());
//        API.registerUsageHandler(new WaffleIronCraftingHandler());

//        API.registerRecipeHandler(new SimpleCraftingHandler("kitchen.hand_mixer", "item.dirty_hand_mixer.name", Collections.singletonList(recipe), new ItemStack(Blocks.cauldron)));
//        API.registerUsageHandler(new SimpleCraftingHandler("kitchen.hand_mixer", "item.dirty_hand_mixer.name", Collections.singletonList(recipe), new ItemStack(Blocks.cauldron)));
//        GuiCraftingRecipe.craftinghandlers.add();
        ArrayList<ICraftingHandler> handlers = GuiCraftingRecipe.craftinghandlers;
        handlers.add(new SimpleCraftingHandler("kitchen.frying_pan", "tile.frying_pan.name", FryingPanRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.frying_pan)));
        handlers.add(new SimpleCraftingHandler("kitchen.oven", "tile.oven.name", OvenRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.oven), false));
        handlers.add(new SimpleCraftingHandler("kitchen.toaster", "tile.toaster.name", ToasterRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.toaster)));
        handlers.add(new MuffinCupCraftingHandler());
        handlers.add(new WaffleIronCraftingHandler());
        IRecipe recipe = new BasicRecipe(KitchenItems.dirty_hand_mixer, KitchenItems.hand_mixer);
        handlers.add(new SimpleCraftingHandler("kitchen.hand_mixer", "item.dirty_hand_mixer.name", Collections.singletonList(recipe), new ItemStack(Blocks.cauldron)));
    }

    @Override
    public String getName()
    {
        return ModInfo.name;
    }

    @Override
    public String getVersion()
    {
        return ModInfo.version;
    }
}
