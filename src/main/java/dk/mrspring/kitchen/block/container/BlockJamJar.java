package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.jam.Jam;
import dk.mrspring.kitchen.tileentity.TileEntityJamJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class BlockJamJar extends BlockContainer
{
	public BlockJamJar(String name)
	{
		super(Material.glass);
		this.setBlockName(name);
		this.setBlockTextureName(ModInfo.modid + ":" + name);
		this.setCreativeTab(Kitchen.instance.tab);
		float pixel = 0.0625F;
		this.setBlockBounds(3 * pixel, 0, 3 * pixel, 1 - (3 * pixel), 12 * pixel, 1 - (3 * pixel));
	}

	@Override
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
	{
		return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntityJamJar tileEntity = (TileEntityJamJar) world.getTileEntity(x, y, z);
		ItemStack stack = new ItemStack(KitchenBlocks.jam_jar, 1, 1);
		if (tileEntity.getJam() != Jam.EMPTY)
		{
			stack = Kitchen.getJamItemStack(tileEntity.getJam(), tileEntity.getUsesLeft());
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack));
			System.out.println("Spawning Entity from Kitchen");
		} else
		{
			stack.setItemDamage(0);
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack));
			System.out.println("Spawning Empty Jar");
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityJamJar(Jam.EMPTY, 0);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}
}