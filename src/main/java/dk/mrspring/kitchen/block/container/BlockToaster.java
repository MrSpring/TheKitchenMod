package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import dk.mrspring.kitchen.tileentity.TileEntityToaster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class BlockToaster extends BlockContainerBase
{
    public BlockToaster()
    {
        // TODO: Set Hardness, Resistance
        super("toaster", TileEntityToaster.class);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
    {
        int direction = MathHelper.floor_double((double) (player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7;
        super.onBlockPlacedBy(world, x, y, z, player, itemStack);

        world.setBlockMetadataWithNotify(x, y, z, direction, 2);

        if (itemStack.getTagCompound() != null)
            if (itemStack.getTagCompound().getTag("PlateData") != null)
            {
                TileEntityPlate tileEntityPlate = (TileEntityPlate) world.getTileEntity(x, y, z);
                tileEntityPlate.readItemsFromNBT((NBTTagCompound) itemStack.getTagCompound().getTag("PlateData"));
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
