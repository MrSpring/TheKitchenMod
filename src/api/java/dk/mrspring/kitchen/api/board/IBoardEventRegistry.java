package dk.mrspring.kitchen.api.board;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IBoardEventRegistry
{
    /**
     * Registers an item handler.
     *
     * @param handler The handler to register.
     */
    void registerHandler(IBoardItemHandler handler);

    /**
     * Uses {@link IBoardItemHandler#isForItem} to test each handler.
     *
     * @param board  The TileEntityBoard requesting the handler.
     * @param item   The {@link ItemStack} to get the handler for.
     * @param player The player interacting with the Cutting Board.
     * @return Returns the matching IBoardItemHandler.
     */
    IBoardItemHandler getHandlerFor(ICuttingBoard board, ItemStack item, EntityPlayer player);
}
