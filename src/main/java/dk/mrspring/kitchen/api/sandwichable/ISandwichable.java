package dk.mrspring.kitchen.api.sandwichable;

import dk.mrspring.kitchen.api.stack.Stack;

/**
 * Created by Konrad on 13-05-2015.
 */
public interface ISandwichable
{
    int getHealAmount();

    ISandwichable setHealAmount(int healAmount);

    Stack getStack();

    ISandwichable setStack(Stack stack);

    boolean getIsBread();

    ISandwichable setIsBread(boolean isBread);

    boolean getHideInformation();

    ISandwichable setHideInformation(boolean hideInformation);

    boolean getShowInformation();

    ISandwichable setShowInformation(boolean showInformation);

    boolean getDropItem();

    ISandwichable setDropItem(boolean dropItem);
}
