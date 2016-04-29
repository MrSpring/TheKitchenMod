package dk.mrspring.kitchen.client.api.render;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created on 29-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderRegistry<T, P, F extends IRenderFactory<T, P>>
{
    List<F> factories = Lists.newArrayList();
    T fallbackRenderer = null;

    public T getFrom(P par)
    {
        for (F factory : factories)
        {
            T renderer = factory.make(par);
            if (renderer != null) return renderer;
        }
        return getFallbackRenderer();
    }

    public T getFallbackRenderer()
    {
        return fallbackRenderer;
    }

    public void register(F factory)
    {
        factories.add(factory);
    }
}
