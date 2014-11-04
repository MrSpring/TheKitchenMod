package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.tileentity.TileEntityPan;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class BlockFryingPan extends BlockContainerBase
{
    public BlockFryingPan()
    {
        super("frying_pan");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityPan();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		TileEntityPan tileEntityPan = (TileEntityPan) world.getTileEntity(x, y, z);

        if (tileEntityPan != null && !world.isRemote)
        {
			System.out.println("Right Clidked! World is not remote!");
            if (tileEntityPan.rightClicked(player.getCurrentEquippedItem()))
            {
				System.out.println("Subtracting stackSize!");
				player.getCurrentEquippedItem().stackSize--;
                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.getBlock(x, y - 1, z) == KitchenBlocks.oven;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placed)
    {
        int rotation = (MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        super.onBlockPlacedBy(world, x, y, z, placer, placed);

        world.setBlockMetadataWithNotify(x, y, z, rotation, 0);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }
}
