package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.*;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.IRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.recipe.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
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

        register(new SimpleCraftingHandler("kitchen.frying_pan", "tile.frying_pan.name", FryingPanRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.frying_pan)));
        register(new SimpleCraftingHandler("kitchen.oven", "tile.oven.name", OvenRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.oven), false));
        register(new SimpleCraftingHandler("kitchen.toaster", "tile.toaster.name", ToasterRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.toaster)));
        register(new KnifeCraftingHandler());//(new SimpleCraftingHandler("kitchen.knife", "tile.board.name", KnifeRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.board)));
        register(new MuffinCupCraftingHandler());
        register(new WaffleIronCraftingHandler());
        IRecipe recipe = new BasicRecipe(KitchenItems.dirty_hand_mixer, KitchenItems.hand_mixer);
        register(new SimpleCraftingHandler("kitchen.hand_mixer", "item.dirty_hand_mixer.name", Collections.singletonList(recipe), new ItemStack(Items.cauldron)));
    }

    private void register(TemplateRecipeHandler handler)
    {
        GuiCraftingRecipe.craftinghandlers.add(handler);
        GuiUsageRecipe.usagehandlers.add(handler);
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
