package dk.mrspring.kitchen;

import net.minecraft.block.Block;
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
        registerItemRenderer(TheKitchenMod.bread_slice, 0, ModInfo.MOD_ID+":bread_slice_top");
        registerItemRenderer(TheKitchenMod.bread_slice, 1, ModInfo.MOD_ID+":bread_slice_bottom");
    }

    @Override
    public void registerItemRenderer(Item item, int metadata, String name)
    {
        this.registerItemRenderer(item, metadata, new ModelResourceLocation(name, "inventory"));
    }
    
    @Override
    public void registerItemRenderer(Item item, int metadata, ModelResourceLocation location)
    {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metadata, location);
    }
    
    @Override
    public void addVariants(Item item, String... pars)
    {
    	ModelBakery.addVariantName(item, pars);
    }
    
    @Override
    public void registerBlockRenderer(Block block, int i, ModelResourceLocation location)
    {
    	
    }
}
