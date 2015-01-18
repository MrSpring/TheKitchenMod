package dk.mrspring.kitchen;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.item.ItemBase;

/**
 * Created by MrSpring on 01-12-2014 for TheKitchenMod.
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class TheKitchenMod {
	public static Item knife;

	@SidedProxy(clientSide = "dk.mrspring.kitchen.ClientProxy", serverSide = "dk.mrspring.kitchen.CommonProxy")
	public static CommonProxy proxy;

	public CreativeTabs baseTab;

	@Mod.Instance
	public static TheKitchenMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		baseTab = new CreativeTabs("tabKitchen") {
			@Override
			public Item getTabIconItem() {
				return Items.acacia_door;
			}
		};
		knife = new ItemBase("knife", true);
		GameRegistry.registerItem(knife, "knife");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
}
