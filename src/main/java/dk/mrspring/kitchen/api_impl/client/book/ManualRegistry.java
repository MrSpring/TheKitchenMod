package dk.mrspring.kitchen.api_impl.client.book;

import dk.mrspring.kitchen.api.book.IBookRegistry;
import dk.mrspring.kitchen.api.manual.IManualRegistry;
import dk.mrspring.kitchen.api_impl.client.book.registry.GrinderType;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 06-12-2015 for TheKitchenMod.
 */
public class ManualRegistry implements IManualRegistry
{
    private static final ManualRegistry ourInstance = new ManualRegistry();

    public static ManualRegistry getInstance()
    {
        return ourInstance;
    }

    private final Map<String, IBookRegistry> manuals = new HashMap<String, IBookRegistry>();

    private ManualRegistry()
    {
        registerManual("grinder", new GrinderType());
    }

    @Override
    public void registerManual(String identifier, IBookRegistry bookRegistry)
    {

    }

    @Override
    public ItemStack makeManualStack(String identifier)
    {
        return null;
    }
}
