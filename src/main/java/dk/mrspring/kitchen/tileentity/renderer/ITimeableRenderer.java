package dk.mrspring.kitchen.tileentity.renderer;

import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 13-12-2014 for TheKitchenMod.
 */
public interface ITimeableRenderer
{
    /***
     * Locally translate the timer around using GL11.glTranslate/glRotate
     * @param tileEntity The tileEntity from renderTileEntityAt
     */
    public void translateTimer(TileEntity tileEntity);
}
