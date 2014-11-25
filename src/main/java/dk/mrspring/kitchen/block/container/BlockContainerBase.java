package dk.mrspring.kitchen.block.container;

import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockContainerBase extends BlockContainer
{
    Class tileEntityClass;

    protected BlockContainerBase(Material material, String name, String textureName, boolean useCreativeTab, Class tileEntityClass)
    {
        super(material);

        this.setBlockName(name);
        this.setBlockTextureName(textureName);

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
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        try
        {
            Constructor constructor = tileEntityClass.getConstructor();
            return (TileEntity) constructor.newInstance();
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

    public Class getTileEntityClass()
    {
        return tileEntityClass;
    }


}
