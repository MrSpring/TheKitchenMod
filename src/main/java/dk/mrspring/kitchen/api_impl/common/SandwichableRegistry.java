package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
import dk.mrspring.kitchen.api.sandwichable.ISandwichableRegistry;
import dk.mrspring.kitchen.api.stack.Stack;
import dk.mrspring.kitchen.config.SandwichableConfig;
import dk.mrspring.kitchen.config.wrapper.JsonItemStack;

import static dk.mrspring.kitchen.KitchenItems.*;

import dk.mrspring.kitchen.util.StackUtils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class SandwichableRegistry implements ISandwichableRegistry
{
    private static SandwichableRegistry ourInstance = new SandwichableRegistry();
    private List<Sandwichable> sandwichableItems = new ArrayList<Sandwichable>();

    private SandwichableRegistry()
    {
        // Adds all default Sandwichable items.
        this.makeItemSandwichable(new ItemStack(bread_slice), 0, true);
        this.makeItemSandwichable(new ItemStack(sliced_burger_bun), 1, true);
        this.makeItemSandwichable(new ItemStack(toast), 0, true);
        this.makeItemSandwichable(new ItemStack(toasted_toast), 1, true);
        this.makeItemSandwichable(new ItemStack(bacon), 2, false);
        this.makeItemSandwichable(new ItemStack(raw_bacon), 1, false);
        this.makeItemSandwichable(new ItemStack(tomato_slice), 1, false);
        this.makeItemSandwichable(new ItemStack(lettuce_leaf), 2, false);
        this.makeItemSandwichable(new ItemStack(potato_slice), 2, false);
        this.makeItemSandwichable(new ItemStack(carrot_slice), 2, false);
        this.makeItemSandwichable(new ItemStack(roast_beef), 4, false);
        this.makeItemSandwichable(new ItemStack(raw_roast_beef), 1, false);
        this.makeItemSandwichable(new ItemStack(chicken_fillet), 4, false);
        this.makeItemSandwichable(new ItemStack(raw_chicken_fillet), 1, false);
        this.makeItemSandwichable(new ItemStack(cheese_slice), 3, false);
        this.makeItemSandwichable(new ItemStack(butter), 1, false); // TODO: Add butter board handler
        this.makeItemSandwichable(new ItemStack(jam_strawberry), 2, false, false, false);
        this.makeItemSandwichable(new ItemStack(jam_apple), 2, false, false, false);
        this.makeItemSandwichable(new ItemStack(jam_peanut), 2, false, false, false);
        this.makeItemSandwichable(new ItemStack(jam_cocoa), 2, false, false, false);
        this.makeItemSandwichable(new ItemStack(jam_ketchup), 2, false, false, false);
        this.makeItemSandwichable(new ItemStack(raw_cut_fish), 1, false);
        this.makeItemSandwichable(new ItemStack(cooked_cut_fish), 3, false);
        this.makeItemSandwichable(new ItemStack(ham_slice), 3, false);
        this.makeItemSandwichable(new ItemStack(onion_slice), 2, false);
        this.makeItemSandwichable(new ItemStack(raw_meat_patty), 1, false);
        this.makeItemSandwichable(new ItemStack(cooked_meat_patty), 4, false);
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
                this.makeItemSandwichable(new Stack(asItemStack), entry.getHealAmount(), entry.isBread(), entry.showInformation(), entry.dropItem());
        }
    }

    @Override
    public Sandwichable makeItemSandwichable(Stack stack, int heal, boolean isBread, boolean showInformation, boolean dropItem)
    {
        Sandwichable entry = new Sandwichable().setStack(stack).setHealAmount(heal).setIsBread(isBread).setShowInformation(showInformation).setDropItem(dropItem);
        sandwichableItems.add(entry);
        ModLogger.print(ModLogger.DEBUG, "Registering Sandwichable: " + entry.toString());
        return entry;
    }

    public Sandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread, boolean showInformation, boolean dropItem)
    {
//        System.out.println(stack.getUnlocalizedName() + ", heal: " + heal + ", isBread: " + isBread + ", showInformation: " + showInformation + ", dropItem: " + dropItem);
        return this.makeItemSandwichable(new Stack(stack), heal, isBread, showInformation, dropItem);
    }

    public Sandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread)
    {
//        System.out.println(stack.getUnlocalizedName() + ", heal: " + heal + ", isBread: " + isBread);
        return this.makeItemSandwichable(stack, heal, isBread, true, true);
    }

    @Override
    public Sandwichable getSandwichableForItem(Stack stack)
    {
        for (Sandwichable sandwichable : sandwichableItems)
            if (sandwichable.getStack().areStacksEqual(stack, Stack.Type.ITEM, Stack.Type.METADATA))
                return sandwichable;
        return null;
    }

    public Sandwichable getSandwichableForItem(ItemStack stack)
    {
        return this.getSandwichableForItem(new Stack(stack));
    }

    @Override
    public boolean isSandwichable(Stack stack)
    {
        return getSandwichableForItem(stack) != null;
    }

    public boolean isSandwichable(ItemStack stack)
    {
        return this.isSandwichable(StackUtils.fromItemStack(stack));
    }

    /**
     * A simple container class that contains all the Sandwich information for the Stack.
     */
    public static class Sandwichable implements ISandwichable
    {
        public Stack stack;
        public int healAmount;
        public boolean isBread, hideInformation, dropItem;

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

        public Stack getStack()
        {
            return this.stack;
        }

        @Override
        public Sandwichable setStack(Stack newStack)
        {
            this.stack = newStack;
            return this;
        }

        @Override
        public String toString()
        {
            return "stack=" + getStack().toString() + ", healAmount=" + healAmount + ", isBread=" + getIsBread() + ", hideInformation=" + getHideInformation() + ", dropItem=" + this.getDropItem();
        }
    }
}
