package dk.mrspring.kitchen.model;

import org.lwjgl.opengl.GL11;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelBaconRaw extends ModelBase
{
	ModelRenderer bacon31;
	ModelRenderer bacon32;
	ModelRenderer bacon33;
	ModelRenderer bacon34;
	ModelRenderer bacon35;
	ModelRenderer bacon21;
	ModelRenderer bacon22;
	ModelRenderer bacon23;
	ModelRenderer bacon24;
	ModelRenderer bacon25;
	ModelRenderer bacon11;
	ModelRenderer bacon12;
	ModelRenderer bacon13;
	ModelRenderer bacon14;
	ModelRenderer bacon15;
	
	public ModelBaconRaw()
	{
		textureWidth = 64;
		textureHeight = 32;
		
		bacon31 = new ModelRenderer(this, 13, 21);
		bacon31.addBox(0F, 0F, 0F, 3, 1, 2);
		bacon31.setRotationPoint(2.466667F, 23F, -4F);
		bacon31.setTextureSize(64, 32);
		bacon31.mirror = true;
		setRotation(bacon31, 0F, 0F, 0F);
		bacon32 = new ModelRenderer(this, 13, 21);
		bacon32.addBox(0F, 0F, 0F, 3, 1, 2);
		bacon32.setRotationPoint(2.466667F, 23F, -6F);
		bacon32.setTextureSize(64, 32);
		bacon32.mirror = true;
		setRotation(bacon32, 0F, 0F, 0F);
		bacon33 = new ModelRenderer(this, 0, 18);
		bacon33.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon33.setRotationPoint(2.466667F, 23F, 1F);
		bacon33.setTextureSize(64, 32);
		bacon33.mirror = true;
		setRotation(bacon33, 0F, 0F, 0F);
		bacon34 = new ModelRenderer(this, 0, 18);
		bacon34.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon34.setRotationPoint(2.466667F, 23F, 4F);
		bacon34.setTextureSize(64, 32);
		bacon34.mirror = true;
		setRotation(bacon34, 0F, 0F, 0F);
		bacon35 = new ModelRenderer(this, 0, 18);
		bacon35.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon35.setRotationPoint(2.466667F, 23F, -2F);
		bacon35.setTextureSize(64, 32);
		bacon35.mirror = true;
		setRotation(bacon35, 0F, 0F, 0F);
		bacon21 = new ModelRenderer(this, 0, 23);
		bacon21.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon21.setRotationPoint(-1.533333F, 23F, 4F);
		bacon21.setTextureSize(64, 32);
		bacon21.mirror = true;
		setRotation(bacon21, 0F, 0F, 0F);
		bacon22 = new ModelRenderer(this, 0, 23);
		bacon22.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon22.setRotationPoint(-1.533333F, 23F, 1F);
		bacon22.setTextureSize(64, 32);
		bacon22.mirror = true;
		setRotation(bacon22, 0F, 0F, 0F);
		bacon23 = new ModelRenderer(this, 13, 25);
		bacon23.addBox(0F, 0F, 0F, 3, 1, 2);
		bacon23.setRotationPoint(-1.533333F, 23F, -1F);
		bacon23.setTextureSize(64, 32);
		bacon23.mirror = true;
		setRotation(bacon23, 0F, 0F, 0F);
		bacon24 = new ModelRenderer(this, 0, 23);
		bacon24.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon24.setRotationPoint(-1.533333F, 23F, -4F);
		bacon24.setTextureSize(64, 32);
		bacon24.mirror = true;
		setRotation(bacon24, 0F, 0F, 0F);
		bacon25 = new ModelRenderer(this, 0, 23);
		bacon25.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon25.setRotationPoint(-1.533333F, 23F, -7F);
		bacon25.setTextureSize(64, 32);
		bacon25.mirror = true;
		setRotation(bacon25, 0F, 0F, 0F);
		bacon11 = new ModelRenderer(this, 13, 29);
		bacon11.addBox(0F, 0F, 0F, 3, 1, 2);
		bacon11.setRotationPoint(-5.533333F, 23F, -7F);
		bacon11.setTextureSize(64, 32);
		bacon11.mirror = true;
		setRotation(bacon11, 0F, 0F, 0F);
		bacon12 = new ModelRenderer(this, 0, 28);
		bacon12.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon12.setRotationPoint(-5.533333F, 23F, -5F);
		bacon12.setTextureSize(64, 32);
		bacon12.mirror = true;
		setRotation(bacon12, 0F, 0F, 0F);
		bacon13 = new ModelRenderer(this, 0, 28);
		bacon13.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon13.setRotationPoint(-5.533333F, 23F, -2F);
		bacon13.setTextureSize(64, 32);
		bacon13.mirror = true;
		setRotation(bacon13, 0F, 0F, 0F);
		bacon14 = new ModelRenderer(this, 24, 27);
		bacon14.addBox(0F, 0F, 0F, 3, 1, 4);
		bacon14.setRotationPoint(-5.533333F, 23F, 1F);
		bacon14.setTextureSize(64, 32);
		bacon14.mirror = true;
		setRotation(bacon14, 0F, 0F, 0F);
		bacon15 = new ModelRenderer(this, 0, 28);
		bacon15.addBox(0F, 0F, 0F, 3, 1, 3);
		bacon15.setRotationPoint(-5.533333F, 23F, 5F);
		bacon15.setTextureSize(64, 32);
		bacon15.mirror = true;
		setRotation(bacon15, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid + ":textures/models/raw_bacon.png"));
		
//		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//		GL11.glScalef(0.7F, 0.7F, 0.7F);
//		GL11.glTranslatef(0.0F, -1.475F, 0.215F);
		
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bacon31.render(f5);
		bacon32.render(f5);
		bacon33.render(f5);
		bacon34.render(f5);
		bacon35.render(f5);
		bacon21.render(f5);
		bacon22.render(f5);
		bacon23.render(f5);
		bacon24.render(f5);
		bacon25.render(f5);
		bacon11.render(f5);
		bacon12.render(f5);
		bacon13.render(f5);
		bacon14.render(f5);
		bacon15.render(f5);
		
		GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
