package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.model.ModelPan;
import dk.mrspring.kitchen.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.tileentity.TileEntityPan;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created by MrSpring on 24-10-2014 for TheKitchenMod.
 */
public class TileEntityPanRenderer extends TileEntityRenderer<TileEntityPan>
{
    ModelPan pan = new ModelPan();

    @Override
    protected void renderModel(TileEntityPan entity, float partial)
    {
        if (entity.isOnOven())
        {
            float p = 0.0625F;
            rotateBasedOnMetadata(entity.getAngle(), 4);
            translate(4 * p, 0, -(2 * p));
        }
        push();
        translateBlockModel();
        pan.simpleRender(partial);
        pop();

        Ingredient ingredient = entity.getIngredient();
        if (ingredient != null)
            if (ingredient != Ingredient.getIngredient("empty"))
            {
                push();
                IIngredientRenderingHandler renderingHandler = ingredient.getRenderingHandler();
                if (renderingHandler.translateModel(entity.getCookTime(), ingredient))
                {
                    translate(0.5F, 0.075F, 0.5F);
                    scale(0.4F);
                    translate(-0.5F, 0.0F, -0.5F);
                    translateBlockModel();
                }
                renderingHandler.render(entity.getCookTime(), ingredient);
                pop();
            }
    }
}
