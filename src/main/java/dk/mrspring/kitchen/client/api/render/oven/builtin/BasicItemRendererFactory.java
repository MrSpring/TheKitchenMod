package dk.mrspring.kitchen.client.api.render.oven.builtin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRendererFactory;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOven;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 29-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class BasicItemRendererFactory extends OvenItemRendererFactory
{
    @Override
    public OvenItemRenderer make(NBTTagCompound compound)
    {
        return null;
    }

    public static class BasicItemRenderer extends OvenItemRenderer
    {
        ItemStack rendering;
        float xOffset = 0F, yOffset = 0F, zOffset = 0F;
        float xRotation = 0F, yRotation = 0F, zRotation = 0F;
        float xScale = 1F, yScale = 1F, zScale = 1F;

        @Override
        public void render(TileEntityClientOven oven)
        {
            System.out.println("Render");
        }
    }
}
