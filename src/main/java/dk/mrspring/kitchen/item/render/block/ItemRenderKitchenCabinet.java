package dk.mrspring.kitchen.item.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.ClientConfig;
import dk.mrspring.kitchen.item.render.ItemRenderer;
import dk.mrspring.kitchen.model.ModelKitchenCabinet;
import net.minecraft.item.ItemStack;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderKitchenCabinet extends ItemRenderer
{
    ModelKitchenCabinet cabinet = new ModelKitchenCabinet();

    public ItemRenderKitchenCabinet()
    {
        this.renderLikeBlock();
    }

    @Override
    public void renderEquippedFirstPerson(ItemRenderType type, ItemStack stack, Object... data)
    {
        centerRotate(-90F, 0F, 1F, 0F);
        super.renderEquippedFirstPerson(type, stack, data);
    }

    @Override
    public void renderAnyTypes(ItemStack item, Object... data)
    {
        cabinet.simpleRender(0F);
    }

    @Override
    public ClientConfig.RenderConfig getRenderConfig()
    {
        return ModConfig.getClientConfig().kitchen_cabinet_rendering;
    }
}
