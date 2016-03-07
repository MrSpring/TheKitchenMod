package dk.mrspring.kitchen.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.item.render.ItemRenderMixingBowl;
import dk.mrspring.kitchen.util.ItemUtils;
import dk.mrspring.kitchen.util.LangUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Map;

import static dk.mrspring.kitchen.KitchenItems.mixing_bowl;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowl extends ItemBase
{
    public static final String MIX_TYPE = "MixType";
    public static final String MIX_FORMAT = "mix.%s.name";

    @SideOnly(Side.CLIENT)
    IIcon[] icons;

    public ItemMixingBowl(String name)
    {
        super(name, true);
        this.setMaxStackSize(1);
    }

    public static boolean isIceCream(ItemStack mixingBowlStack)
    {
        String mixType = getMixTypeFromStack(mixingBowlStack);
        return !(mixType == null || mixType.isEmpty()) && mixType.toLowerCase().contains("ice_cream");
    }

    public static boolean areBowlsEqual(ItemStack stack1, ItemStack stack2)
    {
        if (!ItemUtils.item(stack1, KitchenItems.mixing_bowl) || !ItemUtils.item(stack2, KitchenItems.mixing_bowl))
            return false;
        String mix1 = getMixTypeFromStack(stack1), mix2 = getMixTypeFromStack(stack2);
        return !(mix1 == null || mix2 == null) && mix1.equals(mix2);
    }

    public static String getMixTypeFromStack(ItemStack stack)
    {
        return ItemUtils.getStringTag(stack, MIX_TYPE);
    }

    public static void reduceUsesLeft(ItemStack mixingBowlStack, int amount)
    {
        if (mixingBowlStack.getItemDamage() > 0)
        {
            mixingBowlStack.setItemDamage(mixingBowlStack.getItemDamage() - amount);
            if (mixingBowlStack.getItemDamage() == 0 && mixingBowlStack.hasTagCompound())
                mixingBowlStack.getTagCompound().removeTag(MIX_TYPE);
        }
    }

    public static ItemStack getMixingBowlStack(String mixType, int usesLeft)
    {
        ItemStack bowl = new ItemStack(mixing_bowl, 1, usesLeft);
        if (mixType != null)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString(MIX_TYPE, mixType);
            bowl.setTagCompound(tagCompound);
        }
        return bowl;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItemDamage() > 0;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack stack = itemStack.copy();
        stack.setItemDamage(stack.getItemDamage() - 1);
        if (stack.getItemDamage() == 0)
            stack.getTagCompound().removeTag(MIX_TYPE);
        return stack;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean b)
    {
        super.addInformation(stack, player, information, b);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MIX_TYPE))
            information.add(LangUtils.deepFormat("item.mixing_bowl.full.desc", String.format(MIX_FORMAT, stack.getTagCompound().getString(MIX_TYPE))));
        if (stack.getItemDamage() > 0)
            information.add(I18n.format("item.mixing_bowl.uses_left_msg", stack.getItemDamage()));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        super.getSubItems(item, tab, items);

        if (ModConfig.getKitchenConfig().show_different_mixing_bowls_in_creative_tab)
            for (Map.Entry<String, Integer> entry : ItemRenderMixingBowl.COLOR_HANDLER.getColors().entrySet())
            {
                ItemStack stack = new ItemStack(item, 1, 3);
                stack.setTagInfo(MIX_TYPE, new NBTTagString(entry.getKey()));
                items.add(stack);
            }
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        if (metadata == 0)
            return 1;
        else return 2;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() > 0 && itemStack.hasTagCompound())
            if (itemStack.getTagCompound().hasKey(MIX_TYPE))
                return I18n.format("item.mixing_bowl.full.name");
        return I18n.format("item.mixing_bowl.empty.name");
    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        icons = new IIcon[4];

        for (int i = 0; i < icons.length; i++)
            icons[i] = register.registerIcon(this.iconString + "_" + i);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (renderPass == 1)
            return ItemRenderMixingBowl.COLOR_HANDLER.getColorFromStack(stack);
        else return super.getColorFromItemStack(stack, renderPass);
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int renderPass)
    {
        if (renderPass == 0)
            return this.itemIcon;
        else if (damage > 0 && damage < 4)
            return icons[damage];
        else return icons[0];
    }
}
