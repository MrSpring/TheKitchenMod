package dk.mrspring.kitchen;

import dk.mrspring.kitchen.block.*;
import dk.mrspring.kitchen.block.plant.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class KitchenBlocks
{
	// All the block variables
	public static final Block tiles = new BlockBase(Material.iron, "tiles", true);

	public static final Block tomato_crop = new BlockCropBase("tomato", KitchenItems.tomato);
	public static final Block lettuce_crop = new BlockCropBase("lettuce", KitchenItems.lettuce);

	public static final Block wild_tomato = new BlockWildBase("tomato",KitchenItems.tomato);
	public static final Block wild_lettuce = new BlockWildBase("lettuce",KitchenItems.lettuce);

	public static final Block board = new BlockBoard();
	public static final Block oven = new BlockOven();
	public static final Block plate = new BlockPlate();
}
