package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.render.ItemMixingBowlRenderer;
import dk.mrspring.kitchen.model.ModelGrinder;
import dk.mrspring.kitchen.model.ModelWaffleIron;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 02-06-2015.
 */
public class TileEntityGrinderRenderer extends TileEntitySpecialRenderer
{
    ModelGrinder model = new ModelGrinder();
    ResourceLocation texture = new ResourceLocation(ModInfo.toTexture("textures/models/grinder.png"));

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float p_147500_8_)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        int metadata = var1.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
}
