package dk.mrspring.kitchen.config;

import dk.mrspring.kitchen.config.wrapper.JsonItemStack;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 29-09-2014 for TheKitchenMod.
 */
public class SandwichableConfig extends BaseConfig
{
    SandwichableEntry[] example = new SandwichableEntry[]{
            new SandwichableEntry(new JsonItemStack("minecraft:diamond"), 4, false),
            new SandwichableEntry(new JsonItemStack("kitchen:bread_slice"), 0, true)
    };
    List<SandwichableEntry> sandwichable_items = new ArrayList<SandwichableEntry>();

    public SandwichableConfig()
    {
        example = new SandwichableEntry[]{
                new SandwichableEntry(new JsonItemStack("minecraft:diamond"), 4, false),
                new SandwichableEntry(new JsonItemStack("kitchen:bread_slice"), 0, true)
        };
        sandwichable_items = new ArrayList<SandwichableEntry>();
    }

    public SandwichableConfig(File location, String name)
    {
        super(location, name);
    }

    @Override
    public void addDefaults()
    {
//        this.makeSandwichable(new SandwichableEntry("kitchen:bread_slice", 0, true));
//        this.makeSandwichable(new SandwichableEntry("kitchen:sliced_burger_bun", 1, true));
//        this.makeSandwichable(new SandwichableEntry("kitchen:toast", 0, true));
//        this.makeSandwichable(new SandwichableEntry("kitchen:toasted_toast", 1, true));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:bacon_cooked", 2));
//        this.makeSandwichable(new SandwichableEntry("kitchen:bacon_raw", 1));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:tomato_slice", 1));
//        this.makeSandwichable(new SandwichableEntry("kitchen:lettuce_leaf", 2));
//        this.makeSandwichable(new SandwichableEntry("kitchen:potato_slice", 2));
//        this.makeSandwichable(new SandwichableEntry("kitchen:carrot_slice", 2));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:raw_roast_beef", 1));
//        this.makeSandwichable(new SandwichableEntry("kitchen:roast_beef", 4));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:chicken_fillet_raw", 1));
//        this.makeSandwichable(new SandwichableEntry("kitchen:chicken_fillet_cooked", 4));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:cheese_slice", 3));
//        this.makeSandwichable(new SandwichableEntry("kitchen:butter", 1));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:strawberry_jam", 2).hideInformation().dontDropItem());
//        this.makeSandwichable(new SandwichableEntry("kitchen:apple_jam", 2).hideInformation().dontDropItem());
//        this.makeSandwichable(new SandwichableEntry("kitchen:peanut_jam", 2).hideInformation().dontDropItem());
//        this.makeSandwichable(new SandwichableEntry("kitchen:cocoa_jam", 2).hideInformation().dontDropItem());
//        this.makeSandwichable(new SandwichableEntry("kitchen:ketchup_jam", 2).hideInformation().dontDropItem());
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:jam_jar", 0).hideInformation());
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:raw_cut_fish", 1));
//        this.makeSandwichable(new SandwichableEntry("kitchen:cut_fish", 2));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:ham_slice", 3));
//        this.makeSandwichable(new SandwichableEntry("kitchen:onion_slice", 2));
//
//        this.makeSandwichable(new SandwichableEntry("kitchen:raw_meat_patty", 1).hideInformation());
//        this.makeSandwichable(new SandwichableEntry("kitchen:cooked_meat_patty", 3));
    }

    /*public void makeSandwichable(SandwichableEntry entry)
    {
        if (entry != null)
            if (!this.sandwichable_items.contains(entry))
                this.sandwichable_items.add(entry);
    }

    public boolean canAdd(ItemStack stack)
    {
        return this.findEntry(stack) != null;
    }

    public int getHealAmount(ItemStack stack)
    {
        SandwichableEntry entry = this.findEntry(stack);
        if (entry != null)
            return entry.heal_amount;
        else return 0;
    }

    public boolean isBread(ItemStack stack)
    {
        SandwichableEntry entry = this.findEntry(stack);
        return entry != null && entry.is_bread;
    }

    public SandwichableEntry findEntry(String stack)
    {
        for (SandwichableEntry entry : this.sandwichable_items)
            if (entry.matches(stack))
                return entry;
        return null;
    }

    public SandwichableEntry findEntry(ItemStack stack)
    {
        if (stack != null)
        {
            GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            if (identifier != null)
                return this.findEntry(identifier.toString());
            else return null;
        } else return null;
    }*/

    public List<SandwichableEntry> getSandwichableItems()
    {
        return sandwichable_items;
    }

    public static class SandwichableEntry
    {
        String item_name = "minecraft:dirt";
        JsonItemStack input;
        int heal_amount = 0;
        boolean is_bread = false;
        boolean hide_information = false;
        boolean drop_item = true;

        public SandwichableEntry(JsonItemStack stack, int healAmount, boolean isBread, boolean hideInformation, boolean dropItem)
        {
            this(stack, healAmount, isBread);
            this.hide_information = hideInformation;
            this.drop_item = dropItem;
        }

        public SandwichableEntry(JsonItemStack stack, int healAmount, boolean isBread)
        {
            this.input = stack;
            this.heal_amount = healAmount;
            this.is_bread = isBread;
        }

        public SandwichableEntry(JsonItemStack stack, int healAmount)
        {
            this(stack, healAmount, false);
        }

        public boolean matches(ItemStack stack)
        {
            ItemStack inputStack = input.toItemStack();
            return ItemUtils.areStacksEqual(stack, inputStack, true);
        }

        public SandwichableEntry hideInformation()
        {
            this.hide_information = true;
            return this;
        }

        public SandwichableEntry dontDropItem()
        {
            this.drop_item = false;
            return this;
        }

        public boolean dropItem()
        {
            return this.drop_item;
        }

        public boolean showInformation()
        {
            return !this.hide_information;
        }

        public Item getItemName()
        {
            return this.input.toItemStack().getItem();
        }

        public int getItemMetadata()
        {
            return this.input.toItemStack().getItemDamage();
        }

        public int getHealAmount()
        {
            return this.heal_amount;
        }

        public JsonItemStack getInput()
        {
            if (input == null && item_name != null)
                this.input = new JsonItemStack(item_name);
            return input;
        }

        public boolean isBread()
        {
            return this.is_bread;
        }
    }
}
