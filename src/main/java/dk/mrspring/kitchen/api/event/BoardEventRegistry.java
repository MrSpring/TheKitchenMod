package dk.mrspring.kitchen.api.event;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public class BoardEventRegistry
{
    static Map<String, IBoardEvent> onAddedToBoardEvents = new HashMap<String, IBoardEvent>();
    static Map<String, IBoardEvent> onBoardRightClickedEvents = new HashMap<String, IBoardEvent>();
    static Map<String, IBoardEvent> topItemEvents = new HashMap<String, IBoardEvent>();

    static IOnAddedToBoardEvent defaultOnAddedToBoardEvent = new IOnAddedToBoardEvent()
    {
        @Override
        public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo)
        {

        }

        @Override
        public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo)
        {
            return true;
        }

        @Override
        public String getEventName()
        {
            return "on_added-default";
        }
    };
    static IOnBoardRightClickedEvent defaultOnBoardRightClickedEvent = new IOnBoardRightClickedEvent()
    {
        @Override
        public void onRightClicked(List<ItemStack> layers, ItemStack rightClicked, NBTTagCompound specialTagInfo)
        {
        }

        @Override
        public String getEventName()
        {
            return "on_right_click-default";
        }
    };
    static ITopItemEvent defaultTopItemEvent = new ITopItemEvent()
    {
        @Override
        public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo)
        {
            return true;
        }

        @Override
        public String getEventName()
        {
            return "top_item-default";
        }
    };

    public static void registerTopItemEvent(String itemName, IBoardEvent event)
    {
        if (itemName != null && !topItemEvents.containsKey(itemName))
            topItemEvents.put(itemName, event);
    }

    public static void registerTopItemEvent(Item item, IBoardEvent event)
    {
        registerTopItemEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
    }

    public static void registerTopItemEvent(Block block, IBoardEvent event)
    {
        registerTopItemEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
    }


    public static void registerOnRightClickedEvent(String itemName, IBoardEvent event)
    {
        if (itemName != null && !onBoardRightClickedEvents.containsKey(itemName))
            onBoardRightClickedEvents.put(itemName, event);
    }

    public static void registerOnRightClickedEvent(Item item, IBoardEvent event)
    {
        registerOnRightClickedEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
    }

    public static void registerOnRightClickedEvent(Block block, IBoardEvent event)
    {
        registerOnRightClickedEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
    }


    public static void registerOnAddedEvent(String itemName, IBoardEvent event)
    {
        if (itemName != null && !onAddedToBoardEvents.containsKey(itemName))
            onAddedToBoardEvents.put(itemName, event);
    }

    public static void registerOnAddedEvent(Item item, IBoardEvent event)
    {
        registerOnAddedEvent(GameRegistry.findUniqueIdentifierFor(item).toString(), event);
    }

    public static void registerOnAddedEvent(Block block, IBoardEvent event)
    {
        registerOnAddedEvent(GameRegistry.findUniqueIdentifierFor(block).toString(), event);
    }


    public static IBoardEvent getOnAddedToBoardEventFor(String itemName)
    {
        if (onAddedToBoardEvents.containsKey(itemName))
        {
            System.out.println("Returning an onAddedToBoardEvent for " + itemName);
            return onAddedToBoardEvents.get(itemName);
        } else return getDefaultOnAddedToBoardEvent();
    }

    public static IBoardEvent getOnAddedToBoardEventFor(Item item)
    {
        return getOnAddedToBoardEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
    }


    public static IBoardEvent getOnBoardRightClickedEventFor(String itemName)
    {
        if (onBoardRightClickedEvents.containsKey(itemName))
            return onBoardRightClickedEvents.get(itemName);
        else return getDefaultOnBoardRightClickedEvent();
    }

    public static IBoardEvent getOnBoardRightClickedEventFor(Item item)
    {
        return getOnBoardRightClickedEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
    }


    public static IBoardEvent getTopItemEventFor(String itemName)
    {
        if (topItemEvents.containsKey(itemName))
            return topItemEvents.get(itemName);
        else return getDefaultTopItemEvent();
    }

    public static IBoardEvent getTopItemEventFor(Item item)
    {
        return getTopItemEventFor(GameRegistry.findUniqueIdentifierFor(item).toString());
    }

    public static IOnAddedToBoardEvent getDefaultOnAddedToBoardEvent()
    {
        return defaultOnAddedToBoardEvent;
    }

    public static IOnBoardRightClickedEvent getDefaultOnBoardRightClickedEvent()
    {
        return defaultOnBoardRightClickedEvent;
    }

    public static ITopItemEvent getDefaultTopItemEvent()
    {
        return defaultTopItemEvent;
    }

    public static void registerDefaultEvents()
    {
        
    }
}
