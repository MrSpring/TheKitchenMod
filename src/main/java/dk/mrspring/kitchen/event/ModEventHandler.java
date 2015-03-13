package dk.mrspring.kitchen.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.SandwichableConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

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
            if (entry.showInformation() || ModConfig.getKitchenConfig().show_item_debug_info)
            {
                event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_msg"));
                if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                    event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_stats_msg"));
                else
                {
                    event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.stats.heal_amount") + ": §3" + String.valueOf(entry.getHealAmount()));
                    {
                        String line = StatCollector.translateToLocal("item.sandwichable.stats.is_bread") + ": ";
                        if (entry.isBread())
                            line += "§3" + StatCollector.translateToLocal("waila.true");
                        else line += "§c" + StatCollector.translateToLocal("waila.false");
                        event.toolTip.add(line);
                    }

                    if (ModConfig.getKitchenConfig().show_item_debug_info)
                    {
                        String line = StatCollector.translateToLocal("item.sandwichable.stats.show_info") + ": ";
                        if (entry.showInformation())
                            line += "§3" + StatCollector.translateToLocal("waila.true");
                        else line += "§c" + StatCollector.translateToLocal("waila.false");
                        event.toolTip.add(line);

                        line = StatCollector.translateToLocal("item.sandwichable.stats.drop_item")+": ";
                        if (entry.dropItem())
                            line += "§3" + StatCollector.translateToLocal("waila.true");
                        else line += "§c" + StatCollector.translateToLocal("waila.false");
                        event.toolTip.add(line);
                    }
                }
            }

        if (stack.getItem() == KitchenItems.pancake || stack.getItem() == KitchenItems.waffle || stack.getItem() == KitchenItems.ice_cream_cone)
            if (stack.hasTagCompound())
            {
                NBTTagList iceCreamList = stack.getTagCompound().getTagList("IceCream", 8);
                event.toolTip.add(StatCollector.translateToLocal("item.ice_cream.ice_cream_message") + ": " + iceCreamList.tagCount() + "/4");
                for (int i = 0; i < iceCreamList.tagCount(); i++)
                    event.toolTip.add(" " + StatCollector.translateToLocal("mix." + iceCreamList.getStringTagAt(i) + ".name"));
            }
    }

    @SubscribeEvent
    public void handleMobDrops(LivingDropsEvent event)
    {
        if (event.entityLiving != null)
            if (event.entityLiving instanceof EntityPig)
            {
                Random random = new Random();
                int chance = 30;
                ItemStack dropped = new ItemStack(KitchenItems.raw_ham, 0);
                for (int i = 0; i < event.lootingLevel + 1; i++)
                    if (random.nextInt(100) < chance)
                        dropped.stackSize++;

                if (dropped.stackSize > 0)
                {
                    EntityLivingBase entity = event.entityLiving;
                    EntityItem hamEntity = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, dropped);
                    hamEntity.delayBeforeCanPickup = 10;
                    event.drops.add(hamEntity);
                }
            }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        if (!player.getEntityData().getBoolean("HasGottenCookingBook"))
        {
            player.getEntityData().setBoolean("HasGottenCookingBook", true);
            player.inventory.addItemStackToInventory(new ItemStack(KitchenItems.cooking_book));
        }
    }
}
