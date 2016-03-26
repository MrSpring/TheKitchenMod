package dk.mrspring.kitchen.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

import static dk.mrspring.kitchen.common.util.ItemUtils.*;

/**
 * Created on 08-03-2016 for TheKitchenMod.
 */
public class ItemContainerBase extends ItemNoLogic implements IComparable
{
    public final String TYPE_TAG;
    public String containsKey = null, typeFormat = null;

    public ItemContainerBase(String typeTag, String name, String textureName, CreativeTabs tab, String... lore)
    {
        super(name, textureName, tab, lore);
        this.TYPE_TAG = typeTag;
    }

    public ItemContainerBase(String typeTag, String name, CreativeTabs tab, String... lore)
    {
        super(name, tab, lore);
        this.TYPE_TAG = typeTag;
    }

    public ItemContainerBase(String typeTag, String name, String... lore)
    {
        super(name, lore);
        this.TYPE_TAG = typeTag;
    }

    /**
     * Sets the strings used to translate the information.
     *
     * @param containsKey The lang. key used to translate the information line.
     * @param typeFormat  Will be formatted with {@link String#format} to get the lang. key for the type is
     *                    currently being held in the container.
     * @return
     */
    public ItemContainerBase setContainsFormat(String containsKey, String typeFormat)
    {
        this.containsKey = containsKey;
        this.typeFormat = typeFormat;
        return this;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
        super.addInformation(stack, player, list, flag);
        if (containsKey != null && damage(stack) > 0 && hasString(stack, TYPE_TAG))
        {
            String contains = getTypeForDisplay(stack);
            if (contains != null) list.add(I18n.format(containsKey, contains));
        }
    }

    public String getType(ItemStack stack)
    {
        return getString(stack, TYPE_TAG);
    }

    public String getTypeForDisplay(ItemStack stack)
    {
        String type = getType(stack);
        return type == null ? null : I18n.format(String.format(typeFormat, type));
    }

    @Override
    public boolean compareStacks(ItemStack stack1, ItemStack stack2)
    {
        return areContainerStacksEqual(stack1, stack2, this, TYPE_TAG);
    }
}
