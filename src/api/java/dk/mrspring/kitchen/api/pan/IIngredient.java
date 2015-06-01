package dk.mrspring.kitchen.api.pan;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface IIngredient
{
    String getName();

    String getLocalizedName();

    /**
     * Is used to determine if this Ingredient should be used for the Item that was right-clicked with.
     *
     * @param pan     The Frying Pan the ingredient is trying to get to.
     * @param clicked The ItemStack that was right-clicked with.
     * @param player  The Player that right-clicked the Frying Pan.
     * @return Returns true if this Ingredient should continue being used when adding the item.
     * #canAdd will be called if this returns true.
     */
    boolean isForItem(IFryingPan pan, ItemStack clicked, EntityPlayer player);

    /**
     * Gets called when the Frying Pan is right-clicked, and does not already contain an Ingredient.
     *
     * @param pan    The Frying Pan this ingredient is on.
     * @param adding The ItemStack that was right-clicked with.
     * @param player The Player that right-clicked the Frying Pan.
     * @return Returns true if this Ingredient can be set to the Frying Pan.
     */
    boolean canAdd(IFryingPan pan, ItemStack adding, EntityPlayer player);

    /**
     * Gets called after the ingredient has been set on the pan, and the cook time has been reset.
     *
     * @param pan    The Frying Pan this ingredient was put on.
     * @param added  The ItemStack that was used to set this ingredient.
     * @param player The player that right-clicked the frying pan.
     */
    void onAdded(IFryingPan pan, ItemStack added, EntityPlayer player);

    /**
     * Called before any other method. Called when this ingredient is on the Frying Pan, and it is right-clicked.
     *
     * @param pan     The Frying Pan this ingredient is on.
     * @param clicked The ItemStack that was right-clicked with.
     * @param player  The Player that right-clicked the Frying Pan.
     */
    void onRightClicked(IFryingPan pan, ItemStack clicked, EntityPlayer player);

    /**
     * @param pan The Frying Pan this ingredient is on.
     * @return Returns how long it takes to cook this Ingredient.
     */
    int getCookTime(IFryingPan pan);

    /**
     * Gets called when the frying pan is right-clicked with an empty hand, and the cook time is greater than
     * #getCookTime
     *
     * @param pan    The Frying Pan this ingredient is on.
     * @param player The Player removing the ingredient.
     * @return Returns true if the removal can continue.
     */
    boolean canBeRemoved(IFryingPan pan, EntityPlayer player);

    /**
     * Called when the frying pan is being right-clicked with an empty hand, and the cook time is greater than
     * #getCookTime
     *
     * @param pan    The Frying Pan this ingredient is on.
     * @param player The Player removing the ingredient.
     * @return Returns the ItemStack that will be given to the player.
     */
    ItemStack onRemoved(IFryingPan pan, ItemStack clicked, EntityPlayer player);

    boolean readyToCook(IFryingPan pan);
}
