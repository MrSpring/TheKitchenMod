package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;
import java.util.Map;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowl extends ItemBase
{
    IIcon[] icons;

    public ItemMixingBowl(String name)
    {
        super(name, true);
        this.setMaxStackSize(1);
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

        for (Map.Entry<String, Integer> entry : ItemMixingBowlRenderer.mixColors.entrySet())
        {
            ItemStack stack = new ItemStack(p_150895_1_, 1, 3);
            stack.setTagInfo("MixType", new NBTTagString(entry.getKey()));
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
            if (itemStack.getTagCompound().hasKey("MixType"))
                return StatCollector.translateToLocal("mix." + ItemMixingBowlRenderer.getMixType(itemStack) + ".name") + " " + StatCollector.translateToLocal("item.mixing_bowl.full.name");
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
            return ItemMixingBowlRenderer.getColorAsInteger(stack);
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

    /*@Override
    public IIcon getIconFromDamage(int metadata)
    {
        if (metadata < 4 && metadata > 0)
            return this.icons[metadata];
        else return this.icons[0];
    }*/
}
