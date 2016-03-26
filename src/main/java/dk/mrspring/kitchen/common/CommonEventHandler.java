package dk.mrspring.kitchen.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dk.mrspring.kitchen.common.block.BlockContainerBase;
import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public class CommonEventHandler
{
    @SubscribeEvent
    public void blockBreakEvent(BlockEvent.BreakEvent event)
    {
        Block block = event.block;
        if (block != null && block instanceof BlockContainerBase)
        {
            BlockContainerBase container = (BlockContainerBase) block;
            container.onBlockBrokenBy(event.world, event.x, event.y, event.z, event.getPlayer(), event.blockMetadata);
        }
    }
}
