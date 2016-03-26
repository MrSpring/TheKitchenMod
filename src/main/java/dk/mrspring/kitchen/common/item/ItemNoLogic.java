package dk.mrspring.kitchen.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created on 08-03-2016 for TheKitchenMod.
 */
public class ItemNoLogic extends Item
{
    String[] lore;

    public ItemNoLogic(String name, String textureName, CreativeTabs tab, String... lore)
    {
        this.lore = lore;

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        this.setCreativeTab(tab);
    }

    public ItemNoLogic(String name, CreativeTabs tab, String... lore)
    {
        this(name, ModInfo.toResource(name), tab, lore);
    }

    public ItemNoLogic(String name, String... lore)
    {
        this(name, Kitchen.mainTab, lore);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
        super.addInformation(stack, player, list, flag);
        for (String l : lore) list.add(I18n.format(l));
    }
}
