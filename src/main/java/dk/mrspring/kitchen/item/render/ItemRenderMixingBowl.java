package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.ItemMixingBowl;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderMixingBowl extends ColorHandler
{
    public static final ColorHandler COLOR_HANDLER = new ItemRenderMixingBowl();

    @Override
    String getIdentifierFromStack(ItemStack stack)
    {
        return ItemMixingBowl.getMixTypeFromStack(stack);
    }

    @Override
    public void loadDefaults()
    {
        registerColor("waffle_dough", 0xFFBB56);
        registerColor("pancake_dough", 0xFFD375);
        registerColor("burger_bun_dough", 0xFFD375);
        registerColor("vanilla_ice_cream", 0xFFFFFF);
        registerColor("strawberry_ice_cream", 0xFF9F9E);
        registerColor("chocolate_ice_cream", 0xCC8051);
        registerColor("apple_ice_cream", 0xF2EFBC);
        registerColor("scrambled_eggs", 0xFFF356);
    }
}
