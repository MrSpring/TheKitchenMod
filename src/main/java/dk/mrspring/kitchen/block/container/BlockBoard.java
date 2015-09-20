package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api_impl.common.registry.BoardEventRegistry;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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

        this.rotationAngles = 4;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        TileEntityBoard entity = (TileEntityBoard) world.getTileEntity(x, y, z);
        if (!world.isRemote)
            if (entity.rightClicked(activator.getCurrentEquippedItem(), activator)) world.markBlockForUpdate(x, y, z);
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
        if (metadata % 2 == 0)
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
        else if (metadata % 2 == 1)
            this.setBlockBounds(0.0625F * 3, 0.0F, 0.0625F, 1.0F - (0.0625F * 3), 0.0625F * 2, 1.0F - 0.0625F);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
    }

    @Override
    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
        super.onBlockBroken(player, world, x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityBoard)
        {
            TileEntityBoard board = (TileEntityBoard) tileEntity;
            List<ItemStack> stacks = board.getLayers();
            for (ItemStack stack : stacks)
            {
                IBoardItemHandler handler = BoardEventRegistry.instance().getHandlerFor(board, stack, player);
                ItemStack drop = handler.onRemoved(board, stack, player);
                spawnItem(drop, world, x, y, z);
            }
        }
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
