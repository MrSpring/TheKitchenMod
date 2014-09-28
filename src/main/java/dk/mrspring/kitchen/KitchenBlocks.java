package dk.mrspring.kitchen;

import dk.mrspring.kitchen.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class KitchenBlocks
{
	// All the block variables
	public static final Block tiles = new BlockBase(Material.iron, "tiles", true);
	public static final Block board = new BlockBoard();
	public static final Block tomato_crop = new BlockTomatoCrop();
	public static final Block lettuce_crop = new BlockLettuceCrop();
	public static final Block wild_tomato = new BlockWildTomato();
	public static final Block wild_lettuce = new BlockWildLettuce();
	public static final Block oven = new BlockOven();
	public static final Block plate = new BlockPlate();
}
