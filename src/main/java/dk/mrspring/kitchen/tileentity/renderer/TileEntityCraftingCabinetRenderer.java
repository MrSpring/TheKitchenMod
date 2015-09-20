package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelCraftingCabinet;
import dk.mrspring.kitchen.tileentity.TileEntityKitchenCabinet;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class TileEntityCraftingCabinetRenderer extends TileEntitySpecialRenderer
{
    ModelCraftingCabinet model = new ModelCraftingCabinet();
    ResourceLocation texture = ModInfo.toResource("textures/models/crafting_cabinet.png");

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
