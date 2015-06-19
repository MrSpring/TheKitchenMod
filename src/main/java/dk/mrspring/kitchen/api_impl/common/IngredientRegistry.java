package dk.mrspring.kitchen.api_impl.common;

import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api.pan.IIngredientRegistry;
import dk.mrspring.kitchen.api_impl.common.ingredient.BasicIngredient;
import dk.mrspring.kitchen.api_impl.common.ingredient.JamIngredient;
import dk.mrspring.kitchen.api_impl.common.ingredient.RecipeIngredient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 25-03-2015.
 */
public class IngredientRegistry implements IIngredientRegistry
{
    private static final IngredientRegistry ourInstance = new IngredientRegistry();
    private static final IIngredient defaultIngredient = new BasicIngredient();
    private List<IIngredient> ingredients = new ArrayList<IIngredient>();

    private IngredientRegistry()
    {
        registerIngredient(new RecipeIngredient());
        registerIngredient(new JamIngredient());
    }

    public static IngredientRegistry getInstance()
    {
        return ourInstance;
    }

    @Override
    public void registerIngredient(IIngredient ingredient)
    {
        if (ingredient != null)
            ingredients.add(ingredient);
    }

    @Override
    public IIngredient getIngredientFor(IFryingPan fryingPan, ItemStack stack, EntityPlayer player)
    {
        for (IIngredient ingredient : ingredients)
            if (ingredient.isForItem(fryingPan, stack, player))
                return ingredient;
        return defaultIngredient;
    }

    @Override
    public IIngredient getIngredientFromName(String ingredientName)
    {
        for (IIngredient ingredient : ingredients)
            if (ingredient.getName().equals(ingredientName))
                return ingredient;
        return null; // Returns null cause it's not from an item.
    }

