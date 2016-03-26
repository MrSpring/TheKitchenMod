package dk.mrspring.kitchen.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class CommonProxy
{
    //    public CreativeTabs mainTab;
    private CommonEventHandler commonHandler = new CommonEventHandler();
    public IParticleHandler particles = new IParticleHandler()
    {
        @Override
        public void spawnParticle(World world, double x, double y, double z, int id)
        {
        }
    };

    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(commonHandler);

        Kitchen.blocks.register();
        Kitchen.items.register();
    }

    public void init(FMLInitializationEvent event)
    {
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
