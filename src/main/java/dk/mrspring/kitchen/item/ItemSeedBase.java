package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;

/**
 * Created by MrSpring on 06-10-2014 for TheKitchenMod.
 */
public class ItemSeedBase extends ItemSeedFood
{
    public ItemSeedBase(String name, String textureName, Block placedCrop, int healAmount, boolean useCreativeTab)
    {
        super(healAmount, 0.3F, placedCrop, Blocks.farmland);

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        if (useCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
    }

    public ItemSeedBase(String name, String textureName, Block placedCrop, boolean useCreativeTab)
    {
        this(name, textureName, placedCrop, 2, useCreativeTab);
    }

    public ItemSeedBase(String name, Block placedCrop, int healAmount, boolean useCreativeTab)
    {
        this(name, ModInfo.toTexture(name), placedCrop, healAmount, useCreativeTab);
    }

    public ItemSeedBase(String name, Block placedCrop, boolean useCreativeTab)
    {
        this(name, ModInfo.toTexture(name), placedCrop, useCreativeTab);
    }
}
