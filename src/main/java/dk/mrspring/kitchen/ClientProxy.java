package dk.mrspring.kitchen;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.mrspring.kitchen.item.render.*;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.tileentity.renderer.*;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;

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
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("pancake"), new ItemIceCreamableRenderer());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("waffle"), new ItemIceCreamableRenderer());

        SandwichRender.loadRenderingHandlers();
        ItemMixingBowlRenderer.initColors();

        try
        {
            InputStream inputStream = new URL("http://mrspring.dk/mods/kitchen/update_highlights.php?version=" + ModInfo.version).openStream();
            versionHighlights = IOUtils.toString(inputStream);
        } catch (Exception e)
        {
            ModLogger.print(ModLogger.DEBUG, "Failed to download the version highlights, they will not be listed in the book!");
        }
    }
}
