package dk.mrspring.kitchen.item;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

import static dk.mrspring.kitchen.GameRegisterer.registerItem;

public class ItemBase extends Item
{
	private String[] informationLines = new String[0];

    public ItemBase(String name, String textureName, boolean useCreativeTab)
    {
        super();

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        if (useCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
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
		if (this.informationLines.length!=0)
			Collections.addAll(information, this.informationLines);
	}

	public static void load()
    {
        registerItem(KitchenItems.knife);
        registerItem(KitchenItems.mortar_and_pestle);
        registerItem(KitchenItems.mortar);
        registerItem(KitchenItems.pestle);
        registerItem(KitchenItems.raw_bacon);
        registerItem(KitchenItems.bread_slice);
        GameRegistry.registerCustomItemStack("sandwich_itemstack", KitchenItems.basic_sandwich);
        registerItem(KitchenItems.tomato);
        registerItem(KitchenItems.lettuce);
        registerItem(KitchenItems.tomato_slice);
        registerItem(KitchenItems.lettuce_leaf);
        registerItem(KitchenItems.bacon);
        registerItem(KitchenItems.potato_slice);
        registerItem(KitchenItems.carrot_slice);
        registerItem(KitchenItems.flour);
        registerItem(KitchenItems.toast);
        registerItem(KitchenItems.raw_roast_beef);
        registerItem(KitchenItems.roast_beef);
        registerItem(KitchenItems.raw_chicken_fillet);
        registerItem(KitchenItems.chicken_fillet);
        registerItem(KitchenItems.chicken_leg);
        registerItem(KitchenItems.cheese);
        registerItem(KitchenItems.cheese_slice);
        registerItem(KitchenItems.burnt_meat);
        registerItem(KitchenItems.butter);
        registerItem(KitchenItems.butter_knife);
        registerItem(KitchenItems.jam_jar);
        registerItem(KitchenItems.jam_strawberry);
        registerItem(KitchenItems.jam_apple);
        registerItem(KitchenItems.strawberry);
        registerItem(KitchenItems.cut_apple);
        registerItem(KitchenItems.cut_strawberry);
        registerItem(KitchenItems.peanut);
        registerItem(KitchenItems.peanuts_in_shell);
    }
}
