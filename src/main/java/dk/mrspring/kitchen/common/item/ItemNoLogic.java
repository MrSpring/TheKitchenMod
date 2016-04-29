package dk.mrspring.kitchen.common.item;

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
    String[] names;

    public ItemNoLogic(String name, String textureName, CreativeTabs tab)
    {
        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        this.setCreativeTab(tab);

        this.names = new String[]{name};
        this.lore = new String[0];
    }

    public ItemNoLogic(String name, CreativeTabs tab)
    {
        this(name, ModInfo.toResource(name), tab);
    }

    public ItemNoLogic(String name, String textureName)
    {
        this(name, textureName, Kitchen.mainTab);
    }

    public ItemNoLogic(String name)
    {
        this(name, Kitchen.mainTab);
    }

    public ItemNoLogic setNames(String... names)
    {
        this.names = names;
        return this;
    }

    public ItemNoLogic setLore(String... lore)
    {
        this.lore = lore;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
        super.addInformation(stack, player, list, flag);
        for (String l : lore) list.add(I18n.format(l));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getName(stack.getItemDamage());
    }

    private String getName(int meta)
    {
        return meta >= 0 && meta < names.length ? names[meta] : names[0];
    }
}
