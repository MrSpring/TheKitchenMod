package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.block.container.*;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Konrad on 05-10-2014 for TheKitchenMod.
 */
public class WailaCompatibility
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        final String mod = "The Kitchen Mod";

        registrar.addConfig(mod, "show_is_sandwich_ready", true);
        registrar.addConfig(mod, "show_oven_contents", true);
        registrar.addConfig(mod, "show_oven_status", true);
        registrar.addConfig(mod, "show_pan_status", true);
        registrar.addConfig(mod, "show_toaster_contents", true);
        registrar.addConfig(mod, "show_toaster_status", true);
        registrar.addConfig(mod, "show_waffle_iron_status", true);

        registrar.registerBodyProvider(new WailaBoardDataProvider(), BlockBoard.class);
        registrar.registerBodyProvider(new WailaOvenProvider(), BlockOven.class);
        registrar.registerBodyProvider(new WailaPanProvider(), BlockFryingPan.class);
        registrar.registerBodyProvider(new WailaToasterProvider(), BlockToaster.class);
        registrar.registerBodyProvider(new WailaWaffleIronProvider(), BlockWaffleIron.class);
        registrar.registerBodyProvider(new WailaMuffinTrayProvider(), BlockMuffinTray.class);
        registrar.registerStackProvider(new WailaMuffinTrayProvider(), BlockMuffinTray.class);
    }
}
