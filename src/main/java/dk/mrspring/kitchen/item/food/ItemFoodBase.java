package dk.mrspring.kitchen.item.food;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class ItemFoodBase extends ItemFood
{
    public ItemFoodBase(String name, String textureName, int healAmount, boolean canWolfEat, boolean useDefaultCreativeTab)
    {
        super(healAmount, canWolfEat);

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        if (useDefaultCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
    }

    public ItemFoodBase(String name, int healAmount, boolean canWolfEat, boolean useDefaultCreativeTab)
    {
        this(name, ModInfo.toTexture(name), healAmount, canWolfEat, useDefaultCreativeTab);
    }

    public ItemFoodBase(String name, int healAmount, boolean canWolfEat)
    {
        this(name, healAmount, canWolfEat, true);
    }

    public ItemFoodBase(String name, String textureName, int healAmount, boolean canWolfEat, CreativeTabs creativeTab)
    {
        super(healAmount, canWolfEat);

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        this.setCreativeTab(creativeTab);
    }

    public ItemFoodBase(String name, int healAmount, boolean canWolfEat, CreativeTabs creativeTab)
    {
        this(name, ModInfo.toTexture(name), healAmount, canWolfEat, creativeTab);
    }
}
