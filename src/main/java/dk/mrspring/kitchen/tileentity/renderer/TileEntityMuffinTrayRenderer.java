package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelMuffinTray;
import dk.mrspring.kitchen.tileentity.TileEntityMuffinTray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created on 13-11-2015 for TheKitchenMod.
 */
public class TileEntityMuffinTrayRenderer extends TileEntitySpecialRenderer
{
    ModelMuffinTray modelPlate;
    ResourceLocation texture;

    public TileEntityMuffinTrayRenderer()
    {
        this.modelPlate = new ModelMuffinTray();
        this.texture = new ResourceLocation(ModInfo.modid + ":textures/models/muffin_tray.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0F, 0F, 1F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        int metadata = var1.getBlockMetadata();
        GL11.glTranslatef(0, (1F / 16F) * 4F, 0F);
        GL11.glRotatef(metadata * (90F), 0F, 1F, 0F);
        TileEntityMuffinTray tray = (TileEntityMuffinTray) var1;
        boolean[] filled = new boolean[tray.getMuffinCount()];
        for (int i = 0; i < filled.length; i++) filled[i] = tray.getInSlot(i) != null;
        this.modelPlate.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, filled);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
}
