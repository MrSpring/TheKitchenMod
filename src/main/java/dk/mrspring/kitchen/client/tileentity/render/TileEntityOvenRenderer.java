package dk.mrspring.kitchen.client.tileentity.render;

import dk.mrspring.kitchen.client.model.block.ModelOven;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOven;
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
        rotateBasedOnMetadata(entity);
        TileEntityClientOven oven = (TileEntityClientOven) entity;
        ModelOven.Parameters parameters = new ModelOven.Parameters(oven);
        this.oven.simpleRender(partial, parameters);
    }
}
