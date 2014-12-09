package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class BlockWaffleIron extends BlockContainerBase
{
    public BlockWaffleIron()
    {
        super("waffle_iron", TileEntityWaffleIron.class);
        float pixel = 0.0625F;
        this.setBlockBounds(2 * pixel, 0, 2 * pixel, 1 - 2 * pixel, 0.5F, 1 - 2 * pixel);
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
