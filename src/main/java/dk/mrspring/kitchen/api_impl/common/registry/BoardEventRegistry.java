package dk.mrspring.kitchen.api_impl.common.registry;

import dk.mrspring.kitchen.api.board.IBoardEventRegistry;
import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api.board.ICuttingBoard;
import dk.mrspring.kitchen.api_impl.common.board.BasicItemHandler;
import dk.mrspring.kitchen.api_impl.common.board.KnifeItemHandler;
import dk.mrspring.kitchen.api_impl.common.board.SandwichableItemHandler;
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
    private static final IBoardItemHandler defaultHandler = new BasicItemHandler();
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
        if (handler != null) handlers.add(0, handler);
    }

    @Override
    public IBoardItemHandler getHandlerFor(ICuttingBoard board, ItemStack item, EntityPlayer player)
    {
        for (IBoardItemHandler handler : handlers)
            if (handler.isForItem(board, item, player)) return handler;
        return defaultHandler;
    }
}
