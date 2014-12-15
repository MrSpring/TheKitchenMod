package dk.mrspring.kitchen.config.wrapper;

import dk.mrspring.kitchen.recipe.BasicRecipe;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class JsonBasicRecipe
{
    String input, output;

    public boolean isValid()
    {
        if (input == null)
            return false;
        else if (input.isEmpty())
            return false;

        if (output == null)
            return false;
        else if (output.isEmpty())
            return false;

        return true;
    }

    public BasicRecipe toBasicRecipe()
    {
        return new BasicRecipe(this);
    }

    public String getInput()
    {
        return input;
    }

    public String getOutput()
    {
        return output;
    }
}
