package dk.mrspring.kitchen.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;

public class ItemLettuce extends ItemSeedFood
{
	public ItemLettuce()
	{
		super(2, 0.3F, KitchenBlocks.lettuce_crop, Blocks.farmland);
		
		this.setUnlocalizedName("lettuce");
		this.setTextureName(ModInfo.modid + ":lettuce");
		
		this.setCreativeTab(Kitchen.instance.tab);
	}
}
