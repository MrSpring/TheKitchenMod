package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockContainerBase extends BlockContainer
{
	protected BlockContainerBase(Material material, String name, String textureName, boolean useCreativeTab)
	{
		super(material);

		this.setBlockName(name);
		this.setBlockTextureName(textureName);

		if (useCreativeTab)
			this.setCreativeTab(Kitchen.instance.tab);
	}

	protected BlockContainerBase(Material material, String name, boolean useCreativeTab)
	{
		this(material, name, ModInfo.modid + ":" + name, useCreativeTab);
	}

	protected BlockContainerBase(Material material, String name)
	{
		this(material, name, true);
	}

	protected BlockContainerBase(String name, String textureName, boolean useCreativeTab)
	{
		this(Material.iron, name, textureName, useCreativeTab);
	}

	protected BlockContainerBase(String name, boolean useCreativeTab)
	{
		this(name, ModInfo.modid + ":" + name, useCreativeTab);
	}

	protected BlockContainerBase(String name, String textureName)
	{
		this(name, textureName, true);
	}

	protected BlockContainerBase(String name)
	{
		this(name, true);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return null;
	}
}
