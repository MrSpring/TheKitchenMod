package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.ISandwichableRenderingHandler;
import dk.mrspring.kitchen.api.SandwichableRenderingRegistry;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
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
	public static void loadRenderingHandlers()
	{
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bread_slice, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				if (indexInList == itemStackList.size() - 1)
					return new ModelBreadSliceTop();
				else return new ModelBreadSliceBottom();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				if (indexInList == itemStackList.size() - 1)
					return 3;
				else return 2;
			}
		});
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bacon, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				return new ModelBaconCooked();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				return 2;
			}
		});
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.raw_bacon, new ISandwichableRenderingHandler()
		{
			@Override
			public ModelBase getModel(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				return new ModelBaconRaw();
			}

			@Override
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound compound)
			{
				return 1;
			}
		});
		SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.butter, new ISandwichableRenderingHandler()
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
			public int getModelHeight(List<ItemStack> itemStackList, int indexInList, NBTTagCompound specialTagInfo)
			{
				return 1;
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

		for (int i = 0; i < list.size(); i++)
		{
			NBTTagCompound infoCompound = null;
			if (i == list.size() - 1)
				infoCompound = specialTagInfo;

			ItemStack item = list.get(i);

			ISandwichableRenderingHandler renderingHandler = SandwichableRenderingRegistry.getRenderingHandlerFor(item);
			ModelBase model = null;
			if (renderingHandler != null)
				model = renderingHandler.getModel(list, i, infoCompound);

			double yOffsetPerPixel = .04;
			GL11.glTranslated(0, yOffsetPerPixel * renderingHandler.getModelHeight(list, i, infoCompound), 0);

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
