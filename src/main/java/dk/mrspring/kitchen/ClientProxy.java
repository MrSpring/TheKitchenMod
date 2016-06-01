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
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.ItemBaseRenderingHandler;
import dk.mrspring.kitchen.pan.JamBaseRenderingHandler;
import dk.mrspring.kitchen.tileentity.*;
import dk.mrspring.kitchen.tileentity.renderer.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import static dk.mrspring.kitchen.ClientUtils.scale;
import static dk.mrspring.kitchen.ClientUtils.translate;

public class ClientProxy extends CommonProxy
{
    public static ClientConfig clientConfig;

    @Override
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

        Ingredient.bindRenderingHandler("strawberry", new JamBaseRenderingHandler(0xFF3C35));
        Ingredient.bindRenderingHandler("apple", new JamBaseRenderingHandler(0xE0FFA3));
        Ingredient.bindRenderingHandler("peanut", new JamBaseRenderingHandler(0x936529));
        Ingredient.bindRenderingHandler("bacon", new IIngredientRenderingHandler()
        {
            ModelBaconRaw rawBaconModel = new ModelBaconRaw();
            ModelBaconCooked cookedBaconModel = new ModelBaconCooked();

            @Override
            public boolean translateModel(int cookTime, Ingredient ingredient)
            {
                return true;
            }

            @Override
            public void render(int cookTime, Ingredient ingredient)
            {
                float s = 0.4F;
                translate(0F, (1F - s) * 1.5F, 0F);
                scale(s);
                if (cookTime >= 300) cookedBaconModel.simpleRender(0F);
                else rawBaconModel.simpleRender(0F);
            }
        });
        Ingredient.bindRenderingHandler("chicken_fillet", new ItemBaseRenderingHandler(KitchenItems.raw_chicken_fillet, KitchenItems.chicken_fillet));
    }
}
