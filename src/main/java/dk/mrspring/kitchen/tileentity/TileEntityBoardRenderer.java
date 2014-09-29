package dk.mrspring.kitchen.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.ItemSandwichable;

import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityBoardRenderer extends TileEntitySpecialRenderer
{
	private List<ItemStack> layers;
	private double yItemOffset = 0.0D;
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
	{
		this.yItemOffset = 0.0D;
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		
		TileEntityBoard tileEntity = (TileEntityBoard) var1;
		
		this.layers = tileEntity.getLayers();
		
		for (int i = 0; i < this.layers.size(); ++i)
		{
			if (this.layers.get(i) != null)
				renderItem(this.layers.get(i), 0.5D, 0.145D + (i * 0.033F + this.yItemOffset), 0.33D, i);
		}
		
		GL11.glPopMatrix();
	}
	
	private void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset, int i)
	{
		GL11.glPushMatrix();
			
			GL11.glTranslated(xOffset, yOffset, zOffset);
			
			/*if (((ItemSandwichable) this.layers.get(i).getItem()).hasCustomModel)
				if (i + 1 < this.layers.size())
					if (this.layers.get(i + 1) != null)
						{ ((ItemSandwichable) this.layers.get(i).getItem()).getBottomModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.layers[i].getItem()).modelBottomHeight * 0.03D); }
					else
						{ ((ItemSandwichable) this.layers.get(i).getItem()).getTopModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.layers[i].getItem()).modelTopHeight * 0.03D); }
				else
				{ ((ItemSandwichable) this.layers[i].getItem()).getTopModel().render(Minecraft.getMinecraft().renderViewEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F); this.yItemOffset += (((ItemSandwichable) this.layers[i].getItem()).modelTopHeight * 0.03D); }
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
