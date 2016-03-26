package dk.mrspring.kitchen.client;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.entity.particle.ClientParticleHandler;
import dk.mrspring.kitchen.common.CommonProxy;
import net.minecraftforge.client.MinecraftForgeClient;
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
}
