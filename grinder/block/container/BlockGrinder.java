package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created on 16-11-2015 for TheKitchenMod.
 */
public class BlockGrinder extends BlockContainerBase
{
    public BlockGrinder()
    {
        super("grinder", TileEntityGrinder.class);

        this.rotationAngles = 4;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        if (!world.isRemote)
        {
            TileEntityGrinder grinder = (TileEntityGrinder) world.getTileEntity(x, y, z);
            if (grinder.rightClick(clicker.getCurrentEquippedItem(), clicker.isSneaking()))
                world.markBlockForUpdate(x, y, z);
            else return false;
        }
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        float p = 0.0625F;
        if (meta == 0)
            this.setBlockBounds(4F * p, 0F, 4F * p, 1F - 4 * p, 12 * p, 1F - 2F * p);
        else if (meta == 1)
            this.setBlockBounds(2F * p, 0F, 4F * p, 1F - 4F * p, 12 * p, 1F - 4F * p);
        else if (meta == 2)
            this.setBlockBounds(4F * p, 0F, 2F * p, 1F - 4F * p, 12 * p, 1F - 4F * p);
        else if (meta == 3)
            this.setBlockBounds(4F * p, 0F, 4F * p, 1F - 2F * p, 12 * p, 1F - 4F * p);
        else this.setBlockBounds(0F, 0F, 0F, 1F, 12 * p, 1F);
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
