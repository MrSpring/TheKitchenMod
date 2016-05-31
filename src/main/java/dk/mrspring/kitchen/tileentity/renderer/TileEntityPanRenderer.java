package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.model.ModelPan;
import dk.mrspring.kitchen.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.tileentity.TileEntityPan;
import net.minecraft.client.model.ModelBase;
import org.lwjgl.opengl.GL11;

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

        Ingredient ingredient = entity.getIngredient();
        if (ingredient != null)
            if (ingredient != Ingredient.getIngredient("empty"))
            {
                IIngredientRenderingHandler renderingHandler = ingredient.getRenderingHandler();
                ModelBase ingredientModel = renderingHandler.getModel(entity.getCookTime(), ingredient);
                if (renderingHandler.useColorModifier(entity.getCookTime(), ingredient))
                {
                    float[] colors = renderingHandler.getColorModifier(entity.getCookTime(), ingredient);
                    GL11.glColor4f(colors[0] / 255, colors[1] / 255, colors[2] / 255, 1F);
                }
                if (renderingHandler.scaleOnPan(entity.getCookTime(), ingredient))
                {
                    GL11.glTranslatef(0.0F, 0.835F, 0.0F);
                    GL11.glScalef(0.4F, 0.4F, 0.4F);
                }
                ingredientModel.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            }

        pop();
    }

/*
    protected ModelPan model;
    protected ResourceLocation textureLocation;

    public TileEntityPanRenderer()
    {
        this.model = new ModelPan();
        this.textureLocation = new ResourceLocation(ModInfo.toTexture("textures/models/pan.png"));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        int xc = tileEntity.xCoord, yc = tileEntity.yCoord, zc = tileEntity.zCoord;
        boolean isOnOven = tileEntity.getWorldObj().getBlock(xc, yc - 1, zc) == KitchenBlocks.oven;

        if (isOnOven)
        {
            float pixel = 0.0625F;
            int metadata = tileEntity.getWorldObj().getBlockMetadata(xc, yc - 1, zc);
            GL11.glRotatef(-metadata * (90), 0F, 1F, 0F);
            GL11.glTranslatef(4 * pixel, 0F, -(2 * pixel));
        }

        GL11.glPushMatrix();

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(this.textureLocation);
        model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        TileEntityPan pan = (TileEntityPan) tileEntity;

        Ingredient ingredient = pan.getIngredient();
        if (ingredient != null)
            if (ingredient != Ingredient.getIngredient("empty"))
            {
                IIngredientRenderingHandler renderingHandler = ingredient.getRenderingHandler();
                ModelBase ingredientModel = renderingHandler.getModel(pan.getCookTime(), ingredient);
                if (renderingHandler.useColorModifier(pan.getCookTime(), ingredient))
                {
                    float[] colors = renderingHandler.getColorModifier(pan.getCookTime(), ingredient);
                    GL11.glColor4f(colors[0] / 255, colors[1] / 255, colors[2] / 255, 1F);
                }
                if (renderingHandler.scaleOnPan(pan.getCookTime(), ingredient))
                {
                    GL11.glTranslatef(0.0F, 0.835F, 0.0F);
                    GL11.glScalef(0.4F, 0.4F, 0.4F);
                }
                ingredientModel.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            }

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
*/
}
