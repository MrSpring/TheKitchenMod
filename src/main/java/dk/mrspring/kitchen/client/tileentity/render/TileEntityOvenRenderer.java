package dk.mrspring.kitchen.client.tileentity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import dk.mrspring.kitchen.client.model.block.ModelOven;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOven;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class TileEntityOvenRenderer extends TileEntityRenderer<TileEntityClientOven>
{
    ModelOven oven = new ModelOven();

    @Override
    protected void renderModel(TileEntityClientOven oven, float partial)
    {
        rotateBasedOnMetadata(oven);
        ModelOven.Parameters parameters = new ModelOven.Parameters(oven);
        this.oven.simpleRender(partial, parameters);

        OvenItemRenderer[] renderers = oven.renderers;
        for (OvenItemRenderer renderer : renderers)
            if (renderer != null) renderer.render(oven);
    }
}
