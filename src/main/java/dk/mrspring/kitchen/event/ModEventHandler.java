package dk.mrspring.kitchen.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.SandwichableConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * Created by MrSpring on 08-10-2014 for TheKitchenMod.
 */
public class ModEventHandler
{
    @SubscribeEvent
    public void handleToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.itemStack;

        SandwichableConfig.SandwichableEntry entry = ModConfig.getSandwichConfig().findEntry(stack);
        if (entry != null)
            if (entry.showInformation())
                event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_msg"));

        if (stack.getItem() == KitchenItems.pancake || stack.getItem() == KitchenItems.waffle)
            if (stack.hasTagCompound())
            {
                NBTTagList iceCreamList = stack.getTagCompound().getTagList("IceCream", 8);
                event.toolTip.add(StatCollector.translateToLocal("item.ice_cream.ice_cream_message") + ": " + iceCreamList.tagCount() + "/4");
                for (int i = 0; i < iceCreamList.tagCount(); i++)
                    event.toolTip.add(" " + StatCollector.translateToLocal("mix." + iceCreamList.getStringTagAt(i) + ".name"));
            }
    }
}
