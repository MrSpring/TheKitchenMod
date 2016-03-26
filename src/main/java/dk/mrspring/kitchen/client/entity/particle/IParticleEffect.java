package dk.mrspring.kitchen.client.entity.particle;

import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public interface IParticleEffect
{
    ResourceLocation PARTICLES = new ResourceLocation("minecraft", "textures/particle/particles.png");

    EntityFX[] makeEffect(World world, double x, double y, double z, IParticleHandler handler);
}
