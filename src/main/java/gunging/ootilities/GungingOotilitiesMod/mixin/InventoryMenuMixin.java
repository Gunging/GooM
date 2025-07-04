package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.PlayerLinked;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends RecipeBookMenu<CraftingContainer> implements PlayerLinked {

    public InventoryMenuMixin(MenuType<?> pMenuType, int pContainerId) { super(pMenuType, pContainerId); }

    @Shadow @Final private Player owner;

    @Override public @Nullable Player gungingoom$getAssociatedPlayer() { return owner; }
}
