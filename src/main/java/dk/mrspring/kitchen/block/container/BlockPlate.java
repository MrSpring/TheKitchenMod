package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlate extends BlockContainerBase
{
    public BlockPlate()
    {
        super(Material.anvil, "plate", TileEntityPlate.class);

        this.setBlockBounds(0.0F + (2 * 0.0635F), 0.0F, 0.0F + (2 * 0.0635F), 1.0F - (2 * 0.0635F), (3 * 0.0625F), 1.0F - (2 * 0.0635F));

        this.setTickRandomly(true);
        this.setHardness(4.0F);

        this.rotationAngles = 8;
    }

    /*@Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> toReturn = new ArrayList<ItemStack>();

        if (this.tileEntityPlate != null)
            for (ItemStack item : this.tileEntityPlate.getItemsAsArray())
            {
                if (item != null)
                    toReturn.add(item);
            }

        toReturn.add(new ItemStack(this, 1, 0));

        return toReturn;
    }*/

    @Override
    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
        super.onBlockBroken(player, world, x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityPlate)
        {
            TileEntityPlate plate = (TileEntityPlate) entity;
            for (ItemStack stack : plate.getItems()) spawnItem(stack, world, x, y, z);
        }
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer activator, int p_149727_6_, float clickX, float clickY, float clickZ)
    {
        TileEntityPlate tileEntityPlate = (TileEntityPlate) world.getTileEntity(x, y, z);

        world.markBlockForUpdate(x, y, z);

        if (!world.isRemote)
            if (!activator.isSneaking())
                if (activator.getCurrentEquippedItem() != null)
                    if (activator.getCurrentEquippedItem().getItem() != null)
                        if (tileEntityPlate.addItem(activator.getCurrentEquippedItem()))
                        {
                            --activator.getCurrentEquippedItem().stackSize;
                            return true;
                        } else
                            return false;
                    else
                        return false;
                else
                {
                    ItemStack itemStack = tileEntityPlate.removeTopItem();

                    if (itemStack != null)
                    {
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, itemStack));
                        return true;
                    } else
                        return true;
                }
            else
            {
                ItemStack item = new ItemStack(this, 1, 0);
                NBTTagCompound plateCompound = new NBTTagCompound();
                tileEntityPlate.writeItemsToNBT(plateCompound);
                item.setTagInfo("PlateData", plateCompound);
                world.setBlockToAir(x, y, z);
                world.spawnEntityInWorld(new EntityItem(world, x, y, z, item));
                return true;
            }
        else
            return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, itemStack);
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
