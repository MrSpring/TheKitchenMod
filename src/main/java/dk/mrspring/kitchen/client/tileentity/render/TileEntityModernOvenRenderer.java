package dk.mrspring.kitchen.client.tileentity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import dk.mrspring.kitchen.client.model.block.ModelModernOven;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOpenableOven;

import static dk.mrspring.kitchen.client.util.ClientUtils.*;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class TileEntityModernOvenRenderer extends TileEntityRenderer<TileEntityClientOpenableOven>
{
    ModelModernOven oven = new ModelModernOven();

    @Override
    protected void renderModel(TileEntityClientOpenableOven oven, float partial)
    {
        rotateBasedOnMetadata(oven);

        push();
        translateBlockModel();
        ModelModernOven.Parameters parameters = new ModelModernOven.Parameters(oven);
        this.oven.simpleRender(partial, parameters);
        pop();

        push();
        OvenItemRenderer[] renderers = oven.renderers;
        for (OvenItemRenderer renderer : renderers)
            if (renderer != null)
            {
                push();
                renderer.render(oven);
                pop();
            }
        pop();
    }
}
