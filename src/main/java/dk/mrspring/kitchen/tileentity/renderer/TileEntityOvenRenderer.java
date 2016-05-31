package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.model.ModelOven;
import dk.mrspring.kitchen.tileentity.TileEntityOven;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.ClientUtils.*;

public class TileEntityOvenRenderer extends TileEntityRenderer<TileEntityOven>
{
    ModelOven oven = new ModelOven();

    @Override
    protected void renderModel(TileEntityOven entity, float partial)
    {
        rotateBasedOnMetadata(entity);

        push();
        translateBlockModel();
        oven.simpleRender(partial, new ModelOven.Parameters(entity));
        pop();

        ItemStack[] stacks = entity.getOvenItems();
        translate(0.5F, 0.4F, 0.5F);
        scale(0.75F);
        for (int i = 0; i < stacks.length; i++)
        {
            if (stacks[i] != null)
            {
                push();
                switch (i)
                {
                    case 0:
                        translate(0.2, 0, -0.2);
                        break;
                    case 1:
                        translate(0.2, 0, 0.2);
                        break;
                    case 2:
                        translate(-0.2, 0, 0.2);
                        break;
                    case 3:
                        translate(-0.2, 0, -0.2);
                        break;
                }
                rotate(90, 1, 0, 0);
                renderItemStack(stacks[i]);
                pop();
            }
        }
    }
}
