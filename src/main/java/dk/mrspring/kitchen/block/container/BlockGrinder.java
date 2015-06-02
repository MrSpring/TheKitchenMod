package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityGrinder;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Konrad on 02-06-2015.
 */
public class BlockGrinder extends BlockContainerBase
{
    public BlockGrinder()
    {
        super("grinder", TileEntityGrinder.class);
    }

    @Override
    public int getRenderType()
    {
        return -1;
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
}
