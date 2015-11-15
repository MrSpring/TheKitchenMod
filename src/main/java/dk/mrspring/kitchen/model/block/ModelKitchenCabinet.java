package dk.mrspring.kitchen.model.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Konrad on 12-07-2014 for The Kitchen Mod.
 */
public class ModelKitchenCabinet extends ModelBase
{
	//fields
	ModelRenderer base;
	ModelRenderer top;
	ModelRenderer rightDoor;
	ModelRenderer leftDoor;
	ModelRenderer leftKnob;
	ModelRenderer rightKnob;
	ModelRenderer cornerPiece;

	public ModelKitchenCabinet()
	{
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this, 1, 0);
		base.addBox(0F, 0F, 0F, 16, 13, 14);
		base.setRotationPoint(-8F, 11F, -6F);
		base.setTextureSize(64, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		top = new ModelRenderer(this, 0, 28);
		top.addBox(0F, 0F, 0F, 16, 3, 16);
		top.setRotationPoint(-8F, 8F, -8F);
		top.setTextureSize(64, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		rightDoor = new ModelRenderer(this, 0, 0);
		rightDoor.addBox(-6F, 0F, -1F, 6, 11, 1);
		rightDoor.setRotationPoint(7F, 12F, -6F);
		rightDoor.setTextureSize(64, 64);
		rightDoor.mirror = true;
		setRotation(rightDoor, 0F, 0F, 0F);
		leftDoor = new ModelRenderer(this, 48, 0);
		leftDoor.addBox(0F, 0F, -1F, 6, 11, 1);
		leftDoor.setRotationPoint(-7F, 12F, -6F);
		leftDoor.setTextureSize(64, 64);
		leftDoor.mirror = true;
		setRotation(leftDoor, 0F, 0F, 0F);
		leftKnob = new ModelRenderer(this, 1, 29);
		leftKnob.addBox(3F, 0F, -2F, 2, 2, 1);
		leftKnob.setRotationPoint(-7F, 15F, -6F);
		leftKnob.setTextureSize(64, 64);
		leftKnob.mirror = true;
		setRotation(leftKnob, 0F, 0F, 0F);
		rightKnob = new ModelRenderer(this, 8, 29);
		rightKnob.addBox(-5F, 0F, -2F, 2, 2, 1);
		rightKnob.setRotationPoint(7F, 15F, -6F);
		rightKnob.setTextureSize(64, 64);
		rightKnob.mirror = true;
		setRotation(rightKnob, 0F, 0F, 0F);
		cornerPiece = new ModelRenderer(this, 15, 0);
		cornerPiece.addBox(0F, 0F, 0F, 14, 13, 2);
		cornerPiece.setRotationPoint(-6F, 11F, -8F);
		cornerPiece.setTextureSize(64, 64);
		cornerPiece.mirror = true;
		setRotation(cornerPiece, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean corner)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		top.render(f5);

		if (!corner)
		{
			rightDoor.render(f5);
			leftDoor.render(f5);
			leftKnob.render(f5);
			rightKnob.render(f5);
		} else
		{
			cornerPiece.render(f5);
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