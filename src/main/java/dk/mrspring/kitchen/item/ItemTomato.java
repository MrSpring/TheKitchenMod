package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;

public class ItemTomato extends ItemSeedFood{

	public ItemTomato()
	{
		super(2, 0.3F, KitchenBlocks.tomato_crop, Blocks.farmland);
		
		this.setUnlocalizedName("tomato");
		this.setTextureName(ModInfo.modid + ":tomato");
		
		this.setCreativeTab(Kitchen.instance.tab);
	}

}
