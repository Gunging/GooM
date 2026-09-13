package gunging.ootilities.GungingOotilitiesMod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatInstance;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStack;
import gunging.ootilities.GungingOotilitiesMod.stats.registry.GOOMStats;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> implements net.minecraftforge.common.extensions.IForgeItemStack, WithStatsStack {
    @Shadow @Nullable private CompoundTag tag;

    @Shadow public abstract CompoundTag getOrCreateTag();

    @Shadow @Nullable public abstract CompoundTag getTag();

    protected ItemStackMixin(Class<ItemStack> baseClass) { super(baseClass); }

    @Unique @NotNull StatStack gungingoom$stats = new StatStack().postInherentStatsChanged((stacc) -> gungingoom$sendMyStatChanges());
    @Override public @NotNull StatStack gungingoom$getStatStack() { return gungingoom$stats; }

    @Unique
    public void gungingoom$sendMyStatChanges() {

        // Update inherent after every change
        getOrCreateTag().putString(GOOMStats.GOOM_STATS_NBT_TAG, gungingoom$stats.serializeInherent());
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    public void whenReadFromFullTag(CompoundTag pCompoundTag, CallbackInfo ci) {

        CompoundTag nbtTag = getTag();
        if (nbtTag != null) {
            if (nbtTag.contains(GOOMStats.GOOM_STATS_NBT_TAG)) {

                // Init GooM
                gungingoom$stats.deserializeInherent(nbtTag.getString(GOOMStats.GOOM_STATS_NBT_TAG), null);
            }
        }
    }

    @WrapOperation(method = "copy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setPopTime(I)V"))
    public void whenCloned(@NotNull ItemStack instance, int pPopTime, Operation<Void> original) {
        original.call(instance, pPopTime);

        // Send in a copy of these items' inherent stats
        WithStatsStack other = (WithStatsStack) (Object) instance;
        for (StatInstance<?> inherent : gungingoom$stats.getInherentStats().values()) {
            StatInstance cloned = inherent.clone();
            other.gungingoom$getStatStack().setStat(cloned);
            other.gungingoom$getStatStack().getRefreshedStatTotals();
        }
    }
}
