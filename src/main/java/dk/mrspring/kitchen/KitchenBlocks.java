package dk.mrspring.kitchen;

import dk.mrspring.kitchen.block.BlockBase;
import dk.mrspring.kitchen.block.container.*;
import dk.mrspring.kitchen.block.decorative.BlockKitchenCabinet;
import dk.mrspring.kitchen.block.plant.BlockCropBase;
import dk.mrspring.kitchen.block.plant.BlockWildBase;
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

    // Crops
    public static final Block tomato_crop = new BlockCropBase("tomato", "kitchen:tomato");
    public static final Block lettuce_crop = new BlockCropBase("lettuce", "kitchen:lettuce");
    public static final Block peanut_crop = new BlockCropBase("peanut", "kitchen:peanuts_in_shell");
    public static final Block strawberry_crop = new BlockCropBase("strawberry", "kitchen:strawberry");
    public static final Block vanilla_crop = new BlockCropBase("vanilla", "kitchen:raw_vanilla");

    // Wild Plants
    public static final Block wild_tomato = new BlockWildBase("tomato", "kitchen:tomato");
    public static final Block wild_lettuce = new BlockWildBase("lettuce", "kitchen:lettuce");
    public static final Block wild_peanut = new BlockWildBase("peanut", "kitchen:peanuts_in_shell");
    public static final Block wild_strawberry = new BlockWildBase("strawberry", "kitchen:strawberry");
    public static final Block wild_vanilla = new BlockWildBase("vanilla", "kitchen:raw_vanilla");

    // "Machines"
    public static final Block board = new BlockBoard();
    public static final Block oven = new BlockOven();
    public static final Block plate = new BlockPlate();
    public static final Block frying_pan = new BlockFryingPan();
    public static final Block waffle_iron = new BlockWaffleIron();
    public static final Block toaster = new BlockToaster();
}
