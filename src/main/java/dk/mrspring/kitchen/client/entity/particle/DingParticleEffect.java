package dk.mrspring.kitchen.client.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.common.entity.particle.IParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static dk.mrspring.kitchen.client.util.ClientUtils.*;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class DingParticleEffect implements IParticleEffect
{
    @Override
    public EntityFX[] makeEffect(World world, double x, double y, double z, IParticleHandler handler)
    {
        return new EntityFX[]{
                new DingFX(world, x, y, z)
        };
    }

    private class DingFX extends EntityFX
    {
        int timeAlive = 0;

        DingFX(World world, double x, double y, double z)
        {
            super(world, x, y, z);
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
            push();
            translate(renderX, renderY, renderZ);
            rotate(-Minecraft.getMinecraft().renderViewEntity.rotationYaw, 0F, 1F, 0F);
            float s = 0.02F;
            scale(-s, -s, s);
            float size = timeAlive >= 20 ?
                    Math.max(0, 5 - Math.max(0F, ((float) timeAlive + partialTick) - 35F)) :
                    Math.min(partialTick + (float) this.timeAlive + partialTick, 5F);
            size /= 5;
            scale(size);
            int bright = 0xF0;
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
            renderer.drawString("DING", -(renderer.getStringWidth("DING") / 2), -9, 0xFFFFFF, true);
            GL11.glPopMatrix();
            bind(PARTICLES);
        }
    }
}
