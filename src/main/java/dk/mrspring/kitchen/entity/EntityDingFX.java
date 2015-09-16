package dk.mrspring.kitchen.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 27-07-2015.
 */
public class EntityDingFX extends EntityFX
{
    double rotY = 0;
    int timeAlive = 0;

    public EntityDingFX(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        this.particleMaxAge = 40;
        this.motionY = 0.1;
    }

    public void setRotation(double y)
    {
        this.rotY = y;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.motionY *= 0.9D;
        this.timeAlive++;
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTick, float x, float y, float z, float p_70539_6_, float p_70539_7_)
    {
        float renderX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTick - interpPosX);
        float renderY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTick - interpPosY);
        float renderZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTick - interpPosZ);
        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
        GL11.glPushMatrix();
        GL11.glTranslatef(renderX, renderY, renderZ);
        GL11.glRotated(-Minecraft.getMinecraft().renderViewEntity.rotationYaw, 0F, 1F, 0F);
        float s = 0.0625F;
        GL11.glScalef(-s, -s, s);
        float size = timeAlive >= 20 ?
                Math.max(0, 5 - Math.max(0F, ((float) timeAlive + partialTick) - 35F)) :
                Math.min(partialTick + (float) this.timeAlive + partialTick, 5F);
        size /= 5;
        GL11.glScalef(size, size, size);
        int bright = 0xF0;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        renderer.drawString("DING", -(renderer.getStringWidth("DING") / 2), -9, 0xFFFFFF, true);
        GL11.glPopMatrix();
    }
}
