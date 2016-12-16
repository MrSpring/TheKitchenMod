package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.sandwichable.ISandwichableRenderingHandler;
import dk.mrspring.kitchen.api.sandwichable.SandwichableRenderingRegistry;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
import dk.mrspring.kitchen.model.butter.ModelButter0;
import dk.mrspring.kitchen.model.butter.ModelButter1;
import dk.mrspring.kitchen.model.butter.ModelButter2;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created by MrSpring on 30-09-2014 for TheKitchenMod.
 */
public class SandwichRender
{
    public static final String IS_RENDERING_IN_SANDWICH = "IsRenderingInSandwich";

    public static final double MODEL_SCALE = 0.75D;

    public static void loadRenderingHandlers()
    {
        SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bread_slice, new ISandwichableRenderingHandler()
        {
            @Override
            public ModelBase getModel(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                if (indexInList == itemStacks.length - 1)
                    return new ModelBreadSliceTop();
                else return new ModelBreadSliceBottom();
            }

            @Override
            public double getModelHeight(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                return 0.125;
            }
        });
        SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.bacon, new ISandwichableRenderingHandler()
        {
            @Override
            public ModelBase getModel(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                return new ModelBaconCooked();
            }

            @Override
            public double getModelHeight(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                return 2 * 0.0625D;
            }
        });
        SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.raw_bacon, new ISandwichableRenderingHandler()
        {
            @Override
            public ModelBase getModel(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                return new ModelBaconRaw();
            }

            @Override
            public double getModelHeight(ItemStack[] itemStacks, int indexInList, NBTTagCompound compound)
            {
                return 0.0625;
            }
        });
        SandwichableRenderingRegistry.registerRenderingHandler(KitchenItems.butter, new ISandwichableRenderingHandler()
        {
            @Override
            public ModelBase getModel(ItemStack[] itemStacks, int indexInList, NBTTagCompound specialTagInfo)
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
            public double getModelHeight(ItemStack[] itemStacks, int indexInList, NBTTagCompound specialTagInfo)
            {
                return 0.05;
            }
        });
    }

    public static double renderSandwich(Sandwich sandwich)
    {
        ItemStack[] stacks = sandwich.items;
        double yOffset = 0D;
        for (int i = 0; i < stacks.length; i++)
        {
            ItemStack stack = stacks[i];
            push();
            translate(0D, yOffset, 0D);
            yOffset += renderItem(stack, i, sandwich);
            pop();
        }
        return yOffset;
    }

    private static double renderItem(ItemStack stack, int index, Sandwich sandwich)
    {
        if (stack.hasTagCompound()) stack.getTagCompound().setBoolean(IS_RENDERING_IN_SANDWICH, true);

        NBTTagCompound compound = index == sandwich.items.length - 1 ? sandwich.info : null;

        ISandwichableRenderingHandler renderingHandler = SandwichableRenderingRegistry.getRenderingHandlerFor(stack);
        ModelBase model = renderingHandler.getModel(sandwich.items, index, compound);
        double height = renderingHandler.getModelHeight(sandwich.items, index, compound);

        if (model != null)
        {
            scale((float) MODEL_SCALE);
            translate(0F, 1.5F, 0F);
            rotate(180F, 0F, 0F, 1F);
            model.render(null, 0, 0, 0, 0, 0, 0.0625F);
            height *= MODEL_SCALE;
        } else
        {
            translate(0F, ITEM_PIXEL / 2F, 0F);
            rotate(90F, 1F, 0F, 0F);
            renderItemStack(stack);
        }

        if (stack.hasTagCompound()) stack.getTagCompound().removeTag(IS_RENDERING_IN_SANDWICH);
        return height;
    }

    public static class Sandwich
    {
        NBTTagCompound info = null;
        ItemStack[] items;

        public Sandwich(ItemStack itemStack)
        {
            if (!itemStack.hasTagCompound())
                items = new ItemStack[0];
            else
            {
                NBTTagList list = itemStack.getTagCompound().getTagList("SandwichLayers", 10);
                if (list.tagCount() > 0)
                {
                    items = new ItemStack[list.tagCount()];
                    for (int i = 0; i < list.tagCount(); i++)
                        items[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
                } else items = new ItemStack[0];
            }
        }

        public Sandwich(List<ItemStack> items, NBTTagCompound compound)
        {
            this.items = items.toArray(new ItemStack[items.size()]);
            this.info = compound;
        }
    }
}
