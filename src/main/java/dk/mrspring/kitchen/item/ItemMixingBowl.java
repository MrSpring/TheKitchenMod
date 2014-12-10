package dk.mrspring.kitchen.item;

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
        this.setMaxDamage(3);
        this.setMaxStackSize(1);
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
        icons = new IIcon[4];

        for (int i = 0; i < icons.length; i++)
            icons[i] = register.registerIcon(this.iconString + "_" + i);
    }

    @Override
    public IIcon getIconFromDamage(int metadata)
    {
        if (metadata < 4 && metadata > 0)
            return this.icons[metadata];
        else return this.icons[0];
    }
}
