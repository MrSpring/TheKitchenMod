package dk.mrspring.kitchen.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 27-07-2015.
 */
public class EntityDingFX extends EntityFX
{
    double rotY = 0;

    public EntityDingFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_)
    {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
//        this.particleIcon = Minecraft.getMinecraft().text
        this.particleMaxAge = 10;
        this.motionY = 0.1;

    }

    public void setRotation(double y)
    {
        this.rotY = y;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float x, float y, float z, float p_70539_6_, float p_70539_7_)
    {
        float renderX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
        float renderY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
        float renderZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);
//        super.renderParticle(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
        GL11.glPushMatrix();
        GL11.glTranslatef(renderX, renderY, renderZ);
        GL11.glRotated(this.rotY,0,1,0);
        float s = 0.0625F;
        GL11.glScalef(-s, -s, s);
//        GL11.glTranslated(this.posX-x, this.posY-y, this.posZ-z);
//        GL11.glTranslatef(0, 0, z);
//        System.out.println(interpPosX+", "+interpPosY+", "+interpPosZ);
//        GL11.glTranslated(interpPosX,interpPosY,interpPosZ);
//        GL11.
        FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
        GL11.glColor4f(1,1,1,1);
        renderer.drawString("DING", 0, 0, 0xFFFFFF, true);
        GL11.glPopMatrix();
//        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft:textures/font/ascii.png"));

//        tessellator.addVertexWithUV(renderX - 0.5, renderY - 0.5, renderZ, 0, 0);
//        tessellator.addVertexWithUV(renderX + 0.5, renderY - 0.5, renderZ, 1, 0);
//        tessellator.addVertexWithUV(renderX + 0.5, renderY + 0.5, renderZ, 1, 1);
//        tessellator.addVertexWithUV(renderX - 0.5, renderY + 0.5, renderZ, 0, 1);

//        tessellator.addVertexWithUV(x - 0.5, y - 0.5, z, 0, 0);
//        tessellator.addVertexWithUV(x + 0.5, y - 0.5, z, 1, 0);
//        tessellator.addVertexWithUV(x + 0.5, y + 0.5, z, 1, 1);
//        tessellator.addVertexWithUV(x - 0.5, y + 0.5, z, 0, 1);
//        super.renderParticle(tessellator, p_70539_2_, x, y, z, p_70539_6_, p_70539_7_);
    }
}
