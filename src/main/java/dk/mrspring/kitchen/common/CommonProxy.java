package dk.mrspring.kitchen.common;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import dk.mrspring.kitchen.common.tileentity.TileEntityOven;
import net.minecraft.tileentity.TileEntity;
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
    public Map<String, Class<? extends TileEntity>> tileEntities = Maps.newHashMap();

    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(commonHandler);

        Kitchen.blocks.register();
        Kitchen.items.register();
    }

    public void init(FMLInitializationEvent event)
    {
        registerTileEntities();
        for (Map.Entry<String, Class<? extends TileEntity>> entry : tileEntities.entrySet())
            GameRegistry.registerTileEntity(entry.getValue(), entry.getKey());
    }

    public void registerTileEntities()
    {
        tileEntities.put("oven", TileEntityOven.class);
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
