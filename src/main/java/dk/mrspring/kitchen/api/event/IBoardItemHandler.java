package dk.mrspring.kitchen.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Konrad on 10-05-2015.
 */
public interface IBoardItemHandler
{
    /**
     * @param stack ItemStack being tested.
     * @return Returns true if this handler should be used for the ItemStack's Item.
     */
    boolean isForItem(ItemStack stack);

    /**
     * Gets called before #onAdded but after #onRightClicked through the top-most item's handler.
     *
     * @param layersOnBoard Layers already present on the board, excluding the one being called for.
     * @param adding        The ItemStack that is being tested.
     * @param player        The Player trying to add an item to the Cutting Board.
     * @return Returns true if the item can be added to the Cutting Board. False if not.
     */
    boolean canAdd(List<ItemStack> layersOnBoard, ItemStack adding, EntityPlayer player);

    /**
     * Gets called when the item is successfully placed on the Cutting Board.
     *
     * @param layersOnBoard Layers already present on the board, including the one being called for.
     * @param added         The ItemStack that was used to right-click on the Cutting Board.
     * @param player        The Player that added the Item to the Cutting Board.
     * @return Returns the ItemStack that should be added to the List of layers. Usually, stackSize should be 1.
     */
    ItemStack onAdded(List<ItemStack> layersOnBoard, ItemStack added, EntityPlayer player);

    /**
     * Gets called when this item handler is on top of the layer stack, and the Cutting Board is right-clicked.
     *
     * @param layersOnBoard The List of layers present on the board.
     * @param clicked       The ItemStack that was right-clicked with.
     * @param player        The Player that right-clicked the Cutting Board.
     * @return Returns true if the item can be placed on top of the current item handler. False will not allow the item
     * to be placed.
     */
    boolean onRightClicked(List<ItemStack> layersOnBoard, ItemStack clicked, EntityPlayer player);

    /**
     * @param layersOnBoard The List of layers present on the board.
     * @param player        The Player that is trying to remove the Item from the Cutting Board.
     * @return Returns true if the top-most item can be removed. False if not.
     */
    boolean canBeRemoved(List<ItemStack> layersOnBoard, ItemStack topMostItem, EntityPlayer player);

    /**
     * Gets called after #canBeRemoved and determines what Item shall be spawned in the world.
     *
     * @param layersOnBoard Layers already present on the board, excluding the one that was removed.
     * @param removed       The ItemStack that was previously on top of the Cutting Board.
     * @param player        The Player that removed the Item to the Cutting Board.
     * @return Returns the ItemStack that will be given back to the player. Null if nothing is left.
     */
    ItemStack onRemoved(List<ItemStack> layersOnBoard, ItemStack removed, EntityPlayer player);
}
