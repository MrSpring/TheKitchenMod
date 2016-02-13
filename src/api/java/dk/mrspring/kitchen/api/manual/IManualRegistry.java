package dk.mrspring.kitchen.api.manual;

import dk.mrspring.kitchen.api.book.IBookRegistry;
import net.minecraft.item.ItemStack;

/**
 * Created on 06-12-2015 for TheKitchenMod.
 */
public interface IManualRegistry
{
    void registerManual(String identifier, IBookRegistry bookRegistry);

    IBookRegistry getManual(String identifier);

    ItemStack makeManualStack(String identifier);
}
