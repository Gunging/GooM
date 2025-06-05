package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Unique
    public int gungingoom$originalSlot;

    @Unique
    public ItemStack gungingoom$originalMainhand;

    @Unique
    @NotNull
    public ArrayList<ItemStack> gungingoom$originalOffhand = new ArrayList<>();

    @Unique
    void gungingoom$prepareOriginals(@org.jetbrains.annotations.Nullable Player who) {

        // If a player actually exists
        if (who == null) { return; }

        // Remember their original slot
        gungingoom$originalSlot = who.getInventory().selected;
        gungingoom$originalMainhand = who.getInventory().getSelected();

        gungingoom$originalOffhand.clear();
        gungingoom$originalOffhand.addAll(who.getInventory().offhand);
    }

    @Unique
    boolean gungingoom$isMainhandRedundant(@org.jetbrains.annotations.Nullable Player who, boolean deep) {
        if (who == null) { return true; }

        return
                // Redundant if the selection is unchanged
                gungingoom$originalSlot == who.getInventory().selected &&

                // If searching in deep mode, also check identical items
                (!deep || gungingoom$originalMainhand.equals(who.getInventory().getSelected(), false));
    }

    @Unique
    boolean gungingoom$isOffhandRedundant(@org.jetbrains.annotations.Nullable Player who) {
        if (who == null) { return true; }

        for (int i = 0; i < who.getInventory().offhand.size(); i++) {
            ItemStack one = who.getInventory().offhand.get(i);
            ItemStack two = gungingoom$originalOffhand.get(i);

            // If any of these differ, then the offhand changed. It is not redundant
            if (!one.equals(two, false)) { return false; }
        }
        return true;
    }

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    protected void onHandleKeybindsCall(CallbackInfo ci) {
        gungingoom$prepareOriginals(this.player);
    }

    @Inject(method = "handleKeybinds", at = @At("RETURN"))
    protected void onHandleKeybindsReturn(CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.player, false)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_MINECRAFT_HANDLE_KEYBINDS, true, EquipmentSlot.MAINHAND, this.player);
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.player)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_MINECRAFT_HANDLE_KEYBINDS, true, EquipmentSlot.OFFHAND, this.player);
        }
    }

    @Inject(method = "pickBlock", at = @At("HEAD"))
    protected void onPickBlockCall(CallbackInfo ci) {
        if (this.player == null) { return; }
        gungingoom$originalSlot = this.player.getInventory().selected;
        gungingoom$originalMainhand = this.player.getInventory().getSelected();
    }

    @Inject(method = "pickBlock", at = @At("RETURN"))
    protected void onPickBlockReturn(CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.player, false)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_PICK_BLOCK, true, EquipmentSlot.MAINHAND, this.player);
        }
    }
}
