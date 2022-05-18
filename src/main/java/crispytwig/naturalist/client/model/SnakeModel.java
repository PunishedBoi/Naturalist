package crispytwig.naturalist.client.model;

import crispytwig.naturalist.Naturalist;
import crispytwig.naturalist.entity.Snake;
import crispytwig.naturalist.registry.NaturalistEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SnakeModel extends AnimatedGeoModel<Snake> {
    @Override
    public ResourceLocation getModelLocation(Snake crocodile) {
        return new ResourceLocation(Naturalist.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Snake snake) {
        return new ResourceLocation(Naturalist.MOD_ID, "textures/entity/snake/snake.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Snake snake) {
        return new ResourceLocation(Naturalist.MOD_ID, "animations/snake.animation.json");
    }

    @Override
    public void setLivingAnimations(Snake snake, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(snake, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone tail2 = this.getAnimationProcessor().getBone("tail2");
        IBone tail4 = this.getAnimationProcessor().getBone("tail4");

        if (!snake.isSleeping()) {
            head.setRotationX(extraDataOfType.get(0).headPitch * Mth.DEG_TO_RAD);
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * Mth.DEG_TO_RAD);
        }
        if (!snake.getMainHandItem().isEmpty()) {
            tail2.setScaleX(1.5F);
            tail2.setScaleY(1.5F);
        }
        if (!snake.getType().equals(NaturalistEntityTypes.SNAKE.get())) {
            tail4.setHidden(true);
        }
    }
}
