package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import dk.mrspring.kitchen.tileentity.TileEntityToaster;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class BlockToaster extends BlockContainerBase
{
    public BlockToaster()
    {
        super("toaster", TileEntityToaster.class);
        float pixel = 0.0625F;
        this.setBlockBounds(3 * pixel, 0, 3 * pixel, 1 - 3 * pixel, 0.5F - pixel, 1 - 3 * pixel);
        this.setHardness(4.0F);
        this.setStepSound(Block.soundTypePiston);
        this.rotationAngles = 8;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        if (!world.isRemote)
        {
            TileEntityToaster tileEntity = (TileEntityToaster) world.getTileEntity(x, y, z);
            if (activator.isSneaking())
            {
                if (activator.getCurrentEquippedItem() == null)
                    if (tileEntity.startCooking())
                    {
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    } else return false;
            } else
            {
                if (activator.getCurrentEquippedItem() != null)
                    if (tileEntity.addItem(activator.getCurrentEquippedItem()))
                    {
                        activator.getCurrentEquippedItem().stackSize--;
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    } else return false;
                else
                {
                    ItemStack removedItem = tileEntity.removeItem();
                    if (removedItem != null)
                    {
                        if (removedItem.stackSize < 1)
                            removedItem.stackSize = 1;
                        world.spawnEntityInWorld(new EntityItem(world, x, y, z, removedItem));
                        world.markBlockForUpdate(x, y, z);
                    }
                    return true;
                }
            }
        }
        world.markBlockForUpdate(x, y, z);
        return false;
    }

    @Override
    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
        super.onBlockBroken(player, world, x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityToaster)
        {
            TileEntityToaster toaster = (TileEntityToaster) entity;
            spawnItem(toaster.getStack1(), world, x, y, z);
            spawnItem(toaster.getStack2(), world, x, y, z);
        }
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
