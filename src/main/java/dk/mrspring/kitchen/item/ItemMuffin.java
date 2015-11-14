package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.food.ItemFoodBase;
import dk.mrspring.kitchen.item.render.ItemRenderMuffin;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Konrad on 10-08-2015.
 */
public class ItemMuffin extends ItemFoodBase
{
    public static final String MUFFIN_TYPE = "MuffinType";
    private static final Random RANDOM = new Random();

    public static String getMuffinType(ItemStack stack)
    {
        return ItemUtils.getStringTag(stack, MUFFIN_TYPE);
    }

    public static ItemStack makeMuffinStack(String muffinType, boolean cooked)
    {
        return makeMuffinStack(muffinType, cooked, RANDOM.nextInt(ItemMuffinCup.colorNames.length));
    }

    public static ItemStack makeMuffinStack(String muffinType, boolean cooked, int color)
    {
        ItemStack muffin = new ItemStack(cooked ? KitchenItems.cooked_muffin : KitchenItems.raw_muffin, 1, color);
        ItemUtils.setStringTag(muffin, MUFFIN_TYPE, muffinType);
        return muffin;
    }

    public ItemMuffin(String name, int healAmount)
    {
        super(name, healAmount, false, true);

        this.setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.format(getUnlocalizedName(stack) + ".name", I18n.format("muffin." + getMuffinType(stack) + ".name"));
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        super.onEaten(stack, world, player);
        return new ItemStack(KitchenItems.empty_muffin_cup, 1, stack.getItemDamage());
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        if (pass != 2)
            return KitchenItems.empty_muffin_cup.getIconFromDamageForRenderPass(damage, pass);
        else
            return super.getIconFromDamageForRenderPass(damage, pass);
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 3;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs p_150895_2_, List list)
    {
        for (Map.Entry<String, Integer> entry : ItemRenderMuffin.COLOR_HANDLER.getColors().entrySet())
            list.add(makeMuffinStack(entry.getKey(), item == KitchenItems.cooked_muffin));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        super.addInformation(stack, player, list, p_77624_4_);
        list.add(I18n.format("item.muffin.desc", I18n.format("item." + ItemMuffinCup.colorNames[stack.getItemDamage()] + "_muffin_cup.name")));
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        if (pass != 2) return KitchenItems.empty_muffin_cup.getColorFromItemStack(stack, pass);
        else return ItemRenderMuffin.COLOR_HANDLER.getColorFromStack(stack);
    }
}
