package dk.mrspring.kitchen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by MrSpring on 01-12-2014 for TheKitchenMod.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        ModelBakery.addVariantName(TheKitchenMod.bread_slice, new String[]{
        		ModInfo.MOD_ID+":bread_slice_top"
        });
        
        ModelBakery.addVariantName(TheKitchenMod.bread_slice, new String[]{
        		ModInfo.MOD_ID+":bread_slice_bottom"
        });
        
        registerItemRenderer(TheKitchenMod.bread_slice, 0, ModInfo.MOD_ID+":bread_slice_top");
        registerItemRenderer(TheKitchenMod.bread_slice, 1, ModInfo.MOD_ID+":bread_slice_bottom");
    }

    @Override
    public void registerItemRenderer(Item item, int metadata, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metadata, new ModelResourceLocation(name, "inventory"));
    }
}
