package dk.mrspring.kitchen.item;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.Collections;
import java.util.List;

import static dk.mrspring.kitchen.GameRegisterer.registerItem;
import static dk.mrspring.kitchen.KitchenItems.*;

public class ItemBase extends Item
{
    private String[] informationLines = new String[0];
    private String localizableName = "super";

    public ItemBase(String name, String textureName, boolean useCreativeTab)
    {
        super();

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        if (useCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
    }

    public ItemBase setSelfAsContainerItem()
    {
        this.setContainerItem(this);
        return this;
    }

    public ItemBase setLocalizableName(String localizableName)
    {
        this.localizableName = localizableName;
        return this;
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        if (this.localizableName.equals("super"))
            return super.getItemStackDisplayName(p_77653_1_);
        else return StatCollector.translateToLocal(this.localizableName);
    }

    public ItemBase(String name, boolean useCreativeTab)
    {
        this(name, ModInfo.modid + ":" + name, useCreativeTab);
    }

    public ItemBase setInformationLines(String[] informationLines)
    {
        this.informationLines = informationLines;
        return this;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List information, boolean p_77624_4_)
    {
        if (this.informationLines.length != 0)
            Collections.addAll(information, this.informationLines);
    }

    public static void load()
    {
        registerItem(knife);
        registerItem(mortar_and_pestle);
        registerItem(mortar);
        registerItem(pestle);
        registerItem(jam_jar);
        registerItem(mixing_bowl);
        registerItem(bacon);
        registerItem(raw_bacon);
        registerItem(bread_slice);
        GameRegistry.registerCustomItemStack("sandwich_itemstack", basic_sandwich);
        registerItem(tomato);
        registerItem(lettuce);
        registerItem(tomato_slice);
        registerItem(lettuce_leaf);
        registerItem(potato_slice);
        registerItem(carrot_slice);
        registerItem(cut_apple);
        registerItem(flour);
        registerItem(toast);
        registerItem(toasted_toast);
        registerItem(raw_roast_beef);
        registerItem(roast_beef);
        registerItem(raw_chicken_fillet);
        registerItem(chicken_fillet);
        registerItem(chicken_leg);
        registerItem(cheese);
        registerItem(cheese_slice);
        registerItem(burnt_meat);
        registerItem(butter);
        registerItem(jam_strawberry);
        registerItem(jam_apple);
        registerItem(jam_peanut);
        registerItem(strawberry);
        registerItem(cut_strawberry);
        registerItem(jammable_strawberry);
        registerItem(peanut);
        registerItem(peanuts_in_shell);
        registerItem(waffle);
        registerItem(burnt_waffle);
        registerItem(pancake);
        registerItem(timer);
    }
}
