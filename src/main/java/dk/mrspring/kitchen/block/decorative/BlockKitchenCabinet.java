package dk.mrspring.kitchen.block.decorative;

import dk.mrspring.kitchen.block.container.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityKitchenCabinet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockKitchenCabinet extends BlockContainerBase
{
	public BlockKitchenCabinet()
	{
		super(Material.wood, "kitchen_cabinet", TileEntityKitchenCabinet.class);

		this.setStepSound(Block.soundTypeWood);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack placed)
	{
		int rotation = (MathHelper.floor_double((double) (player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7);
		super.onBlockPlacedBy(world, x, y, z, player, placed);

		world.setBlockMetadataWithNotify(x, y, z, rotation, 0);
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
