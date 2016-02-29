package dk.mrspring.kitchen.api_impl.client.book;

import dk.mrspring.kitchen.api.book.IBookRegistry;
import dk.mrspring.kitchen.api.manual.IManualRegistry;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 06-12-2015 for TheKitchenMod.
 */
public class ManualRegistry implements IManualRegistry, IResourceManagerReloadListener
{
    private static final ManualRegistry ourInstance = new ManualRegistry();

    public static ManualRegistry getInstance()
    {
        return ourInstance;
    }

    private final Map<String, IBookRegistry> manuals = new HashMap<String, IBookRegistry>();

    public final IBookRegistry WAFFLE_IRON = new JsonBookRegistry(new ResourceLocation("kitchen", "waffle_iron"));

    private ManualRegistry()
    {
        registerManual("waffle_iron", WAFFLE_IRON);
    }

    @Override
    public void registerManual(String identifier, IBookRegistry manualRegistry)
    {
        if (!manuals.containsKey(identifier)) manuals.put(identifier, manualRegistry);
    }

    @Override
    public IBookRegistry getManual(String identifier)
    {
        return manuals.get(identifier);
    }

    @Override
    public ItemStack makeManualStack(String identifier)
    {
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager manager)
    {
        for (IBookRegistry registry : manuals.values())
            if (registry instanceof IResourceManagerReloadListener)
                ((IResourceManagerReloadListener) registry).onResourceManagerReload(manager);
    }
}
