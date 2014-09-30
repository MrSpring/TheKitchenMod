package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.ISandwichableRenderingHandler;
import dk.mrspring.kitchen.api.SandwichableRenderingRegistry;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
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
	public static void loadRenderingHandlers()
	{
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bread_slice, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList)
			{
				if (indexInList == itemStackList.size() - 1)
					return new ModelBreadSliceTop();
				else return new ModelBreadSliceBottom();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList)
			{
				if (indexInList == itemStackList.size() - 1)
					return 3;
				else return 2;
			}
		});
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bacon, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList)
			{
				return new ModelBaconCooked();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList)
			{
				return 2;
			}
		});
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.raw_bacon, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList)
			{
				return new ModelBaconRaw();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList)
			{
				return 1;
			}
		});
	}

	public static void renderSandwich(ItemStack sandwich)
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

		renderSandwich(items);
	}

	public static void renderSandwich(List<ItemStack> list)
	{
		GL11.glPushMatrix();

		for (int i = 0; i < list.size(); i++)
		{
			ItemStack item = list.get(i);

			ISandwichableRenderingHandler renderingHandler = SandwichableRenderingRegistry.getRenderingHandlerFor(item);
			ModelBase model = null;
			if (renderingHandler!=null)
				model = renderingHandler.getModel(list, i);

			double yOffsetPerPixel = .04;
			GL11.glTranslated(0, yOffsetPerPixel * renderingHandler.getModelHeight(list, i), 0);

			GL11.glPushMatrix();

			if (model != null)
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			else
			{
				EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
				itemEntity.hoverStart = 0.0F;
				RenderItem.renderInFrame = true;
				GL11.glRotatef(180, 0, 1, 1);
				RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
			}

			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}
}
