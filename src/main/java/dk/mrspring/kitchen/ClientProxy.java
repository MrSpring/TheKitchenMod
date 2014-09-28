package dk.mrspring.kitchen;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.mrspring.kitchen.item.render.ItemRenderSandwich;
import dk.mrspring.kitchen.tileentity.*;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoard.class, new TileEntityBoardSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenSpecialRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TileEntityPlateSpecialRenderer());

		MinecraftForgeClient.registerItemRenderer(GameRegisterer.findItem("sandwich"), (IItemRenderer) new ItemRenderSandwich());
	}
}
