package dk.mrspring.kitchen;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.mrspring.kitchen.config.ClientConfig;
import dk.mrspring.kitchen.item.render.ItemRenderJamJar;
import dk.mrspring.kitchen.item.render.ItemRenderSandwich;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.item.render.block.ItemRenderFryingPan;
import dk.mrspring.kitchen.item.render.block.ItemRenderKitchenCabinet;
import dk.mrspring.kitchen.item.render.block.ItemRenderOven;
import dk.mrspring.kitchen.item.render.block.ItemRenderPlate;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.tileentity.renderer.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    public static ClientConfig clientConfig;

    public void getConfigs()
    {
        super.getConfigs();
        clientConfig = ModConfig.getClientConfig();
    }

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoard.class, new TileEntityBoardRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TileEntityPlateRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKitchenCabinet.class, new TileEntityKitchenCabinetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPan.class, new TileEntityPanRenderer());

        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("sandwich"), new ItemRenderSandwich());
        MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("jam_jar"), new ItemRenderJamJar());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("oven")), new ItemRenderOven());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("frying_pan")), new ItemRenderFryingPan());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("plate")), new ItemRenderPlate());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GameRegisterer.findBlock("kitchen_cabinet")), new ItemRenderKitchenCabinet());

        SandwichRender.loadRenderingHandlers();
    }
}
