package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

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
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        double pixel = 0.0625;
        TileEntityWaffleIron tileEntityWaffleIron = (TileEntityWaffleIron) world.getTileEntity(x, y, z);
        int cookTime = tileEntityWaffleIron.getCookTime();
        if (cookTime > 400 && !tileEntityWaffleIron.isOpen())
            world.spawnParticle("smoke", x + 4 * pixel + (random.nextDouble() * (8 * pixel)), y + 0.4, z + 4 * pixel + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
        if (cookTime > 600 && !tileEntityWaffleIron.isOpen())
            world.spawnParticle("flame", x + 4 * pixel + (random.nextDouble() * (8 * pixel)), y + 0.5, z + 4 * pixel + (random.nextDouble() * (8 * pixel)), 0, 0, 0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (!world.isRemote)
        {
            if (activator.isSneaking())
            {
                if (activator.getCurrentEquippedItem() == null)
                {
                    TileEntityWaffleIron tileEntity = (TileEntityWaffleIron) world.getTileEntity(x, y, z);
                    tileEntity.toggleOpen();
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            } else
            {
                if (activator.getCurrentEquippedItem() != null)
                {
                    System.out.println("Equipped item is not null!");
                    if (activator.getCurrentEquippedItem().getItem() == KitchenItems.mixing_bowl && activator.getCurrentEquippedItem().getItemDamage() > 0)
                    {
                        System.out.println("Equipped item is Mixing Bowl!");
                        TileEntityWaffleIron tileEntity = (TileEntityWaffleIron) world.getTileEntity(x, y, z);
                        if (tileEntity.addWaffleDough())
                        {
                            System.out.println("Adding dough!");
                            world.markBlockForUpdate(x, y, z);
                            activator.getCurrentEquippedItem().setItemDamage(activator.getCurrentEquippedItem().getItemDamage() - 1);
                        } else return false;
                    }
                } else
                {
                    TileEntityWaffleIron tileEntity = (TileEntityWaffleIron) world.getTileEntity(x, y, z);
                    return tileEntity.finishWaffle();
                }
            }
        } else world.markBlockForUpdate(x, y, z);

        return true;
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
