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

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
	{
		GL11.glPushMatrix();

		TileEntityBoard tileEntity = (TileEntityBoard) var1;
		GL11.glTranslated(x, y, z);
        int metadata = tileEntity.getBlockMetadata();
        if (metadata == 0)
            GL11.glTranslated(.5, .11, .3);
        else
        {
            GL11.glRotatef(90,0,1,0);
            GL11.glTranslated(-.5, .11, .3);
        }

		List<ItemStack> layers = tileEntity.getLayers();
		SandwichRender.renderSandwich(layers);
		
		GL11.glPopMatrix();
	}
}
