package dk.mrspring.kitchen;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class KitchenItems
{
	// All the Item variables
	public static final Item knife = new ItemKnife().setMaxStackSize(1);
	public static final Item butter_knife = new ItemBase("butter_knife", true);
	public static final Item mortar_and_pestle = new ItemMandP().setMaxStackSize(1);

	public static final Item mortar = new ItemBase("mortar", true);
	public static final Item pestle = new ItemBase("pestle", true);

	public static final Item raw_bacon = new ItemBase("bacon_raw",true);
	public static final Item bacon = new ItemBase("bacon_cooked", true);

	public static final Item bread_slice = new ItemBase("bread_slice", true);
	public static final Item toast = new ItemBase("toast", true);

	public static final Item tomato = new ItemSeedBase("tomato", KitchenBlocks.tomato_crop,true);
	public static final Item lettuce = new ItemSeedBase("lettuce",KitchenBlocks.lettuce_crop,true);
	public static final Item tomato_slice = new ItemBase("tomato_slice", true);
	public static final Item lettuce_leaf = new ItemBase("lettuce_leaf", true);

	public static final Item potato_slice = new ItemBase("potato_slice", true);
	public static final Item carrot_slice = new ItemBase("carrot_slice", true);

	public static final Item flour = new ItemBase("flour", true);

	public static final Item raw_roast_beef = new ItemBase("raw_roast_beef", ModInfo.modid + ":beef_slice", true);
	public static final Item roast_beef = new ItemBase("roast_beef", true);

	public static final Item raw_chicken_fillet = new ItemBase("chicken_fillet_raw", true);
	public static final Item chicken_fillet = new ItemBase("chicken_fillet_cooked", true);

	public static final Item chicken_leg = new ItemFood(4, true).setUnlocalizedName("chicken_leg").setTextureName(ModInfo.modid + ":chicken_leg").setCreativeTab(Kitchen.instance.tab);
	public static final Item cheese = new ItemFood(3, false).setUnlocalizedName("cheese").setTextureName(ModInfo.modid + ":cheese").setCreativeTab(Kitchen.instance.tab);
	public static final Item cheese_slice = new ItemBase("cheese_slice", true);

	// The basic sandwich ItemStack. It's used as the icon fot the creative tab
	public static ItemStack basic_sandwich = getSandwichItemStackWithNBTTags(new ItemStack[] { new ItemStack(bread_slice, 1, 0), new ItemStack(raw_bacon, 1, 0), new ItemStack(bread_slice, 1, 0) });

    public static final Item burnt_meat = new ItemFood(1, false).setUnlocalizedName("burnt_meat").setTextureName(ModInfo.modid + ":burnt_meat").setCreativeTab(Kitchen.instance.tab);

    public static final Item butter = new ItemBase("butter", true);

	// Pre-loads the sandwich ItemStack with some NBT-Data.
	private static ItemStack getSandwichItemStackWithNBTTags(ItemStack[] layers)
	{
		ItemSandwich sandwich = new ItemSandwich();
		GameRegistry.registerItem(sandwich, "sandwich");
		
		ItemStack basicSandwich = new ItemStack(sandwich, 1, 0);
		
		NBTTagList layersList = new NBTTagList();

		for (ItemStack layer : layers)
		{
			NBTTagCompound layerCompound = new NBTTagCompound();
			layer.writeToNBT(layerCompound);
			layersList.appendTag(layerCompound);
		}
		
		basicSandwich.setTagInfo("SandwichLayers", layersList);
		
		return basicSandwich;
	}
}
