package dk.mrspring.kitchen.common;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import dk.mrspring.kitchen.common.tileentity.TileEntityModernOven;
import dk.mrspring.kitchen.common.tileentity.constructor.BasicConstructor;
import dk.mrspring.kitchen.common.tileentity.constructor.NullConstructor;
import dk.mrspring.kitchen.common.tileentity.constructor.TileEntityConstructor;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class CommonProxy
{
    private CommonEventHandler commonHandler = new CommonEventHandler();
    public IParticleHandler particles = new IParticleHandler()
    {
        @Override
        public void spawnParticle(World world, double x, double y, double z, int id)
        {
        }
    };
    public Map<String, TileEntityConstructor> tileEntities = Maps.newHashMap();
    private final TileEntityConstructor NULL = new NullConstructor();

    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(commonHandler);

        Kitchen.blocks.register();
        Kitchen.items.register();
        Kitchen.ovenItems.register();
        Kitchen.ovenRecipes.register();
    }

    public void init(FMLInitializationEvent event)
    {
        registerTileEntities();
        for (Map.Entry<String, TileEntityConstructor> entry : tileEntities.entrySet())
            entry.getValue().register(entry.getKey());
    }

    public void registerTileEntities()
    {
        tileEntities.put("oven", new BasicConstructor(TileEntityModernOven.class));
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }

    public TileEntityConstructor getConstructor(String name)
    {
        if (tileEntities.containsKey(name)) return tileEntities.get(name);
        else return NULL;
    }
}
