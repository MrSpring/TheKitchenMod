package dk.mrspring.kitchen.api.ingredient;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.pan.ItemBaseRenderingHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 25-03-2015.
 */
public class IngredientRegistry
{
    private static IngredientRegistry ourInstance = new IngredientRegistry();

    public static IngredientRegistry getInstance()
    {
        return ourInstance;
    }

    private IngredientRegistry()
    {
    }

    //    private final Map<Stack, String> ingredientRelations = new TreeMap<Stack, String>();
    private final List<LinkedStack> ingredientRelations = new ArrayList<LinkedStack>();
    private final Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();

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
        if (name != null)
            if (ingredients.containsKey(name))
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
            if (stack.ingredient.equals(ingredient))
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
                return getIngredient(linkedStack.ingredient);
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

    public static class LinkedStack extends Stack
    {
        public String ingredient;

        public LinkedStack(Item item, int metadata, String ingredient)
        {
            super(item, metadata);
            this.ingredient = ingredient;
        }

        public LinkedStack(ItemStack stack, String ingredient)
        {
            super(stack);
            this.ingredient = ingredient;
        }

        public LinkedStack(Stack stack, String ingredient)
        {
            super(stack.item, stack.metadata);
            this.ingredient = ingredient;
        }

        @Override
        public boolean equals(Object that)
        {
            if (that instanceof LinkedStack)
            {
                LinkedStack linkedStack = (LinkedStack) that;
                return super.equals(that) && this.ingredient.equals(linkedStack.ingredient);
            } else return super.equals(that);
        }
    }

    public static class MixingBowlStack extends LinkedStack
    {
        public String mixType;

        public MixingBowlStack(String mixType, String ingredient)
        {
            super(KitchenItems.mixing_bowl, -1, ingredient);
            this.mixType = mixType;
        }

        @Override
        public ItemStack toItemStack()
        {
            return Kitchen.getMixingBowlStack(mixType, 3);
        }
    }

    public static class Stack
    {
        public int metadata;
        public Item item;

        /**
         * @param item     The item associated with this Stack.
         * @param metadata The metadata associated with this Stack. Use -1 if any can be used.
         */
        public Stack(Item item, int metadata)
        {
            this.metadata = metadata;
            this.item = item;
        }

        /**
         * Creates a Stack based on an {@link net.minecraft.item.ItemStack ItemStack}.
         *
         * @param stack The {@link net.minecraft.item.ItemStack ItemStack} to copy data from.
         */
        public Stack(ItemStack stack)
        {
            this(stack, true);
        }

        /**
         * Creates a Stack based on an {@link net.minecraft.item.ItemStack ItemStack}.
         *
         * @param stack        The {@link net.minecraft.item.ItemStack ItemStack} to copy data from.
         * @param copyMetadata Whether the metadata from stack should be used. If false any will be accepted.
         */
        public Stack(ItemStack stack, boolean copyMetadata)
        {
            this(stack.getItem(), copyMetadata ? stack.getItemDamage() : -1);
        }

        @Override
        public String toString()
        {
            return item + ":" + String.valueOf(metadata);
        }

        /**
         * @return Returns an ItemStack with the Stack's item and metadata, with a stackSize of 1.
         */
        public ItemStack toItemStack()
        {
            return new ItemStack(item, 1, metadata);
        }

        @Override
        public boolean equals(Object that)
        {
            if (that instanceof Stack)
            {
                System.out.println("Comparing: \"" + this.toString() + "\" to: \"" + that.toString() + "\"");
                Stack objStack = (Stack) that;
                return ((objStack.metadata == -1 || this.metadata == -1) || objStack.metadata == this.metadata) &&
                        objStack.item == this.item;
            } else return super.equals(that);
        }
    }

    private static boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2)
    {
        return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }
}
