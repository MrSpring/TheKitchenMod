package dk.mrspring.kitchen.config.wrapper;

import dk.mrspring.kitchen.recipe.BasicRecipe;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class JsonBasicRecipe
{
    JsonItemStack input, output;

    public JsonBasicRecipe(JsonItemStack in, JsonItemStack out)
    {
        input = in;
        output = out;
    }

    public JsonBasicRecipe(String input, String output)
    {
        this(new JsonItemStack(input), new JsonItemStack(output));
    }

    public BasicRecipe toBasicRecipe()
    {
        if (this.isValid())
            return new BasicRecipe(this);
        else return new BasicRecipe("", "");
    }

    private boolean isValid()
    {
        return this.input != null && this.output != null;
    }

    public JsonItemStack getInput()
    {
        return input;
    }

    public JsonItemStack getOutput()
    {
        return output;
    }
}
