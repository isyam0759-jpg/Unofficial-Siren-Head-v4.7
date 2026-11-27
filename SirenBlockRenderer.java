package com.sirenhead.mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sirenhead.mod.SirenHeadMod;
import com.sirenhead.mod.block.SirenBlocks;
import com.sirenhead.mod.block.SirenGeoBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

/** GeckoLib renderer for the add-on blocks (geometry and texture taken from the Bedrock block definitions). */
public class SirenBlockRenderer extends GeoBlockRenderer<SirenGeoBlockEntity> {
    public SirenBlockRenderer() {
        super(new Model());
    }

    /**
     * Same placement as the Bedrock block transformation of the head: scale 1.3, translated up 0.15.
     * (render(...) is deliberately not overridden: GeckoLib declares it with a BlockEntity parameter, which clashes with
     * BlockEntityRenderer<T>.render(T, ...) for a concrete T. GeckoLib sets the protected field "animatable" before this runs.)
     */
    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        super.rotateBlock(facing, poseStack);
        if (this.animatable != null && this.animatable.getBlockState().is(SirenBlocks.SIREN_HEAD_HEAD.get())) {
            poseStack.translate(0, 0.15, 0);
            poseStack.scale(1.3f, 1.3f, 1.3f);
        }
    }

    private static final class Model extends GeoModel<SirenGeoBlockEntity> {
        private static final ResourceLocation HEAD_GEO = new ResourceLocation(SirenHeadMod.MOD_ID, "geo/siren_head_head_bd.geo.json");
        private static final ResourceLocation HEAD_TEX = new ResourceLocation(SirenHeadMod.MOD_ID, "textures/blocks/siren_head_head_bd.png");
        private static final ResourceLocation LIGHT_GEO = new ResourceLocation(SirenHeadMod.MOD_ID, "geo/bd_light.geo.json");
        private static final ResourceLocation LIGHT_TEX = new ResourceLocation(SirenHeadMod.MOD_ID, "textures/blocks/bd_light.png");
        private static final ResourceLocation NONE = new ResourceLocation(SirenHeadMod.MOD_ID, "animations/none.animation.json");

        private static boolean isHead(SirenGeoBlockEntity be) {
            return be.getBlockState().is(SirenBlocks.SIREN_HEAD_HEAD.get());
        }

        @Override
        public ResourceLocation getModelResource(SirenGeoBlockEntity be) {
            return isHead(be) ? HEAD_GEO : LIGHT_GEO;
        }

        @Override
        public ResourceLocation getTextureResource(SirenGeoBlockEntity be) {
            return isHead(be) ? HEAD_TEX : LIGHT_TEX;
        }

        @Override
        public ResourceLocation getAnimationResource(SirenGeoBlockEntity be) {
            return NONE;
        }
    }
}
