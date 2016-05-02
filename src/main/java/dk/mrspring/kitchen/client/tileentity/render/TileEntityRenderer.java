package dk.mrspring.kitchen.client.tileentity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import static dk.mrspring.kitchen.client.util.ClientUtils.*;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public abstract class TileEntityRenderer<T extends TileEntity> extends TileEntitySpecialRenderer
{
    public void translateBlockModel()
    {
        translate(0.5F, 1.5F, 0.5F);
        rotate(180F, 0F, 0F, 1F);
    }

    public void rotateBasedOnMetadata(TileEntity entity)
    {
        this.rotateBasedOnMetadata(entity, 4);
    }

    public void rotateBasedOnMetadata(TileEntity entity, int angles)
    {
        translate(0.5F, 0.5F, 0.5F);
        rotate(angles, entity.getBlockMetadata());
        translate(-0.5F, -0.5F, -0.5F);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partial)
    {
        push();
        translate(x, y, z);
        renderModel((T) entity, partial);
        pop();
    }

    protected abstract void renderModel(T entity, float partial);
}
