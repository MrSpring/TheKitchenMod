package dk.mrspring.kitchen.config.wrapper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 02-01-2016 for TheKitchenMod.
 */
public class JsonCraftingRecipe
{
    String[] pattern;
    Map<Character, JsonItemStack> values;

    public JsonCraftingRecipe(String[] pattern, char[] chars, JsonItemStack[] stacks)
    {
        this();
        this.pattern = pattern;
        for (int i = 0; i < chars.length; i++) values.put(chars[i], stacks[i]);
    }

    public JsonCraftingRecipe()
    {
        pattern = new String[0];
        values = new LinkedHashMap<Character, JsonItemStack>();
    }

    public ShapedRecipes toRecipe(ItemStack output)
    {
        int w = 0, h = 0;
        for (String s : pattern)
        {
            w = s.length();
            h++;
        }
        if (w == 0 || h == 0) return null;
        ItemStack[] items = new ItemStack[w * h];
        boolean nonNull = false;
        for (int y = 0; y < h; y++)
        {
            String row = pattern[y];
            for (int x = 0; x < w; x++)
            {
                char charAt = row.charAt(x);
                if (charAt != ' ')
                    nonNull = ((items[y * w + x] = fromJsonStack(values.get(charAt))) != null) || nonNull;
            }
        }
        return nonNull ? new ShapedRecipes(w, h, items, output) : null;
    }

    public JsonCraftingRecipe copy()
    {
        JsonCraftingRecipe recipe = new JsonCraftingRecipe();
        recipe.pattern = this.pattern.clone();
        recipe.values = new LinkedHashMap<Character, JsonItemStack>(this.values);
        return recipe;
    }

    private ItemStack fromJsonStack(JsonItemStack stack)
    {
        return stack == null ? null : stack.toItemStack();
    }
}
