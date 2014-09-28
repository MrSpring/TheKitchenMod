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

public class BlockTomatoCrop extends BlockCrops
{
	private IIcon[] icons;
	
	public BlockTomatoCrop()
	{
		super();
		
		this.setBlockName("tomato_crop");
		this.setBlockTextureName(ModInfo.modid + ":tomato_crop");
		
		this.setCreativeTab(null);
	}
	
	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return this.icons[p_149691_2_];
	}
	
	protected Item func_149866_i()
	{
		return KitchenItems.tomato;
	}
	
	protected Item func_149865_P()
	{
		return KitchenItems.tomato;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.icons = new IIcon[8];
		
		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + i);
		}
	}
}
