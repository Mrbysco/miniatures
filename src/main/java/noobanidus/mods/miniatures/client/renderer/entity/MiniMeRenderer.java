package noobanidus.mods.miniatures.client.renderer.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import noobanidus.mods.miniatures.client.ModelHolder;
import noobanidus.mods.miniatures.client.model.MiniMeModel;
import noobanidus.mods.miniatures.client.renderer.layers.ArrowRenderTypeLayer;
import noobanidus.mods.miniatures.client.renderer.layers.BeeStingerRenderTypeLayer;
import noobanidus.mods.miniatures.entity.MiniMeEntity;
import noobanidus.mods.miniatures.util.NoobUtil;

import javax.annotation.Nonnull;
import java.util.Map;

public class MiniMeRenderer extends BipedRenderer<MiniMeEntity, MiniMeModel<MiniMeEntity>> {
  private static final ResourceLocation TEXTURE_STEVE = new ResourceLocation("textures/entity/steve.png");

  @SuppressWarnings("unchecked")
  public MiniMeRenderer(EntityRendererManager renderManager, MiniMeModel model, float shadow) {
    super(renderManager, model, shadow);
    this.addLayer(new HeldItemLayer<>(this));
    this.addLayer(new ArrowRenderTypeLayer<>(this));
    this.addLayer(new HeadLayer<>(this));
    this.addLayer(new ElytraLayer<>(this));
    this.addLayer(new BeeStingerRenderTypeLayer<>(this));
    this.addLayer(new BipedArmorLayer<>(this, new BipedModel(1.02F), new BipedModel(1.02F)));
  }

  @Override
  public ResourceLocation getTextureLocation(MiniMeEntity entity) {
    return entity.getGameProfile()
        .map(this::getSkin)
        .orElse(TEXTURE_STEVE);
  }

  private ResourceLocation getSkin(GameProfile gameProfile) {
    if (!gameProfile.isComplete()) {
      return TEXTURE_STEVE;
    } else {
      final Minecraft minecraft = Minecraft.getInstance();
      SkinManager skinManager = minecraft.getSkinManager();
      final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache = skinManager.getInsecureSkinInformation(gameProfile); // returned map may or may not be typed
      if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
        return skinManager.registerTexture(loadSkinFromCache.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
      } else {
        return DefaultPlayerSkin.getDefaultSkin(gameProfile.getId());
      }
    }
  }

  @Override
  public void render(MiniMeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
    this.model = ModelHolder.miniMe;
    if (entityIn.isSlim() && this.model != ModelHolder.miniMeSlim) {
      this.model = ModelHolder.miniMeSlim;
    }
    int noob = entityIn.getNoobVariant();
    if (noob == 3) {
      packedLightIn = 15728880;
      this.model = ModelHolder.miniMeGhost;
      if (entityIn.isSlim() && this.model != ModelHolder.miniMeGhostSlim) {
        this.model = ModelHolder.miniMeGhostSlim;
      }
    } else if (noob == 4) {
      packedLightIn = 15728880;
      this.model = ModelHolder.miniMeGlow;
      if (entityIn.isSlim() && this.model != ModelHolder.miniMeGlowSlim) {
        this.model = ModelHolder.miniMeGlowSlim;
      }
    }
    super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
  }



  protected void scale(MiniMeEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
    float scale = entitylivingbaseIn.getMiniScale();
    if (NoobUtil.isNoob(entitylivingbaseIn)) {
      matrixStackIn.scale(1.0975F * scale, 1.0975F * scale, 1.0975F * scale);
    } else {
      matrixStackIn.scale(0.9375F * scale, 0.9375F * scale, 0.9375F * scale);
    }
  }

  @Override
  protected void setupRotations(MiniMeEntity entityLiving, MatrixStack matrixStackIn, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
    super.setupRotations(entityLiving, matrixStackIn, pAgeInTicks, pRotationYaw, pPartialTicks);
    int noob = entityLiving.getNoobVariant();
    if (noob == 0) {
      matrixStackIn.translate(0.0D, (double) (entityLiving.getBbHeight() + 0.3F), 0.0D);
      matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
    } else if (noob == 1) {
      matrixStackIn.translate(0.0D, 0.5F, 0.0D);
    }
  }

  public static class Factory implements IRenderFactory<MiniMeEntity> {
    @Override
    @Nonnull
    public MiniMeRenderer createRenderFor(@Nonnull EntityRendererManager manager) {
      return new MiniMeRenderer(manager, ModelHolder.miniMe, 0.5f);
    }
  }
}
