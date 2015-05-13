package dk.mrspring.kitchen.api.stack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 13-05-2015.
 */
public class LinkedStack extends Stack
{
    public String linkedTo;

    public LinkedStack(Item item, int metadata, String linkedTo)
    {
        super(item, metadata);
        this.linkedTo = linkedTo;
    }

    public LinkedStack(ItemStack stack, String linkedTo)
    {
        super(stack);
        this.linkedTo = linkedTo;
    }

    public LinkedStack(Stack stack, String linkedTo)
    {
        super(stack.item, stack.metadata);
        this.linkedTo = linkedTo;
    }

    @Override
    public boolean equals(Object that)
    {
        if (that instanceof LinkedStack)
        {
            LinkedStack linkedStack = (LinkedStack) that;
            return super.equals(that) && this.linkedTo.equals(linkedStack.linkedTo);
        } else return super.equals(that);
    }
}
