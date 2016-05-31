package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBoard extends BlockContainerBase
{
    public BlockBoard()
    {
        super(Material.wood, "board", "minecraft:planks_oak", TileEntityBoard.class);

        this.setStepSound(soundTypeWood);
        this.setHardness(2.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileEntityBoard entity = (TileEntityBoard) world.getTileEntity(x, y, z);

        if (!world.isRemote)
        {
            if (!activator.isSneaking())
            {
                if (activator.getCurrentEquippedItem() != null)
                {
                    if (entity.rightClicked(activator.getCurrentEquippedItem(), true))
                    {
                        --activator.getCurrentEquippedItem().stackSize;
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    } else world.markBlockForUpdate(x, y, z);
                } else
                {
                    ItemStack removedItemStack = entity.removeTopItem();
                    if (removedItemStack != null)
                    {
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, removedItemStack));
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    } else world.markBlockForUpdate(x, y, z);
                }
            } else
            {
                ItemStack sandwich = entity.finishSandwich();
                if (sandwich != null)
                {
                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, sandwich));
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TileEntityBoard board = (TileEntityBoard) world.getTileEntity(x, y, z);

        if (board != null)
            while (board.getLayers().size() > 0)
                spawnItem(board.removeTopItem(), world, x, y, z);

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 0)
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
        else if (metadata == 1)
            this.setBlockBounds(0.0625F * 3, 0.0F, 0.0625F, 1.0F - (0.0625F * 3), 0.0625F * 2, 1.0F - 0.0625F);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placed)
    {
        int direction = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (direction == 0 || direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);

        else if (direction == 1 || direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);

        super.onBlockPlacedBy(world, x, y, z, placer, placed);
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
}
