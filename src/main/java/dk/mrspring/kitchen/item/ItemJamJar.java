package dk.mrspring.kitchen.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.render.ItemRenderJamJar;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Map;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class ItemJamJar extends ItemBase
{
    public static String JAM_TYPE = "JamType";

    @SideOnly(Side.CLIENT)
    IIcon[] jamIcon;

    public ItemJamJar(String name)
    {
        super(name, true);
        this.setMaxStackSize(1);
    }

    public static String getJamFromStack(ItemStack jamJarStack)
    {
        if (jamJarStack.getTagCompound() != null)
            return jamJarStack.getTagCompound().getString(JAM_TYPE);
        else return null;
    }

    public static boolean isJamEqual(ItemStack stack1, ItemStack stack2)
    {
        if (!ItemUtils.item(stack1, KitchenItems.jam_jar) || !ItemUtils.item(stack1, KitchenItems.jam_jar))
            return false;
        String type1 = getJamFromStack(stack1), type2 = getJamFromStack(stack2);
        return !(type1 == null || type2 == null) && type1.equals(type2);
    }

    public static ItemStack getJamJarStack(String jam, int usesLeft)
    {
        ItemStack jamStack = new ItemStack(KitchenItems.jam_jar, 1, usesLeft);
        if (jam != null)
        {
            jamStack.setTagCompound(new NBTTagCompound());
            jamStack.getTagCompound().setString(JAM_TYPE, jam);
        }
        return jamStack;
    }

    public static void reduceUsesLeft(ItemStack jamStack, int amount)
    {
        if (jamStack != null && jamStack.getItem() == KitchenItems.jam_jar && jamStack.getItemDamage() > 0)
        {
            jamStack.setItemDamage(jamStack.getItemDamage() - amount);
            if (jamStack.getItemDamage() == 0 && jamStack.hasTagCompound())
                jamStack.getTagCompound().removeTag(JAM_TYPE);
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
    public void getSubItems(Item item, CreativeTabs creativeTab, List subItems)
    {
        super.getSubItems(item, creativeTab, subItems);
        if (ModConfig.getKitchenConfig().show_different_jars_in_creative_tab)
            for (Map.Entry<String, Integer> entry : ItemRenderJamJar.COLOR_HANDLER.getColors().entrySet())
                subItems.add(getJamJarStack(entry.getKey(), 6));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 1 || stack.getItemDamage() <= 0)
            return super.getIcon(stack, pass);
        else
        {
            int usesLeft = stack.getItemDamage();
            return this.jamIcon[usesLeft - 1];
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        int usesLeft = stack.getItemDamage();
        if (usesLeft > 0)
            par3List.add(StatCollector.translateToLocal("item.jam_jar.uses_left_msg") + ": " + usesLeft);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (renderPass == 0 && stack.getItemDamage() > 0)
            return ItemRenderJamJar.COLOR_HANDLER.getColorFromStack(stack);
        else return super.getColorFromItemStack(stack, renderPass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        jamIcon = new IIcon[6];
        super.registerIcons(register);
        for (int i = 0; i < this.jamIcon.length; i++)
            this.jamIcon[i] = register.registerIcon(ModInfo.modid + ":jam_jar_filling_" + (i + 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getItemDamage() > 0)
        {
            String jam = getJamFromStack(stack);
            if (jam != null)
                return StatCollector.translateToLocal("jam." + jam.toLowerCase() + ".name") + " " + StatCollector.translateToLocal("item.jam_jar.filled.name");
        }
        return StatCollector.translateToLocal("item.jam_jar.empty.name");
    }
}