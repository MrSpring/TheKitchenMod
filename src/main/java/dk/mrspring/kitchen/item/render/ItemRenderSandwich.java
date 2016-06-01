package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.ClientConfig;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.ClientUtils.*;

public class ItemRenderSandwich extends ItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case INVENTORY:
                return false;
            default:
                return super.handleRenderType(item, type);
        }
    }

    @Override
    public void renderEquippedFirstPerson(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0.5F, 0.3F, 0F);
        rotate(90F, 0F, 1F, 0F);
        super.renderEquippedFirstPerson(type, stack, data);
    }

    @Override
    public void renderEquipped(ItemRenderType type, ItemStack stack, Object... data)
    {
        rotate(11, 0, 1, 0);
        rotate(15, 0, 0, 1);
        rotate(-70, 1, 0, 0);

        translate(0.42F, -0.1F, 0.35F);
        scale(1.5F);

        super.renderEquipped(type, stack, data);
    }

    @Override
    public void renderEntity(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0F, -0.175F, 0F);
        scale(2F);

        super.renderEntity(type, stack, data);
    }

    @Override
    public void renderAnyTypes(ItemStack item, Object... data)
    {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(PlateRender.IS_RENDERING_ON_PLATE))
        {
            rotate(-90, 1, 0, 0);
            translate(0F, -0.05F, 0.255F);
        }
        SandwichRender.renderSandwich(new SandwichRender.Sandwich(item));
    }

    @Override
    public ClientConfig.RenderConfig getRenderConfig()
    {
        return ModConfig.getClientConfig().sandwich_rendering;
    }
}
