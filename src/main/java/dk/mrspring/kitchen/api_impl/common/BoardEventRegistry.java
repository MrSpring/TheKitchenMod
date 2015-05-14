package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.board.*;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 30-09-2014 for TheKitchenMod.
 */
public class BoardEventRegistry implements IBoardEventRegistry
{
    private static final BoardEventRegistry instance = new BoardEventRegistry();
    private List<IBoardItemHandler> handlers = new ArrayList<IBoardItemHandler>();

    private BoardEventRegistry()
    {
        registerHandler(new SandwichableItemHandler());
        registerHandler(new KnifeItemHandler());
    }

    public static BoardEventRegistry instance()
    {
        return instance;
    }

    @Override
    public void registerHandler(IBoardItemHandler handler)
    {
        if (handler != null)
            handlers.add(handler);
    }

    @Override
    public IBoardItemHandler getHandlerFor(TileEntityBoard board, ItemStack item, EntityPlayer player)
    {
        for (IBoardItemHandler handler : handlers)
        {
            System.out.println("Checking if handler matches. Class: " + handler.getClass().getName());
            if (handler.isForItem(board, item, player))
            {
                System.out.println("Returning handler of type: " + handler.getClass().getName());
                return handler;
            }
        }
        return new BasicItemHandler();
    }
}
