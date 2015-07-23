package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
import dk.mrspring.kitchen.api.sandwichable.ISandwichableRegistry;
import dk.mrspring.kitchen.config.SandwichableConfig;
import dk.mrspring.kitchen.config.wrapper.JsonItemStack;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dk.mrspring.kitchen.KitchenItems.*;

/**
 * Created by Konrad on 13-05-2015.
 */
public class SandwichableRegistry implements ISandwichableRegistry
{
    private static SandwichableRegistry ourInstance = new SandwichableRegistry();
    private List<ISandwichable> sandwichableItems = new ArrayList<ISandwichable>();

    private SandwichableRegistry()
    {
        // Adds all default Sandwichable items.
        this.makeItemSandwichable(bread_slice, 0, true);
        this.makeItemSandwichable(sliced_burger_bun, 1, true);
        this.makeItemSandwichable(toast, 0, true);
        this.makeItemSandwichable(toasted_toast, 1, true);
        this.makeItemSandwichable(bacon, 2, false);
        this.makeItemSandwichable(raw_bacon, 1, false);
        this.makeItemSandwichable(tomato_slice, 1, false);
        this.makeItemSandwichable(lettuce_leaf, 2, false);
        this.makeItemSandwichable(potato_slice, 2, false);
        this.makeItemSandwichable(carrot_slice, 2, false);
        this.makeItemSandwichable(roast_beef, 4, false);
        this.makeItemSandwichable(raw_roast_beef, 1, false);
        this.makeItemSandwichable(chicken_fillet, 4, false);
        this.makeItemSandwichable(raw_chicken_fillet, 1, false);
        this.makeItemSandwichable(cheese_slice, 3, false);
        this.makeItemSandwichable(butter, 1, false); // TODO: Add butter board handler
        this.makeItemSandwichable(jam_strawberry, 2, false, false, false);
        this.makeItemSandwichable(jam_apple, 2, false, false, false);
        this.makeItemSandwichable(jam_peanut, 2, false, false, false);
        this.makeItemSandwichable(jam_cocoa, 2, false, false, false);
        this.makeItemSandwichable(jam_ketchup, 2, false, false, false);
        this.makeItemSandwichable(raw_cut_fish, 1, false);
        this.makeItemSandwichable(cooked_cut_fish, 3, false);
        this.makeItemSandwichable(ham_slice, 3, false);
        this.makeItemSandwichable(onion_slice, 2, false);
        this.makeItemSandwichable(raw_meat_patty, 1, false);
        this.makeItemSandwichable(cooked_meat_patty, 4, false);
        this.registerSandwichable(new JamSandwichable(jam_strawberry, "strawberry", 2));
        this.registerSandwichable(new JamSandwichable(jam_apple, "apple", 2));
        this.registerSandwichable(new JamSandwichable(jam_peanut, "peanut", 2));
        this.registerSandwichable(new JamSandwichable(jam_cocoa, "cocoa", 2));
        this.registerSandwichable(new JamSandwichable(jam_ketchup, "ketchup", 2));
        this.makeItemSandwichable(fried_egg, 3, false);
    }

    public static SandwichableRegistry getInstance()
    {
        return ourInstance;
    }

    public void loadFromConfig(SandwichableConfig config)
    {
        List<SandwichableConfig.SandwichableEntry> fromConfig = config.getSandwichableItems();

        for (SandwichableConfig.SandwichableEntry entry : fromConfig)
        {
            JsonItemStack itemStack = entry.getInput();
            ItemStack asItemStack = itemStack.toItemStack();
            if (asItemStack != null)
                this.makeItemSandwichable(asItemStack, entry.getHealAmount(), entry.isBread(), entry.showInformation(), entry.dropItem());
        }
    }

