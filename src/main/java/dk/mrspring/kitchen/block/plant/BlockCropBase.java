package dk.mrspring.kitchen.block.plant;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockCropBase extends BlockCrops
{
	private IIcon[] icons;
	Item drops;

	public BlockCropBase(String namePrefix, Item dropped)
	{
		super();

		this.drops = dropped;

		this.setBlockName(namePrefix + "_crop");
		this.setBlockTextureName(ModInfo.modid + ":" + namePrefix + "_crop");

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
		} else
			return this.icons[3];
	}

	protected Item func_149866_i()
	{
		return this.drops;
	}

	protected Item func_149865_P()
	{
		return this.drops;
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
