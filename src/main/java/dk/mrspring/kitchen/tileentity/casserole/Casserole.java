package dk.mrspring.kitchen.tileentity.casserole;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 27-01-2015.
 */
public class Casserole
{
    public static Map<String, Layer> itemLayerMap;
    public static Map<Item, String> itemLayerRelations;

    public static void load()
    {
        itemLayerMap = new HashMap<String, Layer>();
        itemLayerRelations = new HashMap<Item, String>();

        itemLayerRelations.put(KitchenItems.lasagna_plate, "LasagnaPlates");
        itemLayerRelations.put(KitchenItems.tomato_slice, "Salad");
        itemLayerRelations.put(KitchenItems.lettuce_leaf, "Salad");
        itemLayerRelations.put(KitchenItems.carrot_slice, "Salad");

        itemLayerMap.put("LasagnaPlates", new LayerLasagnaPlates());
        itemLayerMap.put("Salad", new LayerSalad());
    }

    public static Casserole loadFromItemStack(ItemStack stack)
    {
        if (stack.hasTagCompound())
            return loadFromNBT(stack.getTagCompound());
        else return new Casserole();
    }

    public static Casserole loadFromNBT(NBTTagCompound compound)
    {
        List<Layer> layers = new ArrayList<Layer>();
        NBTTagList list = compound.getTagList("Layers", 10);
        for (int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound layerCompound = list.getCompoundTagAt(i);
            if (layerCompound != null)
            {
                String layerName = layerCompound.getString("LayerIdentifier");
                if (!layerName.isEmpty())
                {
                    Layer baseLayer = itemLayerMap.get(layerName);
                    if (baseLayer != null)
                        layers.add(baseLayer.loadFromNBT(layerCompound));
                }
            }
        }
        return new Casserole().setLayers(layers);
    }

    public static final int RAW = 0, COOKED = 1, BURNT = 2;

    int state = RAW;
    List<Layer> layers = new ArrayList<Layer>();

    public Casserole setLayers(List<Layer> layers)
    {
        this.layers = layers;
        return this;
    }

    public List<Layer> getLayers()
    {
        return layers;
    }

    public Casserole addLayer(Layer layer)
    {
        layers.add(layer);
        return this;
    }

    public ItemStack[] removeTopLayer()
    {
        Layer topLayer = this.getTopLayer();
        if (topLayer != null)
        {
            this.layers.remove(topLayer);
            return topLayer.removeLayer();
        } else return new ItemStack[0];
    }

    public Casserole addLayer(ItemStack stack)
    {
        String layerName = itemLayerRelations.get(stack.getItem());
        Layer layer = itemLayerMap.get(layerName).newInstance(stack);
        this.addLayer(layer);
        return this;
    }

    public boolean addItem(ItemStack stack)
    {
        if (stack == null)
            return false;

        if (itemLayerRelations.containsKey(stack.getItem()))
            if (itemLayerMap.containsKey(itemLayerRelations.get(stack.getItem())))
            {
                if (layers.size() > 0)
                    if (getTopLayer().canAddLayerOnTop(stack))
                    {
                        this.addLayer(stack);
                        return true;
                    } else return getTopLayer().handleRightClick(stack);
                else
                {
                    this.addLayer(stack);
                    return true;
                }
            }
        return false;
    }

    public Layer getTopLayer()
    {
        if (this.layers.size() > 0)
            return this.layers.get(layers.size() - 1);
        else return null;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("State", this.state);
        NBTTagList layerNBTList = new NBTTagList();
        for (int i = 0; i < layers.size(); i++)
        {
            Layer layer = layers.get(i);
            NBTTagCompound layerCompound = new NBTTagCompound();
            layerCompound.setString("LayerIdentifier", layer.getTypeName());
            layerCompound.setInteger("LayerIndex", i);
            layer.writeToNBT(layerCompound);
            layerNBTList.appendTag(layerCompound);
        }
        compound.setTag("Layers", layerNBTList);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        this.state = compound.getInteger("State");
        if (state > 2)
            state = 2;
        else if (state < 0)
            state = 0;
        NBTTagList layerNBTList = compound.getTagList("Layers", 10);
        for (int i = 0; i < layerNBTList.tagCount(); i++)
        {
            NBTTagCompound layerCompound = layerNBTList.getCompoundTagAt(i);
            String layerType = layerCompound.getString("LayerIdentifier");
            int layerIndex = layerCompound.getInteger("LayerIndex");
            if (itemLayerMap.containsKey(layerType))
            {
                Layer layer = itemLayerMap.get(layerType).loadFromNBT(layerCompound);
                this.layers.add(layerIndex, layer);
            } else
                ModLogger.print(ModLogger.INFO, "Unable to find casserole layer type: \"" + layerType + "\". Ignoring it.");
        }
    }

    public enum CasseroleState
    {
        RAW,
        COOKED,
        BURNT
    }
}
