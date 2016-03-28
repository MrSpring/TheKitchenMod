package dk.mrspring.kitchen.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.entity.particle.ClientParticleHandler;
import dk.mrspring.kitchen.client.tileentity.render.TileEntityOvenRenderer;
import dk.mrspring.kitchen.common.CommonProxy;
import dk.mrspring.kitchen.common.tileentity.TileEntityOven;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    private ClientEventHandler clientHandler = new ClientEventHandler();

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(clientHandler);

        particles = new ClientParticleHandler();
    }

    @Override
    public void registerTileEntities()
    {
        tileEntities.put("oven", TileEntityOven.class);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

        bind("oven", new TileEntityOvenRenderer());
    }

    private void bind(String tileEntity, TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntities.get(tileEntity), renderer);
    }
}
