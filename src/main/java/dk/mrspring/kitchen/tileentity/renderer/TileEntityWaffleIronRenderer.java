package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.render.ItemRenderMixingBowl;
import dk.mrspring.kitchen.model.block.ModelWaffleIron;
import dk.mrspring.kitchen.tileentity.TileEntityWaffleIron;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 09-12-2014 for TheKitchenMod.
 */
public class TileEntityWaffleIronRenderer extends TileEntityTimeableRenderer
{
    ResourceLocation offTexture = new ResourceLocation(ModInfo.toTexture("textures/models/waffle_iron.png"));
    ResourceLocation onTexture = new ResourceLocation(ModInfo.toTexture("textures/models/waffle_iron_on.png"));
    ModelWaffleIron model = new ModelWaffleIron();

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float partial)
    {
        super.renderTileEntityAt(var1, x, y, z, partial);
        TileEntityWaffleIron entity = (TileEntityWaffleIron) var1;

        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();

        if (((TileEntityWaffleIron) var1).getCookTime() > 0 && !((TileEntityWaffleIron) var1).isOpen())
            Minecraft.getMinecraft().renderEngine.bindTexture(onTexture);
        else Minecraft.getMinecraft().renderEngine.bindTexture(offTexture);

        int metadata = var1.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F, entity.getLidAngle(), entity.getLidDirection(),
                entity.getWaffleState(), ItemRenderMixingBowl.COLOR_HANDLER.getColorAsRGB(entity.dough), partial);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void transformTimer(TileEntity tileEntity)
    {
        int metadata = tileEntity.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);

        GL11.glTranslatef(0.1F, 0.836F, -0.27F);

        float scale = 0.4F;
        GL11.glScalef(scale, scale, scale);
    }
}
