package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.BoardRenderingRegistry;
import dk.mrspring.kitchen.model.*;
import dk.mrspring.kitchen.model.butter.ModelButter0;
import dk.mrspring.kitchen.model.butter.ModelButter1;
import dk.mrspring.kitchen.model.butter.ModelButter2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public class SandwichRender
{
    /*public static void loadRenderingHandlers()
    {
        BoardRenderingRegistry.registerRenderingHandler(KitchenItems.bread_slice, new IBoardRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                if (indexInList == itemStackList.size() - 1)
                    return new ModelBreadSliceTop();
                else return new ModelBreadSliceBottom();
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return 2 * 0.0625;
            }
        });
        BoardRenderingRegistry.registerRenderingHandler(KitchenItems.sliced_burger_bun, new IBoardRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                if (indexInList == itemStackList.size() - 1)
                    return new ModelBurgerBunTop();
                else return new ModelBurgerBunBottom();
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return 2 * 0.0625;
            }
        });
        BoardRenderingRegistry.registerRenderingHandler(KitchenItems.bacon, new IBoardRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return new ModelBaconCooked();
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return 2 * 0.0625;
            }
        });
        BoardRenderingRegistry.registerRenderingHandler(KitchenItems.raw_bacon, new IBoardRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return new ModelBaconRaw();
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
            {
                return 0.0625;
            }
        });
        BoardRenderingRegistry.registerRenderingHandler(KitchenItems.butter, new IBoardRenderingHandler()
        {
            @Override
            public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound specialTagInfo)
            {
                if (specialTagInfo != null)
                    if (specialTagInfo.hasKey("ClickAmount"))
                    {
                        int clickAmount = specialTagInfo.getInteger("ClickAmount");
                        switch (clickAmount)
                        {
                            case 0:
                                return new ModelButter0();
                            case 1:
                                return new ModelButter1();
                            case 2:
                                return new ModelButter2();
                            default:
                                return new ModelButter0();
                        }
                    }

                return new ModelButter0();
            }

            @Override
            public double getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound specialTagInfo)
            {
                return 0.0625;
            }
        });
    }

    public static void renderSandwich(ItemStack sandwich, NBTTagCompound specialTagInfo)
    {
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (sandwich.getTagCompound() != null)
        {
            NBTTagList layersTagList = sandwich.getTagCompound().getTagList("SandwichLayers", 10);
            if (layersTagList != null)
            {
                for (int i = 0; i < layersTagList.tagCount(); i++)
                {
                    NBTTagCompound layerCompound = layersTagList.getCompoundTagAt(i);
                    ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);
                    items.add(layer);
                }
            }
        }

        renderSandwich(items, specialTagInfo);
    }

    public static void renderSandwich(List<ItemStack> list, NBTTagCompound specialTagInfo)
    {
        GL11.glPushMatrix();
        //GL11.glRotatef(180,0,0,1);

        for (int i = 0; i < list.size(); i++)
        {
            NBTTagCompound infoCompound = new NBTTagCompound();
            if (i == list.size() - 1)
                infoCompound = specialTagInfo;

            ItemStack toRender = list.get(i);

            IBoardRenderingHandler renderingHandler = BoardRenderingRegistry.getRenderingHandlerFor(toRender);
            ModelBase model;

            model = renderingHandler.getModel(list, i, infoCompound);
            double modelHeight = renderingHandler.getModelHeight(list, i, infoCompound);

            GL11.glPushMatrix();

            if (model != null)
            {
                GL11.glRotatef(180, 0, 0, 1);
                model.render(null, 0, 0, 0, 0, 0, 0.0625F);
            } else
            {
                float scale = 1.3F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0, 0.35F, 0);

                EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, toRender);
                itemEntity.hoverStart = 0.0F;
                RenderItem.renderInFrame = true;
                GL11.glRotatef(180, 0, 1, 1);
                GL11.glTranslatef(.0F, -.2F, -1.395F);
                RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, -0.08385D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
//                GL11.glDisable(GL11.GL_CLIP_PLANE5);
            }

            GL11.glPopMatrix();

            GL11.glTranslated(0, modelHeight, 0);
        }

        GL11.glPopMatrix();
    }*/
}
