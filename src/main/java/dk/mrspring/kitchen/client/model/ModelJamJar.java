package dk.mrspring.kitchen.client.model;

import dk.mrspring.kitchen.client.util.ClientUtils;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class ModelJamJar extends ModelBase
{
    public ModelJamJar()
    {
        super(ClientUtils.modelTexture("jar"), 64, 32);

        this.addBox(30, 0, -3F, 23F, -3F, 6, 1, 6);
        this.addBox(15, 0, -3F, 16F, 3F, 6, 7, 1);
        this.addBox(0, 9, -4F, 16F, -3F, 1, 7, 6);
        this.addBox(15, 9, 3F, 16F, -3F, 1, 7, 6);
        this.addBox(0, 0, -3F, 16F, -4F, 6, 7, 1);
        this.addBox(30, 8, -3.5F, 14F, -3.5F, 7, 1, 7);
        this.addBox(30, 0, -3F, 15F, -3F, 6, 1, 6);
    }
    /*//fields
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

	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int color, int metadata)
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
		if (metadata > 0)
		{
			jam = new ModelRenderer(this, 0, 0);
			jam.addBox(-0F, 0F, 0F, 6, metadata, 6);
			jam.setRotationPoint(-3F, 23 - metadata, -3F);
			jam.setTextureSize(16, 16);
			jam.mirror = true;
			setRotation(jam, 0F, 0F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jam.png"));
            float[] rgb= ColorHandler.getIntAsRGB(color);
            GL11.glColor4f(rgb[0] / 255, rgb[1] / 255, rgb[2] / 255, 1);
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
	}*/

}