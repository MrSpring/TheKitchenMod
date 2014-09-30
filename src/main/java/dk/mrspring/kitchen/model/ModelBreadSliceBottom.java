package dk.mrspring.kitchen.model;

import org.lwjgl.opengl.GL11;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelBreadSliceBottom extends ModelBase
{
	ModelRenderer topMiddle;
	ModelRenderer bottom;
	ModelRenderer side0;
	ModelRenderer side1;
	ModelRenderer side2;
	ModelRenderer side3;
	
	public ModelBreadSliceBottom()	
	{
		textureWidth = 64;
		textureHeight = 32;
		
		topMiddle = new ModelRenderer(this, 0, 0);
		topMiddle.addBox(0F, 0F, 0F, 8, 1, 10);
		topMiddle.setRotationPoint(-4F, 22F, -5F);
		topMiddle.setTextureSize(64, 32);
		topMiddle.mirror = true;
		setRotation(topMiddle, 0F, 0F, 0F);
		bottom = new ModelRenderer(this, 0, 0);
		bottom.addBox(0F, 0F, 0F, 8, 1, 10);
		bottom.setRotationPoint(-4F, 23F, -5F);
		bottom.setTextureSize(64, 32);
		bottom.mirror = true;
		setRotation(bottom, 0F, 0F, 0F);
		side0 = new ModelRenderer(this, 0, 12);
		side0.addBox(0F, 0F, 0F, 8, 1, 1);
		side0.setRotationPoint(-4F, 22F, 5F);
		side0.setTextureSize(64, 32);
		side0.mirror = true;
		setRotation(side0, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 19, 12);
		side1.addBox(0F, 0F, 0F, 1, 1, 10);
		side1.setRotationPoint(-5F, 22F, -5F);
		side1.setTextureSize(64, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 0, 18);
		side2.addBox(0F, 0F, 0F, 1, 1, 10);
		side2.setRotationPoint(4F, 22F, -5F);
		side2.setTextureSize(64, 32);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 0, 15);
		side3.addBox(0F, 0F, 0F, 8, 1, 1);
		side3.setRotationPoint(-4F, 22F, -6F);
		side3.setTextureSize(64, 32);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid + ":textures/models/bread_slice_bottom.png"));
		
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(0.7F, 0.7F, 0.7F);
		GL11.glTranslatef(0.0F, -1.4F, 0.215F);
		
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		topMiddle.render(f5);
		bottom.render(f5);
		side0.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		
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
