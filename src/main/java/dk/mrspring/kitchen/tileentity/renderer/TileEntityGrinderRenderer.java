package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.block.ModelCraftingCabinet;
import dk.mrspring.kitchen.model.block.ModelGrinder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 16-11-2015 for TheKitchenMod.
 */
public class TileEntityGrinderRenderer extends TileEntitySpecialRenderer
{
    ModelGrinder model = new ModelGrinder();
    ResourceLocation texture = ModInfo.toResource("textures/models/grinder.png");

    @Override
    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float p_147500_8_)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.5F + (float) var2, 1.5F + (float) var4, 0.5F + (float) var6);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        bindTexture(this.texture);
        int metadata = var1.getBlockMetadata();

        GL11.glPushMatrix();

        GL11.glRotatef(metadata * 90F, 0F, 1F, 0F);
        model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
}
