package dk.mrspring.kitchen.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlate extends ModelBase
{
  //fields
    ModelRenderer Top16;
    ModelRenderer Top15;
    ModelRenderer Top14;
    ModelRenderer Top13;
    ModelRenderer Top12;
    ModelRenderer Top11;
    ModelRenderer Top10;
    ModelRenderer Top9;
    ModelRenderer Bottom0;
    ModelRenderer Top8;
    ModelRenderer Top7;
    ModelRenderer Top6;
    ModelRenderer Top5;
    ModelRenderer Top4;
    ModelRenderer Top3;
    ModelRenderer Top2;
    ModelRenderer Bottom4;
    ModelRenderer Top1;
    ModelRenderer Bottom3;
    ModelRenderer Bottom2;
    ModelRenderer Bottom1;
  
  public ModelPlate()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Top16 = new ModelRenderer(this, 44, 11);
      Top16.addBox(0F, 0F, 0F, 6, 1, 3);
      Top16.setRotationPoint(-3F, 22F, 4F);
      Top16.setTextureSize(64, 32);
      Top16.mirror = true;
      setRotation(Top16, 0F, 0F, 0F);
      Top15 = new ModelRenderer(this, 16, 10);
      Top15.addBox(0F, 0F, 0F, 2, 1, 1);
      Top15.setRotationPoint(-5F, 22F, 5F);
      Top15.setTextureSize(64, 32);
      Top15.mirror = true;
      setRotation(Top15, 0F, 0F, 0F);
      Top14 = new ModelRenderer(this, 24, 13);
      Top14.addBox(0F, 0F, 0F, 3, 1, 6);
      Top14.setRotationPoint(-7F, 22F, -3F);
      Top14.setTextureSize(64, 32);
      Top14.mirror = true;
      setRotation(Top14, 0F, 0F, 0F);
      Top13 = new ModelRenderer(this, 44, 16);
      Top13.addBox(0F, 0F, 0F, 6, 1, 3);
      Top13.setRotationPoint(-3F, 22F, -7F);
      Top13.setTextureSize(64, 32);
      Top13.mirror = true;
      setRotation(Top13, 0F, 0F, 0F);
      Top12 = new ModelRenderer(this, 16, 21);
      Top12.addBox(0F, 0F, 0F, 1, 1, 2);
      Top12.setRotationPoint(-6F, 22F, -5F);
      Top12.setTextureSize(64, 32);
      Top12.mirror = true;
      setRotation(Top12, 0F, 0F, 0F);
      Top11 = new ModelRenderer(this, 16, 13);
      Top11.addBox(0F, 0F, 0F, 2, 1, 1);
      Top11.setRotationPoint(3F, 22F, -6F);
      Top11.setTextureSize(64, 32);
      Top11.mirror = true;
      setRotation(Top11, 0F, 0F, 0F);
      Top10 = new ModelRenderer(this, 16, 17);
      Top10.addBox(0F, 0F, 0F, 1, 1, 2);
      Top10.setRotationPoint(5F, 22F, 3F);
      Top10.setTextureSize(64, 32);
      Top10.mirror = true;
      setRotation(Top10, 0F, 0F, 0F);
      Top9 = new ModelRenderer(this, 24, 5);
      Top9.addBox(0F, 0F, 0F, 3, 1, 6);
      Top9.setRotationPoint(4F, 22F, -3F);
      Top9.setTextureSize(64, 32);
      Top9.mirror = true;
      setRotation(Top9, 0F, 0F, 0F);
      Bottom0 = new ModelRenderer(this, 0, 25);
      Bottom0.addBox(0F, 0F, 0F, 6, 1, 6);
      Bottom0.setRotationPoint(-3F, 22.5F, -3F);
      Bottom0.setTextureSize(64, 32);
      Bottom0.mirror = true;
      setRotation(Bottom0, 0F, 0F, 0F);
      Top8 = new ModelRenderer(this, 9, 17);
      Top8.addBox(0F, 0F, 0F, 1, 1, 2);
      Top8.setRotationPoint(5F, 22F, -5F);
      Top8.setTextureSize(64, 32);
      Top8.mirror = true;
      setRotation(Top8, 0F, 0F, 0F);
      Top7 = new ModelRenderer(this, 9, 10);
      Top7.addBox(0F, 0F, 0F, 2, 1, 1);
      Top7.setRotationPoint(3F, 22F, 5F);
      Top7.setTextureSize(64, 32);
      Top7.mirror = true;
      setRotation(Top7, 0F, 0F, 0F);
      Top6 = new ModelRenderer(this, 9, 21);
      Top6.addBox(0F, 0F, 0F, 1, 1, 2);
      Top6.setRotationPoint(-6F, 22F, 3F);
      Top6.setTextureSize(64, 32);
      Top6.mirror = true;
      setRotation(Top6, 0F, 0F, 0F);
      Top5 = new ModelRenderer(this, 9, 13);
      Top5.addBox(0F, 0F, 0F, 2, 1, 1);
      Top5.setRotationPoint(-5F, 22F, -6F);
      Top5.setTextureSize(64, 32);
      Top5.mirror = true;
      setRotation(Top5, 0F, 0F, 0F);
      Top4 = new ModelRenderer(this, 0, 13);
      Top4.addBox(0F, 0F, 0F, 2, 1, 2);
      Top4.setRotationPoint(-5F, 22F, -5F);
      Top4.setTextureSize(64, 32);
      Top4.mirror = true;
      setRotation(Top4, 0F, 0F, 0F);
      Top3 = new ModelRenderer(this, 0, 9);
      Top3.addBox(0F, 0F, 0F, 2, 1, 2);
      Top3.setRotationPoint(3F, 22F, -5F);
      Top3.setTextureSize(64, 32);
      Top3.mirror = true;
      setRotation(Top3, 0F, 0F, 0F);
      Top2 = new ModelRenderer(this, 0, 21);
      Top2.addBox(0F, 0F, 0F, 2, 1, 2);
      Top2.setRotationPoint(3F, 22F, 3F);
      Top2.setTextureSize(64, 32);
      Top2.mirror = true;
      setRotation(Top2, 0F, 0F, 0F);
      Bottom4 = new ModelRenderer(this, 25, 22);
      Bottom4.addBox(0F, 0F, 0F, 6, 1, 1);
      Bottom4.setRotationPoint(-3F, 22.5F, -4F);
      Bottom4.setTextureSize(64, 32);
      Bottom4.mirror = true;
      setRotation(Bottom4, 0F, 0F, 0F);
      Top1 = new ModelRenderer(this, 0, 17);
      Top1.addBox(0F, 0F, 0F, 2, 1, 2);
      Top1.setRotationPoint(-5F, 22F, 3F);
      Top1.setTextureSize(64, 32);
      Top1.mirror = true;
      setRotation(Top1, 0F, 0F, 0F);
      Bottom3 = new ModelRenderer(this, 25, 25);
      Bottom3.addBox(0F, 0F, 0F, 1, 1, 6);
      Bottom3.setRotationPoint(-4F, 22.5F, -3F);
      Bottom3.setTextureSize(64, 32);
      Bottom3.mirror = true;
      setRotation(Bottom3, 0F, 0F, 0F);
      Bottom2 = new ModelRenderer(this, 40, 25);
      Bottom2.addBox(0F, 0F, 0F, 1, 1, 6);
      Bottom2.setRotationPoint(3F, 22.5F, -3F);
      Bottom2.setTextureSize(64, 32);
      Bottom2.mirror = true;
      setRotation(Bottom2, 0F, 0F, 0F);
      Bottom1 = new ModelRenderer(this, 40, 22);
      Bottom1.addBox(0F, 0F, 0F, 6, 1, 1);
      Bottom1.setRotationPoint(-3F, 22.5F, 3F);
      Bottom1.setTextureSize(64, 32);
      Bottom1.mirror = true;
      setRotation(Bottom1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Top16.render(f5);
    Top15.render(f5);
    Top14.render(f5);
    Top13.render(f5);
    Top12.render(f5);
    Top11.render(f5);
    Top10.render(f5);
    Top9.render(f5);
    Bottom0.render(f5);
    Top8.render(f5);
    Top7.render(f5);
    Top6.render(f5);
    Top5.render(f5);
    Top4.render(f5);
    Top3.render(f5);
    Top2.render(f5);
    Bottom4.render(f5);
    Top1.render(f5);
    Bottom3.render(f5);
    Bottom2.render(f5);
    Bottom1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
