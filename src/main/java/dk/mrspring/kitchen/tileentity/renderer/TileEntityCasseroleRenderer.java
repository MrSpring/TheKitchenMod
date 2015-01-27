package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.model.ModelCasserole;
import dk.mrspring.kitchen.tileentity.TileEntityCasserole;
import dk.mrspring.kitchen.tileentity.casserole.Casserole;
import dk.mrspring.kitchen.tileentity.casserole.Layer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Konrad on 27-01-2015.
 */
public class TileEntityCasseroleRenderer extends TileEntitySpecialRenderer
{
    static ModelBase modelBase = new ModelCasserole();
    static ResourceLocation texture = new ResourceLocation("kitchen", "textures/models/casserole.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(180, 0, 0, 1);
        renderCasserole(((TileEntityCasserole) tileEntity).getCasserole());
        GL11.glPopMatrix();
    }

    public static void renderCasserole(Casserole casserole)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        modelBase.render(null, 0, 0, -0.1F, 0, 0, 0.0625F);
        List<Layer> layerList = casserole.getLayers();
        GL11.glPushMatrix();
        for (Layer layer : layerList)
        {
            GL11.glTranslatef(0, -0.1F, 0);
            layer.render(Casserole.CasseroleState.RAW);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
