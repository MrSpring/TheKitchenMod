package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.item.render.ItemRenderMixingBowl;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Map;

import static dk.mrspring.kitchen.KitchenItems.mixing_bowl;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowl extends ItemBase
{
    public static final String MIX_TYPE = "MixType";

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

    public static String getMixTypeFromStack(ItemStack mixingBowlStack)
    {
        if (mixingBowlStack.getTagCompound() != null)
            return mixingBowlStack.getTagCompound().getString(MIX_TYPE);
        else return null;
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
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List information, boolean p_77624_4_)
    {
        super.addInformation(p_77624_1_, p_77624_2_, information, p_77624_4_);
        if (p_77624_1_.getItemDamage() > 0)
            information.add(StatCollector.translateToLocal("item.jam_jar.uses_left_msg") + ": " + p_77624_1_.getItemDamage());
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list)
    {
        super.getSubItems(p_150895_1_, p_150895_2_, list);

        if (ModConfig.getKitchenConfig().show_different_mixing_bowls_in_creative_tab)
            for (Map.Entry<String, Integer> entry : ItemRenderMixingBowl.mixColors.entrySet())
            {
                ItemStack stack = new ItemStack(p_150895_1_, 1, 3);
                stack.setTagInfo(MIX_TYPE, new NBTTagString(entry.getKey()));
                list.add(stack);
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
        if (itemStack.getItemDamage() == 0)
            return StatCollector.translateToLocal("item.mixing_bowl.empty.name");
        else if (itemStack.getTagCompound() != null)
            if (itemStack.getTagCompound().hasKey(MIX_TYPE))
                return StatCollector.translateToLocal("mix." + getMixTypeFromStack(itemStack) + ".name") + " " + StatCollector.translateToLocal("item.mixing_bowl.full.name");
        return StatCollector.translateToLocal("item.mixing_bowl.empty.name");
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
            return ItemRenderMixingBowl.getColorAsInteger(stack);
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