    @Override
    public ISandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread, boolean showInformation, boolean dropItem)
    {
        Sandwichable entry = new Sandwichable(stack, heal, isBread, !showInformation, dropItem);
        sandwichableItems.add(entry);
        ModLogger.print(ModLogger.DEBUG, "Registering Sandwichable: " + entry.toString());
        return entry;
    }

    @Override
    public void registerSandwichable(ISandwichable sandwichable)
    {
        this.sandwichableItems.add(sandwichable);
    }

    public ISandwichable makeItemSandwichable(Item item, int heal, boolean isBread, boolean showInformation, boolean dropItem)
    {
        return this.makeItemSandwichable(new ItemStack(item), heal, isBread, showInformation, dropItem);
    }

    public ISandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread)
    {
        return this.makeItemSandwichable(stack, heal, isBread, true, true);
    }

    public ISandwichable makeItemSandwichable(Item item, int heal, boolean isBread)
    {
        return this.makeItemSandwichable(new ItemStack(item), heal, isBread);
    }

    @Override
    public ISandwichable getSandwichableForItem(ItemStack stack)
    {
        for (ISandwichable sandwichable : sandwichableItems)
            if (sandwichable.doesStackMatch(stack))
                return sandwichable;
        return null;
    }

    @Override
    public boolean isSandwichable(ItemStack stack)
    {
        return getSandwichableForItem(stack) != null;
    }

    /**
     * A simple container class that contains all the Sandwich information for the Stack.
     */
    public static class Sandwichable implements ISandwichable
    {
        public ItemStack stack;
        public int healAmount;
        public boolean isBread, hideInformation, dropItem;

        public Sandwichable(ItemStack stack, int healAmount, boolean isBread, boolean hideInformation, boolean dropItem)
        {
            this.stack = stack;
            this.healAmount = healAmount;
            this.isBread = isBread;
            this.hideInformation = hideInformation;
            this.dropItem = dropItem;
        }

        public Sandwichable(ItemStack stack)
        {
            this.stack = stack;
        }

        @Override
        public int getHealAmount()
        {
            return healAmount;
        }

        @Override
        public Sandwichable setHealAmount(int newHealAmount)
        {
            this.healAmount = newHealAmount;
            return this;
        }

        @Override
        public boolean getIsBread()
        {
            return this.isBread;
        }

        @Override
        public Sandwichable setIsBread(boolean newIsBread)
        {
            this.isBread = newIsBread;
            return this;
        }

        @Override
        public boolean getHideInformation()
        {
            return hideInformation;
        }

        @Override
        public Sandwichable setHideInformation(boolean newHideInformation)
        {
            this.hideInformation = newHideInformation;
            return this;
        }

        @Override
        public boolean getShowInformation()
        {
            return !this.getHideInformation();
        }

        @Override
        public Sandwichable setShowInformation(boolean showInformation)
        {
            this.hideInformation = !showInformation;
            return this;
        }

        @Override
        public boolean getDropItem()
        {
            return this.dropItem;
        }

        @Override
        public Sandwichable setDropItem(boolean newDropItem)
        {
            this.dropItem = newDropItem;
            return this;
        }

        @Override
        public boolean doesStackMatch(ItemStack stack)
        {
            return ItemUtils.areStacksEqual(stack, getStack(), false);
        }

        @Override
        public ItemStack getBoardStack(ItemStack original)
        {
            return getStack();
        }

        @Override
        public ItemStack getStack()
        {
            return this.stack;
        }

        @Override
        public Sandwichable setStack(ItemStack newStack)
        {
            this.stack = newStack;
            return this;
        }

        @Override
        public void onAdded(ItemStack added)
        {
            added.stackSize--;
        }

        @Override
        public String toString()
        {
            return "stack=" + getStack().toString() + ", healAmount=" + healAmount + ", isBread=" + getIsBread() + ", hideInformation=" + getHideInformation() + ", dropItem=" + this.getDropItem();
        }
    }

    public static class JamSandwichable extends Sandwichable
    {
        ItemStack boardStack;

        public JamSandwichable(Item boardItem, String jam, int heal)
        {
            this(new ItemStack(boardItem), jam, heal);
        }

        public JamSandwichable(ItemStack boardStack, String jam, int heal)
        {
            this(boardStack, ItemJamJar.getJamJarStack(jam, 1), heal);
        }

        public JamSandwichable(ItemStack boardStack, ItemStack stack, int heal)
        {
            super(stack);
            this.boardStack = boardStack;
            this.setHideInformation(true);
            this.setDropItem(false);
            this.setHealAmount(heal);
        }

        @Override
        public ItemStack getBoardStack(ItemStack original)
        {
            ItemStack copy = boardStack.copy();
            copy.stackSize = 1;
            return copy;
        }

        @Override
        public void onAdded(ItemStack added)
        {
            ItemJamJar.reduceUsesLeft(added, 1);
        }
    }
}
