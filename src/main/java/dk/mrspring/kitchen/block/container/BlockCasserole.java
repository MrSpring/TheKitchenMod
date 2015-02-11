package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityCasserole;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Konrad on 27-01-2015.
 */
public class BlockCasserole extends BlockContainerBase
{
    public BlockCasserole()
    {
        super("casserole", TileEntityCasserole.class);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        world.markBlockForUpdate(x, y, z);
        if (!world.isRemote)
        {
            TileEntityCasserole tileEntity = (TileEntityCasserole) world.getTileEntity(x, y, z);
            if (activator.getCurrentEquippedItem() != null)
                tileEntity.onRightClicked(activator.getCurrentEquippedItem());
            else
            {
                ItemStack[] removedLayer = tileEntity.removeTopLayer();
                for (ItemStack stack : removedLayer)
                    if (stack != null)
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 2, z + 0.5, stack));
            }
        }
        return true;
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

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
