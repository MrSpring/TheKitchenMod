package dk.mrspring.kitchen.api.stack;

import dk.mrspring.kitchen.KitchenItems;

/**
 * Created by Konrad on 19-06-2015.
 */
public class JamJarStack extends Stack
{
    String jam;
    int usesLeft;

    public JamJarStack(String jam, int usesLeft)
    {
        super(KitchenItems.jam_jar, usesLeft >= 1 ? 1 : 0, 1);
        this.jam = jam;
        this.usesLeft = usesLeft;
    }

    @Override
    public boolean areStacksEqual(Stack that, Type... types)
    {
        if (that instanceof JamJarStack)
        {
            JamJarStack jarStack = (JamJarStack) that;
            return jarStack.usesLeft == this.usesLeft && jarStack.jam.equals(this.jam) && super.areStacksEqual(that, types);
        } else return super.areStacksEqual(that, types);
    }

    @Override
    public String toString()
    {
        return super.toString() + ":" + jam + "x" + usesLeft;
    }
}
