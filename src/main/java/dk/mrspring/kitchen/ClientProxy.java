package dk.mrspring.kitchen;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import dk.mrspring.kitchen.item.render.ItemRenderJamJar;
import dk.mrspring.kitchen.item.render.ItemRenderSandwich;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.tileentity.renderer.*;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoard.class, new TileEntityBoardRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TileEntityPlateRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKitchenCabinet.class, new TileEntityKitchenCabinetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPan.class, new TileEntityPanRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaffleIron.class, new TileEntityWaffleIronRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToaster.class, new TileEntityToasterRenderer());

        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("sandwich"), new ItemRenderSandwich());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("jam_jar"), new ItemRenderJamJar());

        SandwichRender.loadRenderingHandlers();
        ItemMixingBowlRenderer.initColors();
    }
}
