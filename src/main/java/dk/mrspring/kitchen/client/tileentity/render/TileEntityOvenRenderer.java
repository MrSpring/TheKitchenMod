package dk.mrspring.kitchen.client.tileentity.render;

import dk.mrspring.kitchen.client.model.block.ModelOven;
import net.minecraft.tileentity.TileEntity;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
public class TileEntityOvenRenderer extends TileEntityRenderer
{
    ModelOven oven = new ModelOven();

    @Override
    protected void renderModel(TileEntity entity, float partial)
    {
        oven.simpleRender(partial);
    }
}
