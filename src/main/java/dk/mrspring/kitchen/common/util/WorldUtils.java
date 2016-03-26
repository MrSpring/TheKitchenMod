package dk.mrspring.kitchen.common.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

import static dk.mrspring.kitchen.common.util.ItemUtils.copy;
import static dk.mrspring.kitchen.common.util.ItemUtils.notEmpty;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public class WorldUtils
{
    public static int getAngle(float yaw, int angles)
    {
        return MathHelper.floor_double((double) (yaw * (float) angles / 360.0F) + 0.5D) & (angles - 1);
    }

    public static EntityItem spawnItemAt(ItemStack stack, World world, int x, int y, int z)
    {
        if (notEmpty(stack))
        {
            Random rand = new Random();
            float xr = rand.nextFloat() * 0.8F + 0.1F;
            float yr = rand.nextFloat() * 0.8F + 0.1F;
            float zr = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem e = new EntityItem(world, ((float) x + xr), ((float) y + yr), ((float) z + zr), copy(stack));
            float f = 0.05F;
            e.motionX = (double) ((float) rand.nextGaussian() * f);
            e.motionY = (double) ((float) rand.nextGaussian() * f + 0.2F);
            e.motionZ = (double) ((float) rand.nextGaussian() * f);
            world.spawnEntityInWorld(e);
            return e;
        } else return null;
    }

    public static void sound(World world, String sound, double x, double y, double z, float f1, float f2)
    {
        world.playSoundEffect(x, y, z, sound, f1, f2);
    }

    public static TileEntity getEventEntity(BlockEvent event)
    {
        return event.world.getTileEntity(event.x, event.y, event.z);
    }
}
