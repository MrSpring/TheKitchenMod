package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockContainerBase extends BlockContainer
{
    Class<? extends TileEntity> tileEntityClass;

    protected BlockContainerBase(Material material, String name, String textureName, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
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
        this(material, name, ModInfo.modid + ":" + name, useCreativeTab, tileEntityClass);
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
        this(name, ModInfo.modid + ":" + name, useCreativeTab, tileEntityClass);
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        try
        {
            Constructor<? extends TileEntity> constructor = tileEntityClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void spawnItem(ItemStack item, World world, int x, int y, int z)
    {
        if (item != null)
        {
            Random random = new Random();

            float xRandPos = random.nextFloat() * 0.8F + 0.1F;
            float yRandPos = random.nextFloat() * 0.8F + 0.1F;
            float zRandPos = random.nextFloat() * 0.8F + 0.1F;

            EntityItem entityItem = new EntityItem(world, x + xRandPos, y + yRandPos, z + zRandPos, item);

            entityItem.motionX = random.nextGaussian() * 0.05F;
            entityItem.motionY = random.nextGaussian() * 0.05F + 0.2F;
            entityItem.motionZ = random.nextGaussian() * 0.05F;

            world.spawnEntityInWorld(entityItem);
        }
    }

    public Class getTileEntityClass()
    {
        return tileEntityClass;
    }
}
