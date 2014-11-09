package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.block.container.BlockBoard;
import dk.mrspring.kitchen.block.container.BlockFryingPan;
import dk.mrspring.kitchen.block.container.BlockOven;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 05-10-2014 for TheKitchenMod.
 */
public class WailaDataProvider implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> strings, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return strings;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> strings, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return strings;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> strings, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler)
    {
        return strings;
    }

    public static void callbackRegister(IWailaRegistrar registrar)
    {
        final String mod = "The Kitchen Mod";

        registrar.addConfig(mod, "show_is_sandwich_ready", true);
        registrar.addConfig(mod, "show_oven_contents", true);
        registrar.addConfig(mod, "show_oven_status", true);
        registrar.addConfig(mod, "show_pan_status", true);

        registrar.registerBodyProvider(new WailaBoardDataProvider(), BlockBoard.class);
        registrar.registerBodyProvider(new WailaOvenProvider(), BlockOven.class);
        registrar.registerBodyProvider(new WailaPanProvider(), BlockFryingPan.class);
    }
}
