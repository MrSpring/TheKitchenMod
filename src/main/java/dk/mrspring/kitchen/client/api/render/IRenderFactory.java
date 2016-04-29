package dk.mrspring.kitchen.client.api.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created on 29-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public interface IRenderFactory<T, P>
{
    /**
     * @param par Parameters to make the renderer from.
     * @return Return null if this factory does not make renderers matching this render type.
     */
    T make(P par);
}
