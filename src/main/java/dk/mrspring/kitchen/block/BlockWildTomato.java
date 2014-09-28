package dk.mrspring.kitchen.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;

public class BlockWildTomato extends BlockBush
{
	public BlockWildTomato()
	{
		super();
		this.setBlockName("wild_tomato");
		this.setBlockTextureName(ModInfo.modid + ":wild_tomato");
		this.setTickRandomly(true);
		this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.8F, 0.2F * 3.0F, 0.8F);
		
		this.setCreativeTab(null);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return KitchenItems.tomato;
	}
	
	@Override
	public int quantityDropped(Random p_149745_1_)
	{
		return 1;
	}
}
