package dk.mrspring.kitchen.client.entity.particle;

import com.google.common.collect.Lists;
import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public class ClientParticleHandler implements IParticleHandler
{
    private final List<IParticleEffect> effects = Lists.newArrayList();

    @Override
    public void spawnParticle(World world, double x, double y, double z, int id)
    {
        if (id >= 0 && id < effects.size())
        {
            IParticleEffect effect = effects.get(id);
            if (effect != null)
            {
                EntityFX[] entities = effect.makeEffect(world, x, y, z, this);
                for (EntityFX entity : entities) this.addEffect(entity);
            }
        }
    }

    public void addEffect(EntityFX effect)
    {
        Minecraft.getMinecraft().effectRenderer.addEffect(effect);
    }
}
