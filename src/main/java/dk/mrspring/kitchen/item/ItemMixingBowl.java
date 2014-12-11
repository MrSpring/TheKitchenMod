package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

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
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        if (metadata == 0)
            return 1;
        else return 3;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() == 0)
            return StatCollector.translateToLocal("item.mixing_bowl.empty.name");
        else return StatCollector.translateToLocal("item.mixing_bowl.full.name");
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
        else if (renderPass == 2)
            return icons[0];
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
