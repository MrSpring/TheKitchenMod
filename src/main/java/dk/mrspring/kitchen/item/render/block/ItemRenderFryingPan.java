package dk.mrspring.kitchen.item.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.ClientConfig;
import dk.mrspring.kitchen.item.render.ItemRenderer;
import dk.mrspring.kitchen.model.ModelPan;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.ClientUtils.translate;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderFryingPan extends ItemRenderer
{
    ModelPan pan = new ModelPan();

    public ItemRenderFryingPan()
    {
        this.renderLikeBlock();
    }

    @Override
    public void renderAnyTypes(ItemStack item, Object... data)
    {
        pan.simpleRender(0F);
    }

    @Override
    public void renderEquippedFirstPerson(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0F, 0.6F, -0.2F);
        centerRotate(225F, 0F, 1F, 0F);
        centerScale(1.5F);
        super.renderEquippedFirstPerson(type, stack, data);
    }

    @Override
    public void renderEquipped(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(-0.25F, 0.4F, -0.25F);
        centerRotate(-45F, 0F, 1F, 0F);
        centerScale(1.75F);
        super.renderEquipped(type, stack, data);
    }

    @Override
    public void renderInventory(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0F, 0.4F, 0F);
        centerScale(2F);
        super.renderInventory(type, stack, data);
    }

    @Override
    public ClientConfig.RenderConfig getRenderConfig()
    {
        return ModConfig.getClientConfig().pan_rendering;
    }
}
