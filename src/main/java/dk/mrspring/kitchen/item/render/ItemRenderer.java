package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.config.ClientConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created on 31-05-2016 for TheKitchenMod.
 */
public abstract class ItemRenderer implements IItemRenderer
{
    boolean isBlock = false;
    boolean animateDropped = true;

    public ItemRenderer renderLikeBlock()
    {
        isBlock = true;
        return this;
    }

    public ItemRenderer disableDroppedAnimation()
    {
        animateDropped = false;
        return this;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return getRenderConfig().handleType(type);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case ENTITY_ROTATION:
            case ENTITY_BOBBING:
                return animateDropped;
            case INVENTORY_BLOCK:
            case BLOCK_3D:
            case EQUIPPED_BLOCK:
                return isBlock;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        push();
        switch (type)
        {
            case ENTITY:
                renderEntity(type, item, data);
                break;
            case EQUIPPED:
                renderEquipped(type, item, data);
                break;
            case EQUIPPED_FIRST_PERSON:
                renderEquippedFirstPerson(type, item, data);
                break;
            case INVENTORY:
                renderInventory(type, item, data);
                break;
        }
        this.renderAnyTypes(item, data);
        pop();
    }

    public void renderEntity(ItemRenderType type, ItemStack stack, Object... data)
    {
        if (isBlock) translateEntityBlock(stack, data);
    }

    public void translateEntityBlock(ItemStack stack, Object... data)
    {
        translate(0F, 1F, 0F);
        rotate(180F, 1F, 0F, 0F);
    }

    public void renderEquipped(ItemRenderType type, ItemStack stack, Object... data)
    {
        if (isBlock) translateBlock(type, stack, data);
    }

    public void renderEquippedFirstPerson(ItemRenderType type, ItemStack stack, Object... data)
    {
        if (isBlock) translateBlock(type, stack, data);
    }

    public void translateBlock(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0.5F, 1.5F, 0.5F);
        rotate(180F, 1F, 0F, 0F);
    }

    public void renderInventory(ItemRenderType type, ItemStack stack, Object... data)
    {
        if (isBlock) translateInventoryBlock(stack, data);
    }

    public void translateInventoryBlock(ItemStack stack, Object... data)
    {
        rotate(180F, 1F, 0F, 0F);
        translate(0F, -1F, 0F);
    }

    public void centerRotate(float angle, float x, float y, float z)
    {
        translate(0.5F, 0.5F, 0.5F);
        rotate(angle, x, y, z);
        translate(-0.5F, -0.5F, -0.5F);
    }

    public void centerScale(float scale)
    {
        translate(0.5F, 0F, 0.5F);
        scale(scale);
        translate(-0.5F, 0F, -0.5F);
    }

    public abstract void renderAnyTypes(ItemStack item, Object... data);

    public abstract ClientConfig.RenderConfig getRenderConfig();

/*
    ModelOven model = new ModelOven();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/oven.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return ClientProxy.clientConfig.oven_rendering.handleType(type);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType renderType, ItemStack item, Object... data)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(25, 0F, 0F, 1F);
                GL11.glTranslatef(.5F, .66F, .13F);
                GL11.glRotatef(40, 0, 1, 0);
                float scale = 0.8F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(.5F, -.2F, .5F);
                break;
            case EQUIPPED:
                GL11.glRotatef(25F, 0F, 0F, 1F);
                GL11.glRotatef(40, 0, 1, 0);
                GL11.glTranslatef(0.6F, 0.55F, 0.6F);
                scale = 0.7F;
                GL11.glScalef(scale, scale, scale);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                scale = 1F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1F, 0F);
                break;
            case ENTITY:
                scale = 0.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                GL11.glTranslatef(0F, 0F, -0.1F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.simpleRender(0F);
        GL11.glPopMatrix();
    }
*/
}
