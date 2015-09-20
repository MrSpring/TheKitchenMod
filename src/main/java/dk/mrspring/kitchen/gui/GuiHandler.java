package dk.mrspring.kitchen.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import dk.mrspring.kitchen.gui.container.ContainerCraftingCabinet;
import dk.mrspring.kitchen.gui.screen.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class GuiHandler implements IGuiHandler
{
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
                return new GuiScreenBook();
            case 1:
                return new GuiCraftingCabinet(player.inventory, world, x, y, z);
            default:
                return null;
        }
    }
}
