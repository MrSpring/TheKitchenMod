package dk.mrspring.kitchen.item;

import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

/**
 * Created by MrSpring on 13-03-2015 for TheKitchenMod.
 */
public class ItemFoodWeaponBase extends ItemFoodBase
{
    private final int damage;

    public ItemFoodWeaponBase(String name, int healAmount, boolean canWolfEat, int damage, CreativeTabs creativeTab)
    {
        super(name, healAmount, canWolfEat, creativeTab);

        this.damage = damage;
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }

    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", damage, 0));
        return multimap;
    }
}
