package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.item.render.PlateRender;
import dk.mrspring.kitchen.model.ModelPlate;
import dk.mrspring.kitchen.tileentity.TileEntityPlate;

import static dk.mrspring.kitchen.ClientUtils.*;

public class TileEntityPlateRenderer extends TileEntityRenderer<TileEntityPlate>
{
    ModelPlate plate = new ModelPlate();

    @Override
    protected void renderModel(TileEntityPlate entity, float partial)
    {
        rotateBasedOnMetadata(entity, 8);
        translate(0F, -0.03125F, 0F);
        push();
        translateBlockModel();
        plate.simpleRender(partial);
        pop();

        translate(0.5F, 0.11, 0.5F);
        PlateRender.renderPlateContents(new PlateRender.Plate(entity.getItems()));
    }
}
