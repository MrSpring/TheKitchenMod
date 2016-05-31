package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.model.ModelKitchenCabinet;
import dk.mrspring.kitchen.tileentity.TileEntityKitchenCabinet;

import static dk.mrspring.kitchen.ClientUtils.pop;
import static dk.mrspring.kitchen.ClientUtils.push;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class TileEntityKitchenCabinetRenderer extends TileEntityRenderer<TileEntityKitchenCabinet>
{
    ModelKitchenCabinet cabinet = new ModelKitchenCabinet();

    @Override
    protected void renderModel(TileEntityKitchenCabinet entity, float partial)
    {
        rotateBasedOnMetadata(entity.getBlockMetadata() / 2, 4);
        push();
        translateBlockModel();
        cabinet.simpleRender(partial, new ModelKitchenCabinet.Parameters(entity));
        pop();
    }
/*
    ModelKitchenCabinet model;
	ResourceLocation texture;

	public TileEntityKitchenCabinetRenderer()
	{
		this.model = new ModelKitchenCabinet();
		this.texture = new ResourceLocation(ModInfo.modid, "textures/models/kitchen_cabinet.png");
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
	{
		TileEntityKitchenCabinet tileEntity = (TileEntityKitchenCabinet) var1;

		GL11.glPushMatrix();

		GL11.glTranslatef(0.5F + (float) var2, 1.5F + (float) var4, 0.5F + (float) var6);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
		int metadata = tileEntity.getBlockMetadata();
		boolean renderCorner = false;
		float rotateAngle = 0F;

		switch (metadata)
		{
			case 0: break;
			case 1: renderCorner = true; break;
			case 2: rotateAngle = 90F; break;
			case 3: rotateAngle = 90F; renderCorner = true; break;
			case 4: rotateAngle = 180F; break;
			case 5: rotateAngle = 180F; renderCorner = true; break;
			case 6: rotateAngle = 270F; break;
			case 7: rotateAngle = 270F; renderCorner = true; break;
		}

		GL11.glPushMatrix();

		GL11.glRotatef(rotateAngle, 0F, 1F, 0F);
		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, renderCorner);

		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
*/
}