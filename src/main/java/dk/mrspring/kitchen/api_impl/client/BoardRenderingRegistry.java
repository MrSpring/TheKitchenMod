package dk.mrspring.kitchen.api_impl.client;

import dk.mrspring.kitchen.api_impl.client.board.ItemRenderingHandler;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.api.board.IBoardRenderingRegistry;
import dk.mrspring.kitchen.api_impl.client.board.BaconRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.board.BreadSliceRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.board.SlicingRenderingHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BoardRenderingRegistry implements IBoardRenderingRegistry
{
    private static BoardRenderingRegistry ourInstance = new BoardRenderingRegistry();
    private static IBoardRenderingHandler defaultHandler = new ItemRenderingHandler();
    private List<IBoardRenderingHandler> registeredHandlers = new ArrayList<IBoardRenderingHandler>();

    private BoardRenderingRegistry()
    {
        registerRenderingHandler(new BreadSliceRenderingHandler());
        registerRenderingHandler(new SlicingRenderingHandler());
        registerRenderingHandler(new BaconRenderingHandler());
    }

    public static BoardRenderingRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerRenderingHandler(IBoardRenderingHandler handler)
    {
        registeredHandlers.add(handler);
    }

    @Override
    public IBoardRenderingHandler getHandlerFor(List<ItemStack> layers, int index, NBTTagCompound compound, ItemStack rendering)
    {
        for (IBoardRenderingHandler handler : registeredHandlers)
            if (handler.shouldBeUsed(layers, index, compound, rendering))
                return handler;
        return defaultHandler;
    }
}
