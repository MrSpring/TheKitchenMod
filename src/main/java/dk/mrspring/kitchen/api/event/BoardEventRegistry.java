package dk.mrspring.kitchen.api.event;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.pan.Jam;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public class BoardEventRegistry
{
    private static final BoardEventRegistry instance = new BoardEventRegistry();
    private List<IBoardItemHandler> handlers = new ArrayList<IBoardItemHandler>();

    private BoardEventRegistry()
    {
        registerHandler(new SandwichableItemHandler());
        registerHandler(new KnifeItemHandler());
    }

    public static BoardEventRegistry instance()
    {
        return instance;
    }

    public void registerHandler(IBoardItemHandler handler)
    {
        if (handler != null)
            handlers.add(handler);
    }

    public IBoardItemHandler getHandlerFor(TileEntityBoard board, ItemStack item, EntityPlayer player)
    {
        for (IBoardItemHandler handler : handlers)
        {
//            System.out.println("Checking if handler matches. Class: " + handler.getClass().getName());
            if (handler.isForItem(board, item, player))
            {
//                System.out.println("Returning handler of type: " + handler.getClass().getName());
                return handler;
            }
        }
        return new BasicItemHandler();
    }

    /*static Map<String, IBoardEvent> onAddedToBoardEvents = new HashMap<String, IBoardEvent>();
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
        public ItemStack addedToBoard(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
        {
            return added.copy();
        }

        @Override
        public boolean decrementStackSize(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
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
        public ItemStack getDroppeditem(List<ItemStack> layers, ItemStack removed, NBTTagCompound specialTagInfo)
        {
            return removed;
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
            return onAddedToBoardEvents.get(itemName);
        else return getDefaultOnAddedToBoardEvent();
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

    public static IBoardEvent getTopItemEventFor(ItemStack stack)
    {
        if (stack != null)
            return getTopItemEventFor(stack.getItem());
        else return getDefaultTopItemEvent();
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
        registerOnAddedEvent(KitchenItems.butter, new IOnAddedToBoardEvent()
        {
            @Override
            public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                specialTagInfo.setInteger("ClickAmount", 2);
            }

            @Override
            public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo)
            {
                if (currentLayers.size() > 0)
                {
                    if (currentLayers.get(currentLayers.size() - 1).getItem() == KitchenItems.bread_slice)
                    {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public ItemStack addedToBoard(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                return added.copy();
            }

            @Override
            public boolean decrementStackSize(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                return true;
            }

            @Override
            public String getEventName()
            {
                return "on_added-kitchen:butter";
            }
        });
        registerTopItemEvent(KitchenItems.butter, new ITopItemEvent()
        {
            @Override
            public boolean canAddItemOnTop(List<ItemStack> layers, ItemStack tryingToAdd, NBTTagCompound specialTagInfo)
            {
                return specialTagInfo.getInteger("ClickAmount") <= 0;
            }

            @Override
            public ItemStack getDroppeditem(List<ItemStack> layers, ItemStack removed, NBTTagCompound specialTagInfo)
            {
                if (specialTagInfo.getInteger("ClickAmount") == 2)
                    return removed;
                else return null;
            }

            @Override
            public String getEventName()
            {
                return "top_item-kitchen:butter";
            }
        });

        registerOnRightClickedEvent(KitchenItems.knife, new IOnBoardRightClickedEvent()
        {
            @Override
            public void onRightClicked(List<ItemStack> layers, ItemStack rightClicked, NBTTagCompound specialTagInfo)
            {
                if (layers.size() > 0)
                    if (layers.get(layers.size() - 1).getItem() == KitchenItems.butter)
                        if (specialTagInfo.hasKey("ClickAmount"))
                            specialTagInfo.setInteger("ClickAmount", specialTagInfo.getInteger("ClickAmount") - 1);
            }

            @Override
            public String getEventName()
            {
                return "on_right_clicked-kitchen:butter_knife";
            }
        });

        registerOnAddedEvent(KitchenItems.jam_jar, new IOnAddedToBoardEvent()
        {
            @Override
            public void onAdded(List<ItemStack> layers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                if (added.getTagCompound() != null)
                {
                    NBTTagCompound jamInfo = added.getTagCompound().getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        int usesLeft = jamInfo.getInteger("UsesLeft");
                        usesLeft--;
                        if (usesLeft == 0)
                            added.setItemDamage(0);

                        jamInfo.setInteger("UsesLeft", usesLeft);
                        added.setTagInfo("JamInfo", jamInfo);
                    }
                }
            }

            @Override
            public boolean canAdd(List<ItemStack> currentLayers, ItemStack toAdd, NBTTagCompound specialTagInfo)
            {
                if (toAdd.getTagCompound() != null)
                {
                    NBTTagCompound jamInfo = toAdd.getTagCompound().getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        int usesLeft = jamInfo.getInteger("UsesLeft");
                        Jam jam = Jam.getJam(jamInfo.getString("JamType"));

                        return jam != Jam.getJam("empty") && usesLeft != 0;
                    } else return false;
                } else return false;
            }

            @Override
            public ItemStack addedToBoard(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                if (added.getTagCompound() != null)
                {
                    NBTTagCompound jamInfo = added.getTagCompound().getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        Jam jam = Jam.getJam(jamInfo.getString("JamType"));
                        if (jam != Jam.getJam("empty"))
                        {
                            return new ItemStack(jam.getItem(), 1);
                        }
                    }
                }
                return null;
            }

            @Override
            public boolean decrementStackSize(List<ItemStack> currentLayers, ItemStack added, NBTTagCompound specialTagInfo)
            {
                return false;
            }

            @Override
            public String getEventName()
            {
                return "on_added-kitchen:jam_jar";
            }
        });
    }*/
}
