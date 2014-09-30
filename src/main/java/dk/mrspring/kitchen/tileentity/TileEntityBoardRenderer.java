package dk.mrspring.kitchen.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.render.SandwichRender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

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

		SandwichRender.renderSandwich(layers);
		
		GL11.glPopMatrix();
	}
}
