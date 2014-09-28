package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.ItemSandwich;
import dk.mrspring.kitchen.item.ItemSandwichable;
import dk.mrspring.kitchen.model.ModelPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityPlateSpecialRenderer extends TileEntitySpecialRenderer
{
	ModelPlate modelPlate;
	ResourceLocation texture;
	private double yItemOffset = 0.0D;

	public TileEntityPlateSpecialRenderer()
	{
		this.modelPlate = new ModelPlate();
		this.texture = new ResourceLocation(ModInfo.modid + ":textures/models/plate.png");
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		GL11.glPushMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		// TODO Coffee Color - GL11.glColor4f(0.247F, 0.168F, 0.062F, 1.0F);
		int metadata = var1.getBlockMetadata();
		GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
		this.modelPlate.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F);

		GL11.glPopMatrix();

		GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);

		this.yItemOffset = 0;

		for(ItemStack itemStack : ((TileEntityPlate) var1).getItemsAsArray())
		{
			if (itemStack != null)
			{
				if (itemStack.getItem() instanceof ItemSandwich)
					this.renderSadwich(itemStack);
				else
				{ this.renderItem(itemStack, 0, this.yItemOffset + 1.4, -0.225F); this.yItemOffset -= 0.03; }
			}
		}

		GL11.glPopMatrix();
	}

	private void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset)
	{
		if (item != null)
		{
			GL11.glPushMatrix();

			GL11.glTranslated(xOffset, yOffset, zOffset);

			ItemStack toRender = item.copy();
			toRender.stackSize = 1;

			EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, toRender);
			itemEntity.hoverStart = 0.0F;
			RenderItem.renderInFrame = true;
			GL11.glRotatef(180, 0, 1, 1);
            GL11.glRotatef(180,0,1,0);
			RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;

			GL11.glPopMatrix();
		}
	}

	private void renderSadwich(ItemStack item)
	{
		this.yItemOffset = 0.0;

		NBTTagList layersList = item.stackTagCompound.getTagList("SandwichLayers", 10);

		if (layersList != null)
		{
			ItemStack[] items = new ItemStack[layersList.tagCount()];

			for (int i = 0; i < layersList.tagCount(); ++i)
			{
				NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
				items[i] = ItemStack.loadItemStackFromNBT(layerCompound);
			}

			GL11.glPushMatrix();

			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.375F, -0.225F);
			GL11.glScalef(1.2F, 1.2F, 1.2F);

			for (int i = 0; i < items.length; ++i)
			{
				boolean isTopOfStack = false;
				if (i + 1 < items.length)
					if (items[i + 1] != null)
						isTopOfStack = true;

				this.renderItemEntity(items[i], 0.0D, (i * 0.0311D + yItemOffset), 0.0D, isTopOfStack);
			}

			GL11.glPopMatrix();
		}
	}

	private void renderItemEntity(ItemStack item, double xOffset, double yOffset, double zOffset, boolean isTop)
	{
		GL11.glPushMatrix();

		GL11.glTranslated(xOffset, yOffset, zOffset);

		if (((ItemSandwichable) item.getItem()).hasCustomModel)
			if (isTop)
				{ ((ItemSandwichable) item.getItem()).getBottomModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) item.getItem()).modelBottomHeight * 0.033D); }
			else
				{ ((ItemSandwichable) item.getItem()).getTopModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) item.getItem()).modelTopHeight * 0.033D); }
		else
		{
			EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
			itemEntity.hoverStart = 0.0F;
			RenderItem.renderInFrame = true;
			GL11.glRotatef(180, 0, 1, 1);
			RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
		}

		GL11.glPopMatrix();
	}
}
