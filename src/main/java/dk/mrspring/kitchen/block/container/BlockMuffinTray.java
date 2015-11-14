package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityMuffinTray;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created on 13-11-2015 for TheKitchenMod.
 */
public class BlockMuffinTray extends BlockContainerBase
{
    public BlockMuffinTray()
    {
        super("muffin_tray", TileEntityMuffinTray.class);

        this.rotationAngles = 4;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        if (!world.isRemote)
        {
            TileEntityMuffinTray entity = (TileEntityMuffinTray) world.getTileEntity(x, y, z);
            if (entity.rightClick(clicker.getCurrentEquippedItem(), clicker, clickX, clickZ))
                world.markBlockForUpdate(x, y, z);
            else return false;
            return true;
        } else
        {
            world.markBlockForUpdate(x, y, z);
            return false;
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        float p = 0.0625F;
        if (meta == 0)
            this.setBlockBounds(1.5F * p, 0F, 5F * p, 1F - 1.5F * p, 3 * p, 1F - 2F * p);
        else if (meta == 1)
            this.setBlockBounds(2F * p, 0F, 1.5F * p, 1F - 5F * p, 3 * p, 1F - 1.5F * p);
        else if (meta == 2)
            this.setBlockBounds(1.5F * p, 0F, 2F * p, 1F - 1.5F * p, 3 * p, 1F - 5F * p);
        else if (meta == 3)
            this.setBlockBounds(5F * p, 0F, 1.5F * p, 1F - 2F * p, 3 * p, 1F - 1.5F * p);
        else this.setBlockBounds(0F, 0F, 0F, 1F, 3 * p, 1F);
    }

    @Override
    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
        super.onBlockBroken(player, world, x, y, z);

        TileEntityMuffinTray entity = (TileEntityMuffinTray) world.getTileEntity(x, y, z);
        ItemStack stack = entity.makeDropStack();
        spawnItem(stack, world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, itemStack);
        TileEntityMuffinTray tray = (TileEntityMuffinTray) world.getTileEntity(x, y, z);
        tray.onPlaced(itemStack);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
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
