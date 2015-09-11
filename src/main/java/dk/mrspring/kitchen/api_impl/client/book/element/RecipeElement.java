package dk.mrspring.kitchen.api_impl.client.book.element;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.book.IPageElementContainer;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created on 10-09-2015 for TheKitchenMod.
 */
public class RecipeElement extends ImageElement
{
    ItemStack[] recipe; // TODO: Do NEI-like item switching for alt. items
    ItemStack output;

    public RecipeElement(ItemStack output)
    {
        super(ModInfo.toResource("textures/gui/cooking_book.png"), 0, 0, 99, 62);
        List recipes = CraftingManager.getInstance().getRecipeList();
        for (Object o : recipes)
            if (o != null)
                if (o instanceof IRecipe)
                {
                    ItemStack result = ((IRecipe) o).getRecipeOutput();
                    if (ItemUtils.areStacksEqual(result, output, true))
                    {
                        this.loadFromRecipe((IRecipe) o);
                        System.out.println("load");
                        return;
                    }
                }
        this.recipe = new ItemStack[9];
    }

    private void loadFromRecipe(IRecipe recipe)
    {
        System.out.println(recipe.getClass().getSimpleName());

        if (recipe instanceof ShapedRecipes)
            this.loadFromRecipe((ShapedRecipes) recipe);
        else if (recipe instanceof ShapelessRecipes)
            this.loadFromRecipe((ShapelessRecipes) recipe);
        else if (recipe instanceof ShapelessOreRecipe)
            this.loadFromRecipe((ShapelessOreRecipe) recipe);
        else if (recipe instanceof ShapedOreRecipe)
            this.loadFromRecipe((ShapedOreRecipe) recipe);
    }

    private void loadFromRecipe(ShapedOreRecipe recipe)
    {
        this.output = recipe.getRecipeOutput();
        this.loadFromArray(recipe.getInput());
    }

    private void loadFromRecipe(ShapelessOreRecipe recipe)
    {
        this.output = recipe.getRecipeOutput();
        this.loadFromList(recipe.getInput());
    }

    private void loadFromRecipe(ShapelessRecipes recipe)
    {
        this.output = recipe.getRecipeOutput();
        this.loadFromList(recipe.recipeItems);
    }

    private void loadFromRecipe(ShapedRecipes recipe)
    {
        this.output = recipe.getRecipeOutput();
        this.recipe = new ItemStack[9];
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                this.recipe[y * 3 + x] = getFromRecipe(x, y, recipe);
    }

    private void loadFromArray(Object[] array)
    {
        this.recipe = new ItemStack[9];
        for (int i = 0; i < array.length && i < this.recipe.length; i++)
        {
            Object o = array[i];
            if (o != null)
            {
                System.out.println(o.getClass().getSimpleName());
                if (o instanceof ItemStack)
                    this.recipe[i] = (ItemStack) o;
                else if (o instanceof String)
                    this.recipe[i] = OreDictionary.getOres((String) o).get(0);
                else if (o instanceof Item)
                    this.recipe[i] = new ItemStack((Item) o);
                else if (o instanceof Block)
                    this.recipe[i] = new ItemStack((Block) o);
                else if (o instanceof List)
                    this.recipe[i] = (ItemStack) ((List) o).get(0);
            }
        }
    }

    private void loadFromList(List recipe)
    {
        this.loadFromArray(recipe.toArray());
    }

    private ItemStack getFromRecipe(int x, int y, ShapedRecipes recipe)
    {
        if (x >= recipe.recipeWidth || y >= recipe.recipeHeight) return null;
        else return recipe.recipeItems[y * recipe.recipeWidth + x];
    }

    @Override
    public void initElement(IPageElementContainer container)
    {
        super.initElement(container);

        for (ItemStack stack : recipe)
            System.out.println(ItemUtils.name(stack));
    }

    @Override
    public void render(IPageElementContainer container)
    {
        super.render(container);

        Minecraft mc = container.getMinecraft();
        RenderItem render = container.getRenderItem();

        GL11.glPushMatrix();
        GL11.glTranslated(14F, 7F, 0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1, 1, 1, 1);
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
            {
                GL11.glPushMatrix();
                ItemStack stack = recipe[y * 3 + x];
                if (stack != null)
                    render.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, x * 16, y * 16);
                GL11.glPopMatrix();
            }

        GL11.glTranslatef(67F, 16F, 0F);
        render.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), output, 0, 0);

        GL11.glPopMatrix();
    }
}
