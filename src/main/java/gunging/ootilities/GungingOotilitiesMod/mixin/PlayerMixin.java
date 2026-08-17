package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.stats.core.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.HashMap;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements WithStatsStack {

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract Inventory getInventory();

    /*

    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    protected void onReadAdditionalSaveData(CompoundTag pPacket, CallbackInfo ci) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVER_PLAYER_READ_ADDITIONAL_DATA, false, slot, this);
        }
    }

    //*/

    @Unique @NotNull StatStack gungingoom$stats = new StatStack();
    @Override public @NotNull StatStack gungingoom$getStatStack() { return gungingoom$stats; }
}
