package dk.mrspring.kitchen.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;

public class BlockLettuceCrop extends BlockCrops
{
	private IIcon[] icons;
	
	public BlockLettuceCrop()
	{
		super();
		
		this.setBlockName("lettuce_crop");
		this.setBlockTextureName(ModInfo.modid + ":lettuce_crop");
		
		this.setCreativeTab(null);
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		if (metadata < 7)
		{
			if (metadata == 6)
				metadata = 5;
			
			return this.icons[metadata >> 1];
		}
		else
			return this.icons[3];
	}
	
	protected Item func_149866_i()
	{
		return KitchenItems.lettuce;
	}
	
	protected Item func_149865_P()
	{
		return KitchenItems.lettuce;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.icons = new IIcon[4];
		
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + i);
		}
	}
}
