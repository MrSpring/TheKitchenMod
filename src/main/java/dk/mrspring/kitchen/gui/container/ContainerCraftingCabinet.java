package dk.mrspring.kitchen.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.world.World;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class ContainerCraftingCabinet extends ContainerWorkbench
{
    int x, y, z;

    public ContainerCraftingCabinet(InventoryPlayer p_i1808_1_, World p_i1808_2_, int x, int y, int z)
    {
        super(p_i1808_1_, p_i1808_2_, x, y, z);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return player.getDistanceSq((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }
}
