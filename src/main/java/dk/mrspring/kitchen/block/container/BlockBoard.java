package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api_impl.common.registry.BoardEventRegistry;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockBoard extends BlockContainerBase
{
    public BlockBoard()
    {
        super(Material.wood, "board", "minecraft:planks_oak", TileEntityBoard.class);

        this.setStepSound(soundTypeWood);
        this.setHardness(2.0F);
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        TileEntityBoard entity = (TileEntityBoard) world.getTileEntity(x, y, z);
        if (!world.isRemote)
            if (entity.rightClicked(activator.getCurrentEquippedItem(), activator))
                world.markBlockForUpdate(x, y, z);
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
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
