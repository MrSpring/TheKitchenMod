package dk.mrspring.kitchen.api;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.event.BoardEventRegistry;
import dk.mrspring.kitchen.api.event.IBoardEvent;
import dk.mrspring.kitchen.config.SandwichableConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by MrSpring on 03-11-2014 for TheKitchenMod.
 */
public class KitchenRegistry
{
    public static void registerOnAddedToBoardEvent(Item item, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerOnAddedEvent(item, eventHandler);
    }

    public static void registerOnAddedToBoardEvent(Block block, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerOnAddedEvent(block, eventHandler);
    }


    public static void registerOnBoardRightClickEvent(Item item, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerOnRightClickedEvent(item, eventHandler);
    }

    public static void registerOnBoardRightClickEvent(Block block, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerOnRightClickedEvent(block, eventHandler);
    }


    public static void registerTopItemBoardEvent(Item item, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerTopItemEvent(item, eventHandler);
    }

    public static void registerTopItemBoardEvent(Block block, IBoardEvent eventHandler)
    {
        BoardEventRegistry.registerTopItemEvent(block, eventHandler);
    }


    public static void makeSandwichable(SandwichableConfig.SandwichableEntry entry)
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


    public static void linkItemToIngredient(Item item, String ingredientName)
    {
        KitchenItems.linkToIngredient(item, ingredientName);
    }

    public static void linkItemToIngredient(String itemName, String ingredientName)
    {
        KitchenItems.linkToIngredient(itemName, ingredientName);
    }
}
