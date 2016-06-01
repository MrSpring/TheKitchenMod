package dk.mrspring.kitchen.pan;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.tileentity.TileEntityPan;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 19-10-2014 for TheKitchenMod.
 */
public class Ingredient
{
    public static final Ingredient EMPTY = new Ingredient("empty", "empty", (ItemStack) null);

    public static Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    static
    {
        ingredients.put("empty", EMPTY);
    }

    @SideOnly(Side.CLIENT)
    IIngredientRenderingHandler renderingHandler;
    String name;
    String unlocalizedName;
    ItemStack result;

    public Ingredient(String name, String unlocalizedName, String jamResult)
    {
        this(name, unlocalizedName, ItemJamJar.getJamJarItemStack(jamResult, 6));
    }

    public Ingredient(String name, String unlocalizedName, Item item)
    {
        this(name, unlocalizedName, new ItemStack(item));
    }

    public Ingredient(String name, String unlocalizedName, ItemStack result)
    {
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.result = result;
    }

    @SideOnly(Side.CLIENT)
    public Ingredient setRenderingHandler(IIngredientRenderingHandler handler)
    {
        this.renderingHandler = handler;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public IIngredientRenderingHandler getRenderingHandler()
    {
        return this.renderingHandler;
    }

    public boolean canRemove(TileEntityPan pan, ItemStack clicked)
    {
        if (!pan.isFinished()) return false;
        if (ItemJamJar.isJar(result))
            return ItemJamJar.isEmptyJar(clicked);
        else return clicked == null;
    }

    public ItemStack[] onRemoved(TileEntityPan pan, ItemStack clicked)
    {
        if (clicked != null && clicked.stackSize > 0) clicked.stackSize--;
        return new ItemStack[]{result.copy()};
    }

    public String getName()
    {
        return name;
    }

    public static void registerIngredient(Ingredient ingredient)
    {
        if (ingredient != null)
            if (!ingredients.containsKey(ingredient.getName()))
                ingredients.put(ingredient.getName(), ingredient);
    }

    public static Ingredient getIngredient(String name)
    {
        if (name != null)
            if (ingredients.containsKey(name))
                return ingredients.get(name);
        return EMPTY;
    }

    public static void bindRenderingHandler(String name, IIngredientRenderingHandler handler)
    {
        Ingredient ingredient = getIngredient(name);
        if (ingredient != EMPTY) ingredient.setRenderingHandler(handler);
    }

    public String getLocalizedName()
    {
        return I18n.format(unlocalizedName);
    }

    /*final String name;
    final IIngredientRenderingHandler renderingHandler;
    final boolean isJam;
    final String jResult;
    final ItemStack iResult;

    public Ingredient(String name, IIngredientRenderingHandler handler, boolean jam, String jamResult, ItemStack itemResult)
    {
        this.name = name;
        this.renderingHandler = handler;
        this.isJam = jam;
        this.jResult = jamResult;
        this.iResult = itemResult;
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, ItemStack iResult)
    {
        this(name, handler, false, "empty", iResult);
    }

    public Ingredient(String name, IIngredientRenderingHandler handler, String jResult)
    {
        this(name, handler, true, jResult, null);
    }

    public String getLocalizedName()
    {
        *//*if (this.isJam())
        {
            if (StatCollector.canTranslate("jam." + this.getJamResult().getName() + ".name"))
                return StatCollector.translateToLocal("jam." + this.getJamResult().getName() + ".name");
            else return this.getJamResult().getName();
        } else
            return this.getItemResult().getDisplayName();*//*
    }

    public static void registerIngredient(Ingredient ingredient)
    {
        if (ingredient != null)
            if (!ingredients.containsKey(ingredient.getName()))
                ingredients.put(ingredient.getName(), ingredient);
    }

    public static Ingredient getIngredient(String name)
    {
        if (name != null)
            if (ingredients.containsKey(name))
                return ingredients.get(name);
        return ingredients.get("empty");
    }

    public String getName()
    {
        return name;
    }

    public IIngredientRenderingHandler getRenderingHandler()
    {
        return this.renderingHandler;
    }

    *//*public boolean isJam()
    {
        return isJam;
    }

    public Jam getJamResult()
    {
        return Jam.getJam(this.jResult);
    }

    public ItemStack getItemResult()
    {
        return iResult.copy();
    }*/
}
