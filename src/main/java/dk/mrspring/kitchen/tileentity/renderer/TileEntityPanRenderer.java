package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.ingredient.IngredientRenderingRegistry;
import dk.mrspring.kitchen.model.ModelPan;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 24-10-2014 for TheKitchenMod.
 */
public class TileEntityPanRenderer extends TileEntityTimeableRenderer
{
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
        super.renderTileEntityAt(tileEntity, x, y, z, p_147500_8_);

        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        int xc = tileEntity.xCoord, yc = tileEntity.yCoord, zc = tileEntity.zCoord;
        if (tileEntity.getWorldObj().getBlock(xc, yc - 1, zc) == KitchenBlocks.oven)
        {
            float pixel = 0.0625F;
            int metadata = tileEntity.getWorldObj().getBlockMetadata(xc, yc - 1, zc);
            GL11.glRotatef(-metadata * (90), 0F, 1F, 0F);
            GL11.glTranslatef(4 * pixel, 0F, -(2 * pixel));
        }

        GL11.glPushMatrix();

        GL11.glRotatef(180F, 0, 0, 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureLocation);
        model.render(null, 0, 0, -0.1F, 0, 0, 0.0625F);

        GL11.glPopMatrix();

        IFryingPan fryingPan = (IFryingPan) tileEntity;
        IIngredient ingredient = fryingPan.getIngredient();
        if (ingredient != null)
        {
            IIngredientRenderingHandler handler = IngredientRenderingRegistry.getInstance().getHandlerFor(fryingPan, ingredient);
            handler.render(fryingPan, ingredient);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void transformTimer(TileEntity tileEntity)
    {
        int x = tileEntity.xCoord, y = tileEntity.yCoord, z = tileEntity.zCoord;
        World world = tileEntity.getWorldObj();
        int metadata = world.getBlockMetadata(x, y - 1, z);
        GL11.glRotatef(metadata * 90, 0, 1, 0);
        GL11.glTranslatef(-0.06F, 0.825F, -0.275F);
        float pixel = 0.0625F;

        if (tileEntity.getWorldObj().getBlock(x, y - 1, z) == KitchenBlocks.oven)
            GL11.glTranslatef(4 * pixel, 0F, -(2 * pixel));
        float scale = 0.5F;
        GL11.glScalef(scale, scale, scale);
    }
}
