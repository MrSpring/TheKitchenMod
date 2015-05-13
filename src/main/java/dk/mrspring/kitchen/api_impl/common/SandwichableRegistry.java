package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
import dk.mrspring.kitchen.api.sandwichable.ISandwichableRegistry;
import dk.mrspring.kitchen.api.stack.Stack;
import dk.mrspring.kitchen.config.SandwichableConfig;
import dk.mrspring.kitchen.config.wrapper.JsonItemStack;
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
        return entry;
    }

    public Sandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread, boolean showInformation, boolean dropItem)
    {
        return this.makeItemSandwichable(new Stack(stack), heal, isBread, showInformation, dropItem);
    }

    @Override
    public Sandwichable getSandwichableForItem(Stack stack)
    {
        for (Sandwichable sandwichable : sandwichableItems)
            if (sandwichable.getStack().equals(stack))
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
        return this.isSandwichable(new Stack(stack));
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
            return false;
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
    }
}
