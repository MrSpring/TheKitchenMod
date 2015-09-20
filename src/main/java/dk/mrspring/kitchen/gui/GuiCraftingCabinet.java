package dk.mrspring.kitchen.gui;

import dk.mrspring.kitchen.gui.container.ContainerCraftingCabinet;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class GuiCraftingCabinet extends GuiCrafting
{
    public GuiCraftingCabinet(InventoryPlayer player, World world, int x, int y, int z)
    {
        super(player, world, x, y, z);
        this.inventorySlots = new ContainerCraftingCabinet(player, world, x, y, z);
    }
}
