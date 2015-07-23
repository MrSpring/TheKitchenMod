package dk.mrspring.kitchen.config.wrapper;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.javanbt.NBTJsonDecompile;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class JsonItemStack
{
    String item_name = "";
    int stack_size = 1;
    int metadata = 0;
    Map<String, Object> tag = null;

    public JsonItemStack(String itemName, int stackSize, int metadata)
    {
        this.item_name = itemName;
        this.stack_size = stackSize;
        this.metadata = metadata;
    }

    public JsonItemStack(String itemName, int stackSize)
    {
        this(itemName, stackSize, 0);
    }

    public JsonItemStack(String itemName)
    {
        this(itemName, 1, 0);
    }

    public JsonItemStack(ItemStack stack)
    {
        if (stack != null)
        {
            GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            if (identifier != null)
            {
                this.item_name = identifier.toString();
                this.stack_size = stack.stackSize;
                this.metadata = stack.getItemDamage();
            } else
            {
                this.item_name = "";
                this.stack_size = 1;
                this.metadata = 0;
            }
        } else
        {
            this.item_name = "";
            this.stack_size = 1;
            this.metadata = 0;
        }
    }

    public ItemStack toItemStack()
    {
        if (!item_name.isEmpty())
        {
            String modId = "minecraft";
            String itemName;
            if (item_name.contains(":"))
            {
                modId = item_name.split(":")[0];
                itemName = item_name.split(":")[1];
            } else itemName = item_name;
            ItemStack stack = GameRegistry.findItemStack(modId, itemName, stack_size);
            if (stack != null)
            {
                stack.setItemDamage(metadata);
                if (tag != null)
                {
                    NBTTagCompound compound = NBTJsonDecompile.createFromJsonObject(tag);
                    stack.setTagCompound(compound);
                }
                return stack;
            } else return null;
        } else return null;
    }
}
