package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.PlayerLinked;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class ItemPickerMenuMixin extends AbstractContainerMenu implements PlayerLinked {

    @Shadow @Final private AbstractContainerMenu inventoryMenu;

    protected ItemPickerMenuMixin(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    @Override
    public @Nullable Player gungingoom$getAssociatedPlayer() {

        // Obtain from contained menu
        AbstractContainerMenu menu = inventoryMenu;
        if (menu instanceof PlayerLinked) { return ((PlayerLinked) menu).gungingoom$getAssociatedPlayer(); }
        return null;
    }
}
