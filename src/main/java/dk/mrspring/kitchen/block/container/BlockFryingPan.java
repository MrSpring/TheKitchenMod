package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityPan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class BlockFryingPan extends BlockContainerBase
{
    public BlockFryingPan()
    {
        super("frying_pan", TileEntityPan.class);

        this.setTickRandomly(true);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y - 1, z);
        float pixel = 0.0625F, height = 3 * pixel;
        if (world.getBlock(x, y - 1, z) == KitchenBlocks.oven)
            switch (metadata)
            {
                case 0:
                    this.setBlockBounds(0.5F, 0, 2 * pixel, 1F, height, 10 * pixel);
                    break;
                case 1:
                    this.setBlockBounds(6 * pixel, 0, 0.5F, 14 * pixel, height, 1);
                    break;
                case 2:
                    this.setBlockBounds(0, 0, 6 * pixel, 0.5F, height, 14 * pixel);
                    break;
                case 3:
                    this.setBlockBounds(2 * pixel, 0, 0, 10 * pixel, height, 0.5F);
                    break;
            }
        else this.setBlockBounds(4 * pixel, 0, 4 * pixel, 12 * pixel, height, 12 * pixel);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        super.randomDisplayTick(world, x, y, z, random);

        int metadata = world.getBlockMetadata(x, y - 1, z);
        float pixel = 0.06125F;
        TileEntityPan tileEntityPan = (TileEntityPan) world.getTileEntity(x, y, z);

        if (tileEntityPan.isFinished())
            if (world.getBlock(x, y - 1, z) != KitchenBlocks.oven)
                world.spawnParticle("smoke",
                        x + 4 * pixel + (random.nextDouble() * (8 * pixel)),
                        y + 2 * pixel,
                        z + 4 * pixel + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
            else
                switch (metadata)
                {
                    case 0:
                        world.spawnParticle("smoke",
                                x + 0.5F + (random.nextDouble() * (8 * pixel)),
                                y + 3 * pixel,
                                z + 2 * pixel + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
                        break;
                    case 1:
                        world.spawnParticle("smoke",
                                x + 0.5F - (2 * pixel) + (random.nextDouble() * (8 * pixel)),
                                y + 3 * pixel,
                                z + 0.5F + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
                        break;
                    case 2:
                        world.spawnParticle("smoke",
                                x + (random.nextDouble() * (8 * pixel)),
                                y + 3 * pixel,
                                z + 0.5F - (2 * pixel) + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
                        break;
                    case 3:
                        world.spawnParticle("smoke",
                                x + 2 * pixel + (random.nextDouble() * (8 * pixel)),
                                y + 3 * pixel,
                                z + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
                        break;
                }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        TileEntityPan pan = (TileEntityPan) world.getTileEntity(x, y, z);
        pan.checkIsFunctional();
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
        TileEntityPan tileEntityPan = (TileEntityPan) world.getTileEntity(x, y, z);
        tileEntityPan.checkIsFunctional();
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        TileEntityPan tileEntityPan = (TileEntityPan) world.getTileEntity(x, y, z);

        if (tileEntityPan != null && !world.isRemote)
            if (tileEntityPan.rightClicked(player.getCurrentEquippedItem(), player))
            {
                tileEntityPan.checkIsFunctional();
                world.markBlockForUpdate(x, y, z);
                return true;
            }

        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.getBlock(x, y - 1, z) == KitchenBlocks.oven;
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
