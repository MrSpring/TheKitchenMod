package dk.mrspring.kitchen.api.grinder;

import dk.mrspring.kitchen.api.ISpecialTagInfo;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 02-06-2015.
 */
public interface IGrinder extends ISpecialTagInfo
{
    /**
     * @return Returns the current opening that is one the grinder. Null if none is on.
     */
    GrinderOpening getOpening();

    /**
     * @return Returns the ItemStack that is currently being progressed. Null is none is being progressed.
     */
    ItemStack getProgressingItem();

    /**
     * @return Returns how far the current item is through being processed.
     */
    int getProgress();

    /**
     * @return Returns how many times the grinder has to be spun to finish the current item.
     */
    int getDoneProgress();
}
