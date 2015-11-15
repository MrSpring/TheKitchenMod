package dk.mrspring.kitchen;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.mrspring.kitchen.entity.EntityDingFX;
import dk.mrspring.kitchen.item.render.*;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.tileentity.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingCabinet.class, new TileEntityCraftingCabinetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMuffinTray.class, new TileEntityMuffinTrayRenderer());

        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("sandwich"), new ItemRenderSandwich());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("jam_jar"), new ItemRenderJamJar());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("pancake"), new ItemIceCreamableRenderer());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("waffle"), new ItemIceCreamableRenderer());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("ice_cream_cone"), new ItemIceCreamConeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("muffin_tray")), new ItemMuffinTrayRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("oven")), new ItemRenderOven());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("frying_pan")), new ItemRenderFryingPan());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("toaster")), new ItemRenderToaster());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("waffle_iron")), new ItemRenderWaffleIron());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("plate")), new ItemRenderPlate());

        ItemIceCreamableRenderer.load();

        ItemRenderMixingBowl.COLOR_HANDLER.loadDefaults();
        ItemRenderJamJar.COLOR_HANDLER.loadDefaults();
        ItemRenderMuffin.COLOR_HANDLER.loadDefaults();

        /*try
        {
            InputStream inputStream = new URL("http://mrspring.dk/mods/kitchen/update_highlights.php?version=" + ModInfo.version).openStream();
            versionHighlights = IOUtils.toString(inputStream);
        } catch (Exception e)
        {
            ModLogger.print(ModLogger.DEBUG, "Failed to download the version highlights, they will not be listed in the book!");
        }*/
    }

    @Override
    public void spawnDingParticle(World world, double posX, double posY, double posZ)
    {
        EntityDingFX particle = new EntityDingFX(world, posX, posY, posZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
