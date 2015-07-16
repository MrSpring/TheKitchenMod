package dk.mrspring.kitchen.api;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 12-07-2015.
 */
public interface ISpawner
{
    EntityItem spawnItemInWorld(ItemStack stack);
}
