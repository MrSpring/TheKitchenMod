package dk.mrspring.kitchen;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dk.mrspring.kitchen.config.SandwichableConfig;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * Created by MrSpring on 08-10-2014 for TheKitchenMod.
 */
public class SandwichableTooltipEvent
{
	@SubscribeEvent
	public void handleToolTip(ItemTooltipEvent event)
	{
		SandwichableConfig.SandwichableEntry entry = ModConfig.getSandwichConfig().findEntry(event.itemStack);
		if (entry != null)
			if (entry.showInformation())
				event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_msg"));
	}
}
