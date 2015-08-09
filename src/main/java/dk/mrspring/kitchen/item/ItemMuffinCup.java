package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.item.render.ItemRenderJamJar;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by Konrad on 09-08-2015.
 */
public class ItemMuffinCup extends ItemBase
{
    boolean full;
    public static final int BLACK = 0, RED = 1, GREEN = 2, BROWN = 3, BLUE = 4, PURPLE = 5, CYAN = 6, SILVER = 7,
            GRAY = 8, PINK = 9, LIME = 10, YELLOW = 11, LIGHT_BLUE = 12, MAGENTA = 13, ORANGE = 14, WHITE = 15;

    public static final String[] colorNames = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan",
            "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};

    String name;
    IIcon overlay;

    public ItemMuffinCup(boolean filled)
    {
        super((filled ? "filled_" : "") + "muffin_cup", true);
        this.full = filled;
        this.name = (filled ? "filled_" : "") + "muffin_cup";
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int damage = stack.getItemDamage();
        return "item." + colorNames[clampColor(damage)] + "_" + name;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, WHITE));
    }

    @Override
    public void registerIcons(IIconRegister registry)
    {
        super.registerIcons(registry);
        overlay = registry.registerIcon(getIconString() + "_overlay");
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int renderPass)
    {
        if (renderPass == 1) return overlay;
        else return super.getIconFromDamageForRenderPass(damage, renderPass);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (renderPass == 1)
        {
            float[] afloat = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(stack.getItemDamage())];
            return ItemRenderJamJar.floatArrayAsInt(afloat, super.getColorFromItemStack(stack, renderPass));
        } else return super.getColorFromItemStack(stack, renderPass);
    }

    public int clampColor(int color)
    {
        return color >= colorNames.length ? colorNames.length - 1 : (color < 0 ? 0 : color);
    }
}
