package dk.mrspring.kitchen.gui;

import cpw.mods.fml.common.network.IGuiHandler;
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
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new GuiScreenBook();
    }
}
