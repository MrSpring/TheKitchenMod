package dk.mrspring.kitchen.item.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderSandwich implements IItemRenderer
{
	protected ItemStack[] items;
	protected boolean readData = true;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{
			case EQUIPPED:
				return true;
			case EQUIPPED_FIRST_PERSON:
				return true;
			case ENTITY:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		switch (helper)
		{
			case ENTITY_BOBBING:
				return true;
			case ENTITY_ROTATION:
				return true;
			default:
				return false;
		}
	}


	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			case EQUIPPED:
			{
				GL11.glPushMatrix();

				GL11.glRotatef(-60, 1, 0, 0);
				GL11.glRotatef(20, 0, 0, 1);
				GL11.glTranslated(.4, -.1, 0);
				float f = 1.5F;
				GL11.glScalef(f, f, f);

				SandwichRender.renderSandwich(item, null);

				GL11.glPopMatrix();

				break;
			}
			case EQUIPPED_FIRST_PERSON:
			{
				GL11.glPushMatrix();

				GL11.glTranslatef(.4F, .3F, -.1F);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glRotatef(-10, 1, 0, 0);

				SandwichRender.renderSandwich(item, null);

				GL11.glPopMatrix();

				break;
			}
			case ENTITY:
			{
				GL11.glPushMatrix();

				GL11.glTranslatef(.00F, -0.15F, -.35F);
				float f = 2F;
				GL11.glScalef(f, f, f);


				SandwichRender.renderSandwich(item, null);

				GL11.glPopMatrix();

				break;
			}
		}

		/*this.yItemOffset = 0.0D;

		switch(type)
		{
		case EQUIPPED:
		{
            if (item.getTagCompound() != null)
            {
                NBTTagList layersList = item.getTagCompound().getTagList("SandwichLayers", 10);

                if (layersList != null)
                {

                    this.items = new ItemStack[layersList.tagCount()];

                    for (int i = 0; i < layersList.tagCount(); ++i)
                    {
                        NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                        items[i] = ItemStack.loadItemStackFromNBT(layerCompound);
                    }

                    GL11.glPushMatrix();

                    if (data[1] != null && data[1] instanceof EntityPlayer)
                    {
                        if (!((EntityPlayer) data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)))
                        {
                            GL11.glRotatef(180, -0.15F, 1.0F, -0.6F);
                            GL11.glTranslatef(-0.5F, 0.0F, -0.3F);
                            GL11.glScalef(1.4F, 1.4F, 1.4F);
                        } else
                        {
                            GL11.glRotatef(180, 0.5F, 0.15F, 0.0F);
                            GL11.glTranslatef(1.0F, -1.0F, 1.0F);
                            GL11.glScalef(0.6F, 0.6F, 0.6F);
                        }
                    } else
                    {
                        GL11.glRotatef(180, 0.5F, 1.0F, 0.0F);
                        GL11.glTranslatef(0.0F, 0.7F, -0.2F);
                    }

                    for (int i = 0; i < this.items.length; ++i)
                    {
                        this.renderItemEntity(this.items[i], 0.0D, (i * 0.031D + this.yItemOffset), 0.0D, i);
                    }

                    GL11.glPopMatrix();
                }
            }
		}
		case EQUIPPED_FIRST_PERSON:
		{
			if (((EntityPlayer) data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)))
			{
                if (item.getTagCompound() != null)
                {
                    NBTTagList layersList = item.stackTagCompound.getTagList("SandwichLayers", 10);

                    if (layersList != null)
                    {
                        this.items = new ItemStack[layersList.tagCount()];

                        for (int i = 0; i < layersList.tagCount(); ++i)
                        {
                            NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                            items[i] = ItemStack.loadItemStackFromNBT(layerCompound);
                        }

                        GL11.glPushMatrix();

                        GL11.glRotatef(180, 0.65F, 0.12F, 0.6F);
                        GL11.glTranslatef(-0.3F, -0.5F, 0.3F);
                        GL11.glScalef(0.8F, 0.8F, 0.8F);

                        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);

                        for (int i = 0; i < this.items.length; ++i)
                        {
                            this.renderItemEntity(this.items[i], 0.0D, (i * 0.0311D + yItemOffset), 0.0D, i);
                        }

                        GL11.glPopMatrix();
                    }
                }
			}
		}
		default: break;
		}*/
	}

	private void renderItemEntity(ItemStack item, double xOffset, double yOffset, double zOffset, int i)
	{
		GL11.glPushMatrix();

		GL11.glTranslated(xOffset, yOffset, zOffset);
			
			/*if (((ItemSandwichable) this.items[i].getItem()).hasCustomModel)
				if (i + 1 < this.items.length)
					if (this.items[i + 1] != null)
						{ ((ItemSandwichable) this.items[i].getItem()).getBottomModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.items[i].getItem()).modelBottomHeight * 0.033D); }
					else
						{ ((ItemSandwichable) this.items[i].getItem()).getTopModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.items[i].getItem()).modelTopHeight * 0.033D); }
				else 
					{ ((ItemSandwichable) this.items[i].getItem()).getTopModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.items[i].getItem()).modelTopHeight * 0.033D); }
			else
			{*/
		EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
		itemEntity.hoverStart = 0.0F;
		RenderItem.renderInFrame = true;
		GL11.glRotatef(180, 0, 1, 1);
		RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		//}

		GL11.glPopMatrix();
	}
}
