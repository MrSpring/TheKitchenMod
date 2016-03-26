package dk.mrspring.kitchen.common.entity.particle;

import net.minecraft.world.World;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public interface IParticleHandler
{
    void spawnParticle(World world, double x, double y, double z, int id);
}
