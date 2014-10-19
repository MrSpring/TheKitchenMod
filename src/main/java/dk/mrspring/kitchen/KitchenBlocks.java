package dk.mrspring.kitchen;

import dk.mrspring.kitchen.block.*;
import dk.mrspring.kitchen.block.container.*;
import dk.mrspring.kitchen.block.decorative.BlockKitchenCabinet;
import dk.mrspring.kitchen.block.plant.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class KitchenBlocks
{
	/*
	 * All Block variables
	 */

	// Decorative Blocks
	public static final Block tiles = new BlockBase(Material.rock, "tiles", true).setHardness(1.5F).setResistance(10.0F);
	public static final Block kitchen_cabinet = new BlockKitchenCabinet();

	public static final Block tomato_crop = new BlockCropBase("tomato", KitchenItems.tomato);
	public static final Block lettuce_crop = new BlockCropBase("lettuce", KitchenItems.lettuce);

	public static final Block wild_tomato = new BlockWildBase("tomato", KitchenItems.tomato);
	public static final Block wild_lettuce = new BlockWildBase("lettuce", KitchenItems.lettuce);

	public static final Block board = new BlockBoard();
	public static final Block oven = new BlockOven();
	public static final Block plate = new BlockPlate();
	public static final Block cooking_pot = new BlockCookingPot();

	public static final Block jam_jar = new BlockJamJar("jam_jar_block");
}
