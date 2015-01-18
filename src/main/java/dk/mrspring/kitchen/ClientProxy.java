package dk.mrspring.kitchen;

import net.minecraft.client.Minecraft;
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
//        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TheKitchenMod.knife, new ItemMeshDefinition()
//        {
//            @Override
//            public ModelResourceLocation getModelLocation(ItemStack p_178113_1_)
//            {
//                return new ModelResourceLocation("inventory");
//            }
//        }/*new ModelResourceLocation("test_item")*/);
//        MinecraftForgeClient.
//        RenderItem.field_175051_f
    }

    @Override
    public void registerItemRenderer(Item item, int metedata, ModelResourceLocation location)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TheKitchenMod.knife, 0, new ModelResourceLocation(ModInfo.MOD_ID + ":" + item.getUnlocalizedName().replace("item.", ""), "inventory"));
    }
}
