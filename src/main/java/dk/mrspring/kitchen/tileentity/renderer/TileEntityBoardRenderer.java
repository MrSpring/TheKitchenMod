package dk.mrspring.kitchen.tileentity.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.board.BoardRenderingRegistry;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityBoardRenderer extends TileEntitySpecialRenderer
{
    public static void renderLayers(List<ItemStack> layers, NBTTagCompound specialTag)
    {
        double yOffset = 0;
        for (int i = 0; i < layers.size(); i++)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(0, yOffset, 0);
            ItemStack layer = layers.get(i);
            IBoardRenderingHandler renderingHandler = BoardRenderingRegistry.getInstance().getHandlerFor(layers, i, specialTag, layer);
            renderingHandler.render(layers, i, specialTag, layer);
            yOffset += renderingHandler.getModelHeight(layers, i, specialTag, layer);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        TileEntityBoard tileEntity = (TileEntityBoard) var1;
        GL11.glTranslated(x, y + 1.56, z);
        int metadata = tileEntity.getBlockMetadata();
        double boardHeight = 0.0625;
        GL11.glTranslated(.5, boardHeight, .5);
        GL11.glRotatef(-90 * metadata, 0, 1, 0);

        List<ItemStack> layers = tileEntity.getLayers();
        NBTTagCompound specialInfo = tileEntity.getSpecialInfo();

        renderLayers(layers, specialInfo);

        GL11.glPopMatrix();
    }
}
