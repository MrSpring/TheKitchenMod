package dk.mrspring.kitchen.api.stack;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */
public class LinkedMixingBowlStack extends LinkedStack
{
    /**
     * Set usesLeft to this and the stack will equal any other MixingBowlStack that has more than 0 uses left.
     */
    public static final int NOT_EMPTY = -2;

    /**
     * The Mix that the mixing bowl is filled up with.
     */
    public String mixType;

    /**
     * @param mixType  The MixType this mixing bowl is filled with.
     * @param usesLeft How many times the mixing bowl can be used before becoming empty. Set to #NOT_EMPTY to accept
     *                 any other mixing bowl as long as they have at least one use left.
     */
    public LinkedMixingBowlStack(String mixType, int usesLeft, String linkedTo)
    {
        super(KitchenItems.mixing_bowl, usesLeft, linkedTo);
        this.mixType = mixType;
    }

    /**
     * @return Returns an ItemStack with the same mix type as this MixingBowlStack.
     */
    @Override
    public ItemStack toItemStack()
    {
        return Kitchen.getMixingBowlStack(mixType, Math.min(0, metadata));
    }

    @Override
    public boolean equals(Object that)
    {
        if (that instanceof LinkedMixingBowlStack && ((LinkedMixingBowlStack) that).linkedTo.equals(this.linkedTo))
        {
            LinkedMixingBowlStack stack = (LinkedMixingBowlStack) that;
            if (stack.metadata == NOT_EMPTY && this.metadata == NOT_EMPTY)
                return true;
            else if (this.metadata == NOT_EMPTY)
                return stack.metadata > 0;
            else if (stack.metadata == NOT_EMPTY)
                return this.metadata > 0;
            else return false;
        } else return false;
    }

    @Override
    public String toString()
    {
        return super.toString() + ":" + this.mixType;
    }
}
