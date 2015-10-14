package dk.mrspring.kitchen.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.sandwichable.ISandwichable;
import dk.mrspring.kitchen.api_impl.common.registry.SandwichableRegistry;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
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
        int key = ModConfig.getKitchenConfig().show_stats_key;
        boolean isModifierKeyDown = Keyboard.isKeyDown(key);

//        SandwichableConfig.SandwichableEntry entry = ModConfig.getSandwichConfig().findEntry(stack);
        ISandwichable entry = SandwichableRegistry.getInstance().getSandwichableForItem(stack);
        if (entry != null)
            if (entry.getShowInformation() || ModConfig.getKitchenConfig().show_item_debug_info)
            {
                event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_msg"));
                if (!isModifierKeyDown)
                    event.toolTip.add(StatCollector.translateToLocalFormatted("item.sandwichable.sandwichable_stats_msg", GameSettings.getKeyDisplayString(key)));
                else
                {
                    event.toolTip.add(StatCollector.translateToLocal("item.sandwichable.stats.heal_amount") + ": §3" + String.valueOf(entry.getHealAmount()));
                    {
                        String line = StatCollector.translateToLocal("item.sandwichable.stats.is_bread") + ": ";
                        if (entry.getIsBread())
                            line += "§3" + StatCollector.translateToLocal("waila.true");
                        else line += "§c" + StatCollector.translateToLocal("waila.false");
                        event.toolTip.add(line);
                    }

                    if (ModConfig.getKitchenConfig().show_item_debug_info)
                    {
                        String line = StatCollector.translateToLocal("item.sandwichable.stats.show_info") + ": ";
                        if (entry.getShowInformation())
                            line += "§3" + StatCollector.translateToLocal("waila.true");
                        else line += "§c" + StatCollector.translateToLocal("waila.false");
                        event.toolTip.add(line);

                        line = StatCollector.translateToLocal("item.sandwichable.stats.drop_item") + ": ";
                        if (entry.getDropItem())
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

        if (KnifeRecipes.instance().hasOutput(stack))
        {
            ItemStack output = KnifeRecipes.instance().getOutputFor(stack);
            String line;
            if (isModifierKeyDown)
                line = StatCollector.translateToLocal("item.slicable.long") + ": §3" + output.getDisplayName();// Slice on a Cutting Board to get: ITEM
            else
                line = StatCollector.translateToLocal("item.slicable.short") + ": §3" + output.getDisplayName(); // Slice to get: ITEM
            event.toolTip.add(line);
        }

        if (ModConfig.getKitchenConfig().show_item_name)
        {
            int[] keys = ModConfig.getKitchenConfig().show_item_name_key_combo;
            for (int i : keys)
                if (!Keyboard.isKeyDown(i))
                    return;
            GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            String name = identifier.toString();
            event.toolTip.add(name);
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

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        int x = event.x, y = event.y, z = event.z;
        Block block = event.block;
        if (block instanceof BlockContainerBase)
        {
            BlockContainerBase container = (BlockContainerBase) block;
            container.onBlockBroken(event.getPlayer(), event.world, x, y, z);
        }
    }
}
