package dk.mrspring.kitchen.block;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.tileentity.TileEntityTimeable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockContainerBase extends BlockContainer
{
    Class tileEntityClass;
    public int rotationAngles = 0;
    public boolean enableTimer = true;

    protected BlockContainerBase(Material material, String name, String textureName, boolean useCreativeTab, Class tileEntityClass)
    {
        super(material);

        this.setBlockName(name);
        this.setBlockTextureName(textureName);

        this.setHardness(4.0F);

        this.tileEntityClass = tileEntityClass;

        if (useCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
    }

    protected BlockContainerBase(Material material, String name, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, ModInfo.toTexture(name), useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(Material material, String name, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, String textureName, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(Material.iron, name, textureName, useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(String name, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, ModInfo.toTexture(name), useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(Material material, String name, String textureName, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, textureName, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, String textureName, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, textureName, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, true, tileEntityClass);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (enableTimer && tileEntity instanceof TileEntityTimeable && !world.isRemote)
            if (!activator.isSneaking())
                if (activator.getCurrentEquippedItem() != null)
                    if (activator.getCurrentEquippedItem().getItem() == KitchenItems.timer && !((TileEntityTimeable) tileEntity).getHasTimer())
                    {
                        ((TileEntityTimeable) tileEntity).setHasTimer(true);
                        activator.getCurrentEquippedItem().stackSize--;
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    }
        return onRightClicked(world, x, y, z, activator, side, clickX, clickY, clickZ);
    }

    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        try
        {
            return (TileEntity) tileEntityClass.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placed)
    {
        if (rotationAngles > 0)
        {
            int direction = MathHelper.floor_double((double) (placer.rotationYaw * (float) rotationAngles / 360.0F) + 0.5D) & (rotationAngles - 1);
            System.out.println(direction + ", " + rotationAngles);
            world.setBlockMetadataWithNotify(x, y, z, direction, 2);
        }
        super.onBlockPlacedBy(world, x, y, z, placer, placed);
    }

    public Class getTileEntityClass()
    {
        return tileEntityClass;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (enableTimer && tileEntity != null)
            if (tileEntity instanceof TileEntityTimeable)
                if (((TileEntityTimeable) tileEntity).getHasTimer())
                    spawnItem(new ItemStack(KitchenItems.timer), world, x, y, z);
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
    }

    public void spawnItem(ItemStack stack, World world, int x, int y, int z)
    {
        if (stack != null && stack.stackSize > 0)
        {
            Random rand = new Random();
            float xRand = rand.nextFloat() * 0.8F + 0.1F;
            float yRand = rand.nextFloat() * 0.8F + 0.1F;
            float zRand = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityitem = new EntityItem(world,
                    ((float) x + xRand), ((float) y + yRand), ((float) z + zRand), stack.copy());
            float f = 0.05F;
            entityitem.motionX = (double) ((float) rand.nextGaussian() * f);
            entityitem.motionY = (double) ((float) rand.nextGaussian() * f + 0.2F);
            entityitem.motionZ = (double) ((float) rand.nextGaussian() * f);
            world.spawnEntityInWorld(entityitem);
        }
    }
}