    //    private final Map<Stack, String> ingredientRelations = new TreeMap<Stack, String>();
    /*private final List<LinkedStack> ingredientRelations = new ArrayList<LinkedStack>();
    private final Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

    private IngredientRegistry()
    {
    }

    public static IngredientRegistry getInstance()
    {
        return ourInstance;
    }

    public static void registerItemPanRecipe(ItemStack output, String ingredientName, IIngredientRenderingHandler renderingHandler, Stack... inputs)
    {
        IngredientRegistry registry = getInstance();
        Ingredient ingredient = new Ingredient(ingredientName, renderingHandler, output);
        registry.registerIngredient(ingredient);
        for (Stack input : inputs)
        {
            LinkedStack linkedStack;
            if (input instanceof LinkedStack)
                linkedStack = (LinkedStack) input;
            else linkedStack = new LinkedStack(input, ingredientName);
            registry.linkToIngredient(linkedStack);
        }
    }

    public static void registerItemPanRecipe(ItemStack output, String ingredientName, IIngredientRenderingHandler renderingHandler, ItemStack... inputs)
    {
        Stack[] stacks = new Stack[inputs.length];
        for (int i = 0; i < inputs.length; i++)
        {
            ItemStack itemStack = inputs[i];
            stacks[i] = new Stack(itemStack);
        }
        registerItemPanRecipe(output, ingredientName, renderingHandler, stacks);
    }

    public static void registerItemPanRecipe(ItemStack output, IIngredientRenderingHandler renderingHandler, Stack... inputs)
    {
        String ingredientName = output.getUnlocalizedName();
        registerItemPanRecipe(output, ingredientName, renderingHandler, inputs);
    }

    public static void registerItemPanRecipe(ItemStack output, IIngredientRenderingHandler renderingHandler, ItemStack... inputs)
    {
        String ingredientName = output.getUnlocalizedName();
        registerItemPanRecipe(output, ingredientName, renderingHandler, inputs);
    }

    public static void registerItemPanRecipe(ItemStack output, String ingredientName, Item rawModel, Item cookedModel, Stack... inputs)
    {
        registerItemPanRecipe(output, ingredientName, new ItemBaseRenderingHandler(rawModel, cookedModel), inputs);
    }

    public static void registerItemPanRecipe(ItemStack output, String ingredientName, Item rawModel, Item cookedModel, ItemStack... inputs)
    {
        registerItemPanRecipe(output, ingredientName, new ItemBaseRenderingHandler(rawModel, cookedModel), inputs);
    }

    public static void registerItemPanRecipe(ItemStack output, Item rawModel, Item cookedModel, Stack... inputs)
    {
        registerItemPanRecipe(output, new ItemBaseRenderingHandler(rawModel, cookedModel), inputs);
    }

    public static void registerItemPanRecipe(ItemStack output, Item rawModel, Item cookedModel, ItemStack... inputs)
    {
        registerItemPanRecipe(output, new ItemBaseRenderingHandler(rawModel, cookedModel), inputs);
    }

    private static boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2)
    {
        return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    public List<LinkedStack> getIngredientRelations()
    {
        return ingredientRelations;
    }

    public Map<String, Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void registerIngredient(Ingredient ingredient)
    {
        this.registerIngredient(ingredient, ingredient.getName());
    }

    public void registerIngredient(Ingredient ingredient, String ingredientName)
    {
        if (ingredient != null)
            if (!ingredients.containsKey(ingredientName))
                ingredients.put(ingredientName, ingredient);
    }

    public Ingredient getIngredient(String name)
    {
        if (isIngredientRegistered(name))
            return ingredients.get(name);
        return ingredients.get("empty");
    }

    public Stack[] getInputs(Ingredient ingredient)
    {
        return getInputs(ingredient.getName());
    }

    public Stack[] getInputs(String ingredient)
    {
        List<Stack> found = new ArrayList<Stack>();
        for (LinkedStack stack : ingredientRelations)
            if (stack.linkedTo.equals(ingredient))
                found.add(stack);
        return found.toArray(new Stack[found.size()]);
    }

    public ItemStack[] getInputsAsItemStacks(String ingredient)
    {
        Stack[] stacks = getInputs(ingredient);
        ItemStack[] itemStacks = new ItemStack[stacks.length];
        for (int i = 0; i < stacks.length; i++)
            itemStacks[i] = stacks[i].toItemStack();
        return itemStacks;
    }

    public Ingredient getOutput(ItemStack item)
    {
        Stack fromItem = new Stack(item);
        return getOutput(fromItem);
    }

    public Ingredient getOutput(Stack stack)
    {
        for (LinkedStack linkedStack : ingredientRelations)
            if (stack.equals(linkedStack))
                return getIngredient(linkedStack.linkedTo);
        return null;
    }

    public void linkToIngredient(LinkedStack stack)
    {
        ingredientRelations.add(stack);
    }

    public void linkToIngredient(Stack stack, String ingredient)
    {
        linkToIngredient(new LinkedStack(stack, ingredient));
    }

    public void linkToIngredient(Stack stack, Ingredient ingredient)
    {
        linkToIngredient(stack, ingredient.getName());
    }

    public void linkToIngredient(Item item, String ingredient)
    {
        linkToIngredient(new Stack(item, -1), ingredient);
    }

    public void linkToIngredient(Item item, Ingredient ingredient)
    {
        linkToIngredient(item, ingredient.getName());
    }

    public Ingredient[] getInputsForItemStack(ItemStack itemStack)
    {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        if (itemStack.getItem() == KitchenItems.jam_jar)
        {
            if (itemStack.getItemDamage() <= 0)
                return new Ingredient[0];
            NBTTagCompound compound = itemStack.getTagCompound();
            if (compound == null)
                return new Ingredient[0];
            compound = compound.getCompoundTag("JamInfo");
            if (compound == null)
                return new Ingredient[0];
            String jamType = compound.getString("JamType");
            if (jamType != null && !jamType.equals(""))
            {
                for (Map.Entry<String, Ingredient> entry : ingredients.entrySet())
                {
                    Ingredient ingredient = entry.getValue();
                    if (ingredient.isJam() && ingredient.getJamResult().getName().equals(jamType))
                        ingredientList.add(ingredient);
                }
            }
        } else
        {
            for (Map.Entry<String, Ingredient> entry : ingredients.entrySet())
            {
                Ingredient ingredient = entry.getValue();
                if (!ingredient.isJam())
                    if (areItemStacksEqual(itemStack, ingredient.getItemResult()))
                        ingredientList.add(ingredient);
            }
        }
        return ingredientList.toArray(new Ingredient[ingredientList.size()]);
    }

    public boolean isIngredientRegistered(String ingredient)
    {
        return ingredient != null && this.getIngredients().containsKey(ingredient);
    }*/
}
