package dk.mrspring.kitchen.config;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModConfig;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.item.EnumRarity.*;

/**
 * Created by MrSpring on 04-11-2014 for TheKitchenMod.
 */
public class ComboConfig extends BaseConfig
{
    public List<SandwichCombo> combos = new ArrayList<SandwichCombo>();

    public ComboConfig(File location, String name)
    {
        super(location, name);

        final String ANY_BREAD = "all-bread";

        combos.add(new SandwichCombo("big_mac", rare, 2, "kitchen:bread_slice", "kitchen:cheese_slice", "kitchen:roast_beef", "kitchen:lettuce_leaf", "kitchen:bread_slice", "kitchen:roast_beef", "kitchen:lettuce_leaf", "kitchen:bread_slice").translateName());
        combos.add(new SandwichCombo("blt_bread", uncommon, 1, "kitchen:bread_slice", "kitchen:bacon_cooked", "kitchen:lettuce_leaf", "kitchen:tomato_slice", "kitchen:bread_slice").translateName());
        combos.add(new SandwichCombo("blt_toast", uncommon, 1, "kitchen:toast", "kitchen:bacon_cooked", "kitchen:lettuce_leaf", "kitchen:tomato_slice", "kitchen:toast").translateName());
        combos.add(new SandwichCombo("only_bread", common, 0, "kitchen:bread_slice", "kitchen:bread_slice").translateName());
        combos.add(new SandwichCombo("only_toast", common, 0, "kitchen:toast", "kitchen:toast").translateName());
        combos.add(new SandwichCombo("veggie", rare, 2, "kitchen:bread_slice", "kitchen:carrot_slice", "kitchen:lettuce_leaf", "kitchen:tomato_slice", "kitchen:cheese_slice", "kitchen:bread_slice").translateName());
    }

    public SandwichCombo getComboMatching(ItemStack sandwich)
    {
        for (SandwichCombo combo : this.combos)
            if (combo.matches(sandwich))
                return combo;
        return null;
    }

    public SandwichCombo getCombo(String name)
    {
        for (SandwichCombo combo : this.combos)
            if (combo.getUnlocalizedName().equals(name))
                return combo;
        return null;
    }

    public void registerCombo(SandwichCombo combo)
    {
        if (combo != null)
            this.combos.add(combo);
    }

    public static class SandwichCombo
    {
        String name = "unnamed";
        ArrayList<String> layers = new ArrayList<String>();
        String rarity = EnumRarity.common.rarityName;
        int extra_health = 0;
        boolean translate_name = false;

        public SandwichCombo(String name, String rarity, int extraHealth, String... layers)
        {
            this.name = name;
            this.rarity = rarity;
            this.extra_health = extraHealth;
            Collections.addAll(this.layers, layers);
        }

        public SandwichCombo(String name, EnumRarity rarity, int extraHealth, String... layers)
        {
            this(name, rarity.rarityName, extraHealth, layers);
        }

        public SandwichCombo translateName()
        {
            this.translate_name = true;
            return this;
        }

        public String getUnlocalizedName()
        {
            return name;
        }

        public String getLocalizedName()
        {
            if (this.translate_name)
                return StatCollector.translateToLocal("combo." + this.getUnlocalizedName() + ".name");
            else return this.getUnlocalizedName();
        }

        public ArrayList<String> getLayers()
        {
            return layers;
        }

        public EnumRarity getRarity()
        {
            try
            {
                return EnumRarity.valueOf(this.rarity.toLowerCase());
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                return EnumRarity.common;
            }
        }

        public boolean matches(ItemStack sandwich)
        {
            ArrayList<SandwichableConfig.SandwichableEntry> layersInSandwich = new ArrayList<SandwichableConfig.SandwichableEntry>();
            NBTTagList layersList = sandwich.stackTagCompound.getTagList("SandwichLayers", 10);

            for (int i = 0; i < layersList.tagCount(); ++i)
            {
                NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);
                String name = GameRegistry.findUniqueIdentifierFor(layer.getItem()).toString();

                layersInSandwich.add(ModConfig.getSandwichConfig().findEntry(name));
            }
            return layersInSandwich.containsAll(this.getLayers()) && this.getLayers().containsAll(layersInSandwich);
        }

        public int getExtraHealth()
        {
            return extra_health;
        }
    }
}
