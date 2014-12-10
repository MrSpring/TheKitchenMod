package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.tileentity.TileEntityToaster;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class WailaToasterProvider implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        TileEntityToaster tileEntityToaster = (TileEntityToaster) iWailaDataAccessor.getTileEntity();

        if (iWailaConfigHandler.getConfig("show_toaster_contents"))
        {
            if (tileEntityToaster.getStack1() != null && !tileEntityToaster.isCooking())
                list.add(tileEntityToaster.getStack1().getDisplayName());
            if (tileEntityToaster.getStack2() != null && !tileEntityToaster.isCooking())
                list.add(tileEntityToaster.getStack2().getDisplayName());
        }
        if (iWailaConfigHandler.getConfig("show_toaster_status") && (tileEntityToaster.getStack1() != null || tileEntityToaster.getStack2() != null))
        {
            if (!tileEntityToaster.isCooking())
                if (tileEntityToaster.canStartCooking())
                    list.add(StatCollector.translateToLocal("waila.toaster_can_cook"));
                else
                    list.add(StatCollector.translateToLocal("waila.toaster_cant_cook"));
            else
                list.add(StatCollector.translateToLocal("waila.toaster_cooking") + "...");
        }

        return list;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return null;
    }
}
