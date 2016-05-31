package dk.mrspring.kitchen.tileentity.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;

import static dk.mrspring.kitchen.ClientUtils.rotate;
import static dk.mrspring.kitchen.ClientUtils.translate;

@SideOnly(Side.CLIENT)
public class TileEntityBoardRenderer extends TileEntityRenderer<TileEntityBoard>
{
    @Override
    protected void renderModel(TileEntityBoard entity, float partial)
    {
        translate(0.5F, 0.125F, 0.5F);
        int metadata = entity.getBlockMetadata();
        if (metadata == 1) rotate(90F, 0F, 1F, 0F);
        SandwichRender.renderSandwich(new SandwichRender.Sandwich(entity.getLayers(), entity.getSpecialInfo()));
    }
}
