package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelJamJar;
import dk.mrspring.kitchen.tileentity.TileEntityJamJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class TileEntityJamJarRenderer extends TileEntitySpecialRenderer
{
	ModelJamJar model = new ModelJamJar();
	ResourceLocation texture = new ResourceLocation(ModInfo.modid, "textures/models/jar.png");

	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
	{
		TileEntityJamJar tileEntityJamJar = (TileEntityJamJar) var1;

		GL11.glPushMatrix();

		GL11.glTranslatef(0.5F + (float) var2, 1.8F + (float) var4, 0.5F + (float) var6);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float scale = 1.2F;
		GL11.glScalef(scale,scale,scale);

		Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);

		GL11.glPushMatrix();
		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, var1.getBlockMetadata() == 0, tileEntityJamJar.getJam().getColor(), tileEntityJamJar.getUsesLeft());

		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
}