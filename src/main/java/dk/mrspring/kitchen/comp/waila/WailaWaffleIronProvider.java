package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class WailaWaffleIronProvider implements IWailaDataProvider
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
        if (iWailaConfigHandler.getConfig("show_waffle_iron_status"))
        {
            TileEntityWaffleIron tileEntityWaffleIron = (TileEntityWaffleIron) iWailaDataAccessor.getTileEntity();

            if (tileEntityWaffleIron.getCookTime() < 400)
            {
                if (tileEntityWaffleIron.hasDough && tileEntityWaffleIron.isOpen())
                    list.add(StatCollector.translateToLocal("waila.waffle_iron_ready"));
                if (tileEntityWaffleIron.hasDough && !tileEntityWaffleIron.isOpen())
                    list.add(StatCollector.translateToLocal("waila.waffle_iron_cooking"));
            } else if (tileEntityWaffleIron.hasDough)
                list.add(StatCollector.translateToLocal("waila.waffle_iron_done") + "!");
        }
        return list;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return null;
    }
}
