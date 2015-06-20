package dk.mrspring.kitchen.api.board;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 10-05-2015.
 */
public interface IBoardItemHandler
{
    /**
     * @param stack ItemStack being tested.
     * @return Returns true if this handler should be used for the ItemStack's Item.
     */
    boolean isForItem(ICuttingBoard tileEntityBoard, ItemStack stack, EntityPlayer player);

    /**
     * Gets called before #onAdded but after #onRightClicked through the top-most item's handler.
     *
     * @param tileEntityBoard The Cutting Board's TileEntity. Use getLayers() to get layers on the board.
     * @param adding          The ItemStack that is being tested.
     * @param player          The Player trying to add an item to the Cutting Board.
     * @return Returns true if the item can be added to the Cutting Board. False if not.
     */
    boolean canAdd(ICuttingBoard tileEntityBoard, ItemStack adding, EntityPlayer player);

    /**
     * Gets called when the item is successfully placed on the Cutting Board.
     *
     * @param tileEntityBoard The Cutting Board's TileEntity. Use getLayers() to get layers on the board.
     * @param added           The ItemStack that was used to right-click on the Cutting Board.
     * @param player          The Player that added the Item to the Cutting Board.
     * @return Returns the ItemStack that should be added to the List of layers. Usually, stackSize should be 1.
     */
    ItemStack onAdded(ICuttingBoard tileEntityBoard, ItemStack added, EntityPlayer player);

    /**
     * Gets called when this item handler is on top of the layer stack, and the Cutting Board is right-clicked.
     *
     * @param tileEntityBoard The Cutting Board's TileEntity. Use getLayers() to get layers on the board.
     * @param clicked         The ItemStack that was right-clicked with.
     * @param player          The Player that right-clicked the Cutting Board.
     * @return Return true to bypass other right-click functionality.
     */
    boolean onRightClicked(ICuttingBoard tileEntityBoard, ItemStack clicked, EntityPlayer player);

    /**
     * @param tileEntityBoard The Cutting Board's TileEntity. Use getLayers() to get layers on the board.
     * @param topMostItem     The ItemStack that is being removed. Same as ICuttingBoard#getTipItem.
     * @param player          The Player that is trying to remove the Item from the Cutting Board.
     * @return Returns true if the top-most item can be removed. False if not.
     */
    boolean canBeRemoved(ICuttingBoard tileEntityBoard, ItemStack topMostItem, EntityPlayer player);

    /**
     * Gets called after #canBeRemoved and determines what Item shall be spawned in the world.
     *
     * @param tileEntityBoard The Cutting Board's TileEntity. Use getLayers() to get layers on the board.
     * @param removed         The ItemStack that was previously on top of the Cutting Board.
     * @param player          The Player that removed the Item to the Cutting Board.
     * @return Returns the ItemStack that will be given back to the player. Null if nothing is left.
     */
    ItemStack onRemoved(ICuttingBoard tileEntityBoard, ItemStack removed, EntityPlayer player);
}
