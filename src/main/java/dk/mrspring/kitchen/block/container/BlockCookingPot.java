package dk.mrspring.kitchen.block.container;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class BlockCookingPot extends BlockContainerBase
{
	public BlockCookingPot()
	{
		super("cooking_pot");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return super.createNewTileEntity(world, metadata);
	}
}
