package dk.mrspring.kitchen.config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 29-09-2014 for TheKitchenMod.
 */
public class SandwichableConfig extends BaseConfig
{
    List<SandwichableEntry> sandwichable_items = new ArrayList<SandwichableEntry>();

    public SandwichableConfig()
    {
        sandwichable_items = new ArrayList<SandwichableEntry>();
    }

    public SandwichableConfig(File location, String name)
    {
        super(location, name);

        this.addDefaultItems();
    }

    public void addDefaultItems()
    {
        this.makeSandwichable(new SandwichableEntry("kitchen:bread_slice", 0, true));
        this.makeSandwichable(new SandwichableEntry("kitchen:sliced_burger_bun", 1, true));
        this.makeSandwichable(new SandwichableEntry("kitchen:toast", 0, true));

        this.makeSandwichable(new SandwichableEntry("kitchen:bacon_cooked", 2));
        this.makeSandwichable(new SandwichableEntry("kitchen:bacon_raw", 1));

        this.makeSandwichable(new SandwichableEntry("kitchen:tomato_slice", 1));
        this.makeSandwichable(new SandwichableEntry("kitchen:lettuce_leaf", 2));
        this.makeSandwichable(new SandwichableEntry("kitchen:potato_slice", 2));
        this.makeSandwichable(new SandwichableEntry("kitchen:carrot_slice", 2));

        this.makeSandwichable(new SandwichableEntry("kitchen:raw_roast_beef", 1));
        this.makeSandwichable(new SandwichableEntry("kitchen:roast_beef", 4));

        this.makeSandwichable(new SandwichableEntry("kitchen:chicken_fillet_raw", 1));
        this.makeSandwichable(new SandwichableEntry("kitchen:chicken_fillet_cooked", 4));

        this.makeSandwichable(new SandwichableEntry("kitchen:cheese_slice", 3));
        this.makeSandwichable(new SandwichableEntry("kitchen:butter", 1));

        this.makeSandwichable(new SandwichableEntry("kitchen:strawberry_jam", 2).hideInformation().dontDropItem());
        this.makeSandwichable(new SandwichableEntry("kitchen:apple_jam", 2).hideInformation().dontDropItem());
        this.makeSandwichable(new SandwichableEntry("kitchen:peanut_jam", 2).hideInformation().dontDropItem());

        this.makeSandwichable(new SandwichableEntry("kitchen:jam_jar", 0).hideInformation());
    }

    public void makeSandwichable(SandwichableEntry entry)
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
    }

    public List<SandwichableEntry> getSandwichableItems()
    {
        return sandwichable_items;
    }

    public static class SandwichableEntry
    {
        String item_name = "minecraft:dirt";
        int heal_amount = 0;
        boolean is_bread = false;
        boolean hide_information = false;
        boolean drop_item = true;

        public SandwichableEntry(String name, int healAmount, boolean isBread)
        {
            this.item_name = name;
            this.heal_amount = healAmount;
            this.is_bread = isBread;
        }

        public SandwichableEntry(String name, int healAmount)
        {
            this(name, healAmount, false);
        }

        public boolean matches(String name)
        {
            return name.equals(this.item_name);
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

        public String getItemName()
        {
            return this.item_name;
        }
    }
}
