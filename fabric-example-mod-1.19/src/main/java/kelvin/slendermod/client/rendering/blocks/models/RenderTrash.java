package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.CustomFacingBlock;
import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import kelvin.slendermod.common.blocks.entities.TrashBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderTrash extends GeoBlockRenderer<TrashBlockEntity> {

    public RenderTrash() {
        super(new ModelTrash());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, TrashBlockEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.push();

        var facing = animatable.getCachedState().get(CustomFacingBlock.FACING);

        poseStack.translate(0.5f, 0, 0.5f);

        if (facing == Direction.NORTH) {
            poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
        } else if (facing == Direction.EAST) {
            poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
        } else if (facing == Direction.SOUTH) {
            poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        } if (facing == Direction.WEST) {
            poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        }

        poseStack.translate(-0.5f, 0, -0.5f);

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.pop();
    }
}
