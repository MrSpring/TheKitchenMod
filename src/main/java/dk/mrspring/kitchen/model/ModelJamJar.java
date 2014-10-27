package dk.mrspring.kitchen.model;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class ModelJamJar extends ModelBase
{
	//fields
	ModelRenderer base;
	ModelRenderer side1;
	ModelRenderer side2;
	ModelRenderer side3;
	ModelRenderer side4;
	ModelRenderer lid;
	ModelRenderer top;
	ModelRenderer jam;

	public ModelJamJar()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 30, 0);
		base.addBox(0F, 0F, 0F, 6, 1, 6);
		base.setRotationPoint(-3F, 23F, -3F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 15, 0);
		side1.addBox(0F, 0F, 0F, 6, 7, 1);
		side1.setRotationPoint(-3F, 16F, 3F);
		side1.setTextureSize(64, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 0, 9);
		side2.addBox(0F, 0F, 0F, 1, 7, 6);
		side2.setRotationPoint(-4F, 16F, -3F);
		side2.setTextureSize(64, 32);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 15, 9);
		side3.addBox(0F, 0F, 0F, 1, 7, 6);
		side3.setRotationPoint(3F, 16F, -3F);
		side3.setTextureSize(64, 32);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
		side4 = new ModelRenderer(this, 0, 0);
		side4.addBox(0F, 0F, 0F, 6, 7, 1);
		side4.setRotationPoint(-3F, 16F, -4F);
		side4.setTextureSize(64, 32);
		side4.mirror = true;
		setRotation(side4, 0F, 0F, 0F);
		lid = new ModelRenderer(this, 30, 8);
		lid.addBox(0F, 0F, 0F, 7, 1, 7);
		lid.setRotationPoint(-3.5F, 14F, -3.5F);
		lid.setTextureSize(64, 32);
		lid.mirror = true;
		setRotation(lid, 0F, 0F, 0F);
		top = new ModelRenderer(this, 30, 0);
		top.addBox(0F, 0F, 0F, 6, 1, 6);
		top.setRotationPoint(-3F, 15F, -3F);
		top.setTextureSize(64, 32);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean isEmpty, int color, int jamHeight)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
		lid.render(f5);
		top.render(f5);
		if (!isEmpty && jamHeight != 0)
		{
			jam = new ModelRenderer(this, 0, 0);
			jam.addBox(-0F, 0F, 0F, 6, jamHeight, 6);
			jam.setRotationPoint(-3F, 23 - jamHeight, -3F);
			jam.setTextureSize(16, 16);
			jam.mirror = true;
			setRotation(jam, 0F, 0F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jam.png"));
			float red = ((color >> 16) & 0xFF);
			float green = ((color >> 8) & 0xFF);
			float blue = (color & 0xFF);
			GL11.glColor4f(red / 255, green / 255, blue / 255, 1);
			jam.render(f5);
		}
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