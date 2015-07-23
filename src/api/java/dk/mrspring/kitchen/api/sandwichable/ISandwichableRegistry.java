package dk.mrspring.kitchen.api.sandwichable;

import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */
public interface ISandwichableRegistry
{
    /**
     * @param stack The Stack to get the Sandwichable instance for.
     * @return Returns the Sandwichable instance for the ItemStack passed through. Null if the item is not Sandwichable.
     */
    ISandwichable getSandwichableForItem(ItemStack stack);

    /**
     * Checks if the Stack is Sandwichable. In most cases it's best to do this manually using #getSandwichableForItem
     * and checking if it is null, to minimize the amount of list iterations.
     *
     * @param stack The {@link Stack} to check if it is Sandwichable.
     * @return Returns true if the Stack is associated with any Sandwichable instances.
     */
    boolean isSandwichable(ItemStack stack);

    /**
     * Makes an Item able to be used in a Sandwich
     *
     * @param stack           The Stack to make Sandwichable.
     * @param heal            The amount of health the Item will heal the player when they eat the Sandwich.
     * @param isBread         Whether or not this item can be used as a top/bottom of a Sandwich.
     * @param showInformation Whether or not this item should show "Sandwichable" under its name when hovering over it.
     * @param dropItem        Whether or not the player will get back the item when it's taken off the Cutting Board.
     * @return Returns the Sandwichable for the item that was made Sandwichable.
     */
    ISandwichable makeItemSandwichable(ItemStack stack, int heal, boolean isBread, boolean showInformation, boolean dropItem);

    void registerSandwichable(ISandwichable sandwichable);
}
