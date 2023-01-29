package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.CustomFacingBlock;
import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import kelvin.slendermod.common.blocks.entities.DebrisBlockEntity;
import kelvin.slendermod.common.blocks.entities.TrashBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderDebris extends GeoBlockRenderer<DebrisBlockEntity> {
    public RenderDebris() {
        super(new ModelDebris());
    }

    @Override
    public void render(DebrisBlockEntity tile, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();

        var facing = tile.getCachedState().get(CustomFacingBlock.FACING);

        stack.translate(0.5f, 0, 0.5f);

        if (facing == Direction.NORTH) {
            stack.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, 270, 0)));
        } else if (facing == Direction.EAST) {
            stack.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, 0, 0)));
        } else if (facing == Direction.SOUTH) {
            stack.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, 90, 0)));
        } if (facing == Direction.WEST) {
            stack.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, 180, 0)));
        }

        stack.translate(-0.5f, 0, -0.5f);

        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }
}
