package dk.mrspring.kitchen.client.api.render.oven;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.ItemRenderRegistry;
import dk.mrspring.kitchen.client.api.render.oven.builtin.BasicItemRendererFactory;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class OvenItemRendererRegistry
        extends ItemRenderRegistry<OvenItemRenderer, NBTTagCompound, OvenItemRendererFactory>
{
    public void register()
    {
        register(new BasicItemRendererFactory());
    }
}
