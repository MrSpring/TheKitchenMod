package dk.mrspring.kitchen;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.item.*;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class KitchenItems
{
	// All the Item variables
	public static final Item knife = new ItemKnife().setMaxStackSize(1);
	public static final Item mortar_and_pestle = new ItemMandP().setMaxStackSize(1);
	public static final Item mortar = new ItemBase("mortar", true);
	public static final Item pestle = new ItemBase("pestle", true);
	public static final ItemSandwichable raw_bacon = new ItemSandwichable("bacon_raw", true, 1).setCustomModel(new ModelBaconRaw(), new ModelBaconRaw(), 1, 1);
	public static final ItemSandwichBread bread_slice = (ItemSandwichBread) new ItemSandwichBread("bread_slice", true).setCustomModel(new ModelBreadSliceTop(), new ModelBreadSliceBottom(), 3, 2);
	// The basic sandwich ItemStack. It's used as the icon fot the creative tab
	public static ItemStack basic_sandwich = getSandwichItemStackWithNBTTags(new ItemStack[] { new ItemStack(bread_slice, 1, 0), new ItemStack(raw_bacon, 1, 0), new ItemStack(bread_slice, 1, 0) });
	public static final Item tomato = new ItemTomato();
	public static final Item lettuce = new ItemLettuce();
	public static final ItemSandwichable tomato_slice = new ItemSandwichable("tomato_slice", true, 1);
	public static final ItemSandwichable lettuce_leaf = new ItemSandwichable("lettuce_leaf", true, 2);
	public static final ItemSandwichable bacon = new ItemSandwichable("bacon_cooked", true, 4).setCustomModel(new ModelBaconCooked(), new ModelBaconCooked(), 2, 2);
	public static final ItemSandwichable potato_slice = new ItemSandwichable("potato_slice", true, 2);
	public static final ItemSandwichable carrot_slice = new ItemSandwichable("carrot_slice", true, 2);
	public static final Item flour = new ItemBase("flour", true);
	public static final ItemSandwichBread toast = new ItemSandwichBread("toast", true);
	public static final ItemSandwichable raw_roast_beef = new ItemSandwichable("raw_roast_beef", ModInfo.modid + ":beef_slice", true, 1);
	public static final ItemSandwichable roast_beef = new ItemSandwichable("roast_beef", true, 4);
	public static final ItemSandwichable raw_chicken_fillet = new ItemSandwichable("chicken_fillet_raw", true, 1);
	public static final ItemSandwichable chicken_fillet = new ItemSandwichable("chicken_fillet_cooked", true, 4);
	public static final Item chicken_leg = new ItemFood(4, true).setUnlocalizedName("chicken_leg").setTextureName(ModInfo.modid + ":chicken_leg").setCreativeTab(Kitchen.instance.tab);
	public static final Item cheese = new ItemFood(3, false).setUnlocalizedName("cheese").setTextureName(ModInfo.modid + ":cheese").setCreativeTab(Kitchen.instance.tab);
	public static final ItemSandwichable cheese_slice = new ItemSandwichable("cheese_slice", true, 3);
    public static final Item burnt_meat = new ItemFood(1, false).setUnlocalizedName("burnt_meat").setTextureName(ModInfo.modid + ":burnt_meat").setCreativeTab(Kitchen.instance.tab);

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
