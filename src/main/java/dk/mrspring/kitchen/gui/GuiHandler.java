package dk.mrspring.kitchen.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import dk.mrspring.kitchen.api_impl.client.book.CookingBookRegistry;
import dk.mrspring.kitchen.api_impl.client.book.ManualRegistry;
import dk.mrspring.kitchen.gui.container.ContainerCraftingCabinet;
import dk.mrspring.kitchen.gui.screen.book.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import static dk.mrspring.kitchen.ModInfo.modid;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class GuiHandler implements IGuiHandler
{
    public static final int WAFFLE_IRON_MANUAL_ID = 2;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 1:
                return new ContainerCraftingCabinet(player.inventory, world, x, y, z);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0:
                return new GuiScreenBook(CookingBookRegistry.getInstance().getRegisteredHandlers());
            case 1:
                return new GuiCraftingCabinet(player.inventory, world, x, y, z);
            case WAFFLE_IRON_MANUAL_ID:
                return new GuiScreenBook(ManualRegistry.getInstance().WAFFLE_IRON.getRegisteredHandlers(),
                        new ResourceLocation(modid, "textures/gui/manual_left.png"),
                        new ResourceLocation(modid, "textures/gui/manual_right.png"));
            default:
                return null;
        }
    }
}
