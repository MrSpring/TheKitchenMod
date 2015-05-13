package dk.mrspring.kitchen.api.board;

import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BoardItemRenderingHandler implements IBoardRenderingHandler
{

    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return true;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        GL11.glPushMatrix();
        EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, rendering);
        itemEntity.hoverStart = 0.0F;
        RenderItem.renderInFrame = true;
        GL11.glRotatef(180, 0, 1, 1);
        GL11.glTranslatef(.0F, -.2F, -1.395F);
        RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, -0.08385D, 0.0F, 0.0F);
        RenderItem.renderInFrame = false;
        GL11.glPopMatrix();
        // TODO: Render. See SandwichRender
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 1; // TODO: Return height. See SandwichRender
    }
}
