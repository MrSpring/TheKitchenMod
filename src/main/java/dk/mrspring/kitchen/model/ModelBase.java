package dk.mrspring.kitchen.model;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ModelBase<T extends IRenderParameter> extends net.minecraft.client.model.ModelBase
{
    public ResourceLocation texture;
    List<ModelPart> parts = Lists.newLinkedList();
    ModelPart basePart;

    public ModelBase(ResourceLocation texture, int textureWidth, int textureHeight, ModelPart... parts)
    {
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        Collections.addAll(this.parts, parts);
        this.basePart = new ModelPart(this);
    }

    public ModelBase(String texture, int textureWidth, int textureHeight, ModelPart... parts)
    {
        this(modelTexture(texture), textureWidth, textureHeight, parts);
    }

    public T makeDefaultParameter()
    {
        return null;
    }

    public RenderContext makeContext()
    {
        return makeContext(0F);
    }

    public RenderContext makeContext(float partial)
    {
        return new RenderContext(partial, makeDefaultParameter());
    }

    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
                          RenderContext context)
    {
    }

    public void simpleRender(float partial)
    {
        this.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F, partial);
    }

    public void simpleRender(float partial, T parameters)
    {
        this.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F, partial, parameters);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float partial)
    {
        this.render(entity, f, f1, f2, f3, f4, f5, makeContext(partial));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.render(entity, f, f1, f2, f3, f4, f5, makeContext());
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float partial,
                       T parameters)
    {
        RenderContext context = makeContext(partial);
        context.parameters = parameters;
        this.render(entity, f, f1, f2, f3, f4, f5, context);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
                       T parameters)
    {
        render(entity, f, f1, f2, f3, f4, f5, 0F, parameters);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        preRender(entity, f, f1, f2, f3, f4, f5, context);
        push();
        bind(getTexture(context));
        enableTextures();
        for (ModelRenderer renderer : parts) renderer.render(f5);
        basePart.render(f5);
        renderExtras(entity, f, f1, f2, f3, f4, f5, context);
        pop();
    }

    public void renderExtras(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
                             RenderContext context)
    {
    }

    public ResourceLocation getTexture(RenderContext context)
    {
        return this.texture;
    }

    /**
     * Renders an extra box when needed.
     *
     * @param f5 The last float in the {@link #render(Entity, float, float, float, float, float, float)} function.
     * @return Returns this model.
     */
    public ModelBase renderBox(int u, int v,
                               float xOffset, float yOffset, float zOffset,
                               int width, int height, int depth, float pivotX, float pivotY, float pivotZ,
                               float rotationX, float rotationY, float rotationZ, float f5)
    {
        ModelPart box = new ModelPart(this)
                .setPivot(pivotX, pivotY, pivotZ)
                .setRotation(rotationX, rotationY, rotationZ)
                .addBox(u, v, xOffset, yOffset, zOffset, width, height, depth);
        box.render(f5);
        return this;
    }

    /**
     * Renders an extra box when needed.
     *
     * @param f5 The last float in the {@link #render(Entity, float, float, float, float, float, float)} function.
     * @return Return this model.
     */
    public ModelBase renderBox(int u, int v,
                               float x, float y, float z,
                               int width, int height, int depth, float f5)
    {
        return this.renderBox(u, v, 0F, 0F, 0F, width, height, depth, x, y, z, 0F, 0F, 0F, f5);
    }

    public ModelBase addPart(ModelPart part)
    {
        this.parts.add(part);
        return this;
    }

    public ModelPart addPart()
    {
        return this.addPart(0F, 0F, 0F);
    }

    public ModelPart addPart(float xPivot, float yPivot, float zPivot)
    {
        return this.addPart(null, xPivot, yPivot, zPivot);
    }

    public ModelPart addPart(String name, float xPivot, float yPivot, float zPivot)
    {
        ModelPart part = new ModelPart(this, name)
                .setPivot(xPivot, yPivot, zPivot)
                .setTextureSize(textureWidth, textureHeight);
        parts.add(part);
        return part;
    }

    public ModelBase addBox(int u, int v, float x, float y, float z, int width, int height, int depth)
    {
        this.basePart.addBox(u, v, x, y, z, width, height, depth);
        return this;
    }

    public void setRotation(float x, float y, float z, int... indexes)
    {
        for (int i : indexes) setRotation(i, x, y, z);
    }

    public void setRotation(int index, float x, float y, float z)
    {
        setRotation(parts.get(index), x, y, z);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public ModelBase hideModels(int... indexes)
    {
        for (int i : indexes) hideModel(i);
        return this;
    }

    public ModelBase hideModel(int index)
    {
        return hideModel(parts.get(index));
    }

    public ModelBase hideModels(ModelPart... parts)
    {
        for (ModelPart part : parts) hideModel(part);
        return this;
    }

    public ModelBase hideModel(ModelPart part)
    {
        part.isHidden = true;
        return this;
    }

    public ModelBase showModels(int... indexes)
    {
        for (int i : indexes) showModel(i);
        return this;
    }

    public ModelBase showModel(int index)
    {
        parts.get(index).isHidden = false;
        return this;
    }

    public ModelBase showModels(ModelPart... parts)
    {
        for (ModelPart part : parts) showModel(part);
        return this;
    }

    public ModelBase showModel(ModelPart part)
    {
        part.isHidden = false;
        return this;
    }

    public class RenderContext
    {
        public final float xOffset, yOffset, zOffset;
        public final float xRotation, yRotation, zRotation;
        public final float xScale, yScale, zScale;
        public final float partial;
        public T parameters = null;

        public RenderContext()
        {
            this(0F);
        }

        public RenderContext(float partial)
        {
            this(0F, 0F, 0F, 0F, 0F, 0F, 1F, 1F, 1F, partial, null);
        }

        public RenderContext(float xOffset, float yOffset, float zOffset,
                             float xRotation, float yRotation, float zRotation,
                             float xScale, float yScale, float zScale,
                             float partial, T parameters)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.zOffset = zOffset;
            this.xRotation = xRotation;
            this.yRotation = yRotation;
            this.zRotation = zRotation;
            this.xScale = xScale;
            this.yScale = yScale;
            this.zScale = zScale;
            this.partial = partial;
            this.parameters = parameters;
        }

        public RenderContext(T parameters)
        {
            this(0F, parameters);
        }

        public RenderContext(float partial, T parameters)
        {
            this(0F, 0F, 0F, 0F, 0F, 0F, 1F, 1F, 1F, partial, parameters);
        }
    }
}