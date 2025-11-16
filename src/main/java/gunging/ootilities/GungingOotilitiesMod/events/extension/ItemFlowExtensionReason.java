package gunging.ootilities.GungingOotilitiesMod.events.extension;

import gunging.ootilities.GungingOotilitiesMod.mixin.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Consumer;

/**
 * All "extension" kind events interject the vanilla code via Mixins.
 * Then, this enum is used to tell where exactly the event was fired from.
 * <p>
 * As a general naming convention, [CLIENT] means it originates and runs from the
 * local client, [CLIENTBOUND] means it originated from the server and
 * runs on the client, [SERVER] would mean it originates and runs from the server,
 * and [SERVERBOUND] means it originated from a client and runs on the server.
 *
 * @since   1.0.0
 * @author Gunging
 */
public enum ItemFlowExtensionReason {

    /**
     * A strange circumstance, or perhaps null,
     * or perhaps a third party, who knows.
     *
     * @since 1.0.0
     */
    OTHER,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.ClientPacketListener#handleSetCarriedItem(ClientboundSetCarriedItemPacket)}
     * by the mixin {@link ClientPacketListenerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_CARRIED_ITEM_PACKET_HANDLE,

    /**
     * Fired from {@link net.minecraft.network.protocol.game.ServerGamePacketListener#handlePickItem(ServerboundPickItemPacket)}
     * by the mixin {@link ServerGamePacketListenerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_HANDLE_PICK_ITEM,

    /**
     * Fired from {@link net.minecraft.server.players.PlayerList#placeNewPlayer(Connection, ServerPlayer)}
     * by the mixin {@link PlayerListMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_PLACE_NEW_PLAYER,

    /**
     * Fired from {@link net.minecraft.server.players.PlayerList#sendAllPlayerInfo(ServerPlayer)}
     * by the mixin {@link PlayerListMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_SEND_ALL_PLAYER_INFO,

    /**
     * Fired from {@link net.minecraft.network.protocol.game.ServerGamePacketListener#handleSetCarriedItem(ServerboundSetCarriedItemPacket)}
     * by the mixin {@link ServerGamePacketListenerMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CARRIED_ITEM_HANDLE,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode}.ensureHasSentCarriedItem()
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CARRIED_ITEM_SEND_ENSURE_HAS_SENT_CARRIED_ITEM,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.ClientPacketListener}
     * by the mixin {@link ClientPacketListenerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_EQUIPMENT_HANDLE,

    /**
     * Fired from {@link net.minecraft.world.entity.LivingEntity}.handleEquipmentChanges(Map)
     * by the mixin {@link LivingEntityMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_EQUIPMENT_SEND_HANDLE_EQUIPMENT_CHANGES,

    /**
     * Fired from {@link net.minecraft.server.level.ServerEntity#sendPairingData(ServerPlayer, Consumer)}
     * by the mixin {@link LivingEntityMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_SET_EQUIPMENT_SEND_SEND_PAIRING_DATA,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.ClientPacketListener#handleTakeItemEntity(ClientboundTakeItemEntityPacket)}
     * by the mixin {@link ClientPacketListenerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_TAKE_ITEM_ENTITY_HANDLE,

    /**
     * Fired from {@link net.minecraft.world.entity.LivingEntity#take(Entity, int)}
     * by the mixin {@link LivingEntityMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_TAKE_ITEM_ENTITY_SEND_TAKE,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.ClientPacketListener#handleTakeItemEntity(ClientboundTakeItemEntityPacket)}
     * by the mixin {@link ClientPacketListenerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_CONTAINER_SET_SLOT_HANDLE,

    /**
     * Fired from {@link net.minecraft.server.level.ServerPlayer}.containerSynchronizer.sendSlotChange(AbstractContainerMenu, int, ItemStack)
     * by the mixin {@link ContainerSynchronizerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_CONTAINER_SET_SLOT_SEND_SEND_SLOT_CHANGE,

    /**
     * Fired from {@link net.minecraft.server.level.ServerPlayer}.containerSynchronizer.sendCarriedChange(AbstractContainerMenu, ItemStack)
     * by the mixin {@link ContainerSynchronizerMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_CONTAINER_SET_SLOT_SEND_SEND_CARRIED_CHANGE,

    /**
     * Fired from {@link Inventory#placeItemBackInInventory(ItemStack, boolean)}
     * by the mixin {@link InventoryMixin}
     *
     * @since 1.0.0
     */
    CLIENTBOUND_CONTAINER_SET_SLOT_SEND_PLACE_ITEM_BACK_IN_INVENTORY,

    /**
     * Fired from {@link net.minecraft.network.protocol.game.ServerGamePacketListener#handleUseItemOn(ServerboundUseItemOnPacket)}
     * by the mixin {@link ServerGamePacketListenerMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_USE_ITEM_ON_HANDLE,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#useItemOn(LocalPlayer, InteractionHand, BlockHitResult)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_USE_ITEM_ON_SEND_USE_ITEM_ON,

    /**
     * Fired from {@link net.minecraft.network.protocol.game.ServerGamePacketListener#handleInteract(ServerboundInteractPacket)}
     * by the mixin {@link ServerGamePacketListenerMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_INTERACT_HANDLE,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#attack(Player, Entity)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_INTERACT_SEND_ATTACK,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#attack(Player, Entity)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_INTERACT_SEND_INTERACT,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#attack(Player, Entity)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_INTERACT_SEND_INTERACT_AT,

    /**
     * Fired from {@link ServerGamePacketListener#handleContainerClick(ServerboundContainerClickPacket)}
     * by the mixin {@link ServerGamePacketListener}
     *
     * @since 1.0.0
     */
    SERVERBOUND_CONTAINER_CLICK_HANDLE,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#handleInventoryMouseClick(int, int, int, ClickType, Player)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_CONTAINER_CLICK_SEND_HANDLE_MOUSE_CLICK,

    /**
     * Fired from {@link ServerGamePacketListener#handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket)}
     * by the mixin {@link ServerGamePacketListener}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_HANDLE,

    /**
     * Fired from {@link Minecraft}.pickBlock()
     * by the mixin {@link MinecraftMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_PICK_BLOCK,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#handleCreativeModeItemAdd(ItemStack, int)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_ITEM_ADD,

    /**
     * Fired from {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#handleCreativeModeItemAdd(ItemStack, int)}
     * by the mixin {@link MultiPlayerGameModeMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_ITEM_DROP,

    /**
     * Fired from {@link ServerGamePacketListener#handlePlayerAction(ServerboundPlayerActionPacket)}
     * by the mixin {@link ServerGamePacketListenerMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_PLAYER_ACTION_HANDLE,

    /**
     * Fired from {@link LocalPlayer#drop(boolean)}
     * by the mixin {@link LocalPlayerMixin}
     *
     * @since 1.0.0
     */
    SERVERBOUND_PLAYER_ACTION_SEND_DROP,

    /**
     * Fired from {@link Minecraft}.handleKeybinds()
     * by the mixin {@link MinecraftMixin}
     *
     * @since 1.0.0
     */
    CLIENT_MINECRAFT_HANDLE_KEYBINDS,

    /**
     * Fired from {@link net.minecraft.world.entity.player.Player}.readAdditionalSaveData(CompoundTag)
     * by the mixin {@link PlayerMixin}
     *
     * @since 1.0.0
     */
    SERVER_PLAYER_READ_ADDITIONAL_DATA,

    /**
     * Fired from {@link net.minecraft.server.level.ServerPlayer}.containerSynchronizer.sendInitialData(AbstractContainerMenu, NonNullList, ItemStack, int[])
     * by the mixin {@link ContainerSynchronizerMixin}
     *
     * @since 1.0.0
     */
    SERVER_SEND_INITIAL_DATA,

    /**
     * Fired from {@link net.minecraft.world.inventory.AbstractContainerMenu}.setCarriedItem(ItemStack)
     * by the mixin {@link AbstractContainerMenuMixin}
     *
     * @since 1.0.0
     */
    SERVER_ON_SET_CARRIED_ITEM,

    /**
     * Fired from {@link net.minecraft.world.inventory.AbstractContainerMenu}.setCarriedItem(ItemStack)
     * by the mixin {@link AbstractContainerMenuMixin}
     *
     * @since 1.0.0
     */
    CLIENT_ON_SET_CARRIED_ITEM,

    /**
     * Fired from {@link net.minecraft.world.inventory.AbstractContainerMenu}.onInitializeCarriedItem(int, List, ItemStack)
     * by the mixin {@link AbstractContainerMenuMixin}
     *
     * @since 1.0.0
     */
    SERVER_ON_INITIALIZE_CARRIED_ITEM,

    /**
     * Fired from {@link net.minecraft.world.entity.player.Inventory#setPickedItem(ItemStack)}
     * by the mixin {@link InventoryMixin}
     *
     * @since 1.0.0
     */
    CLIENT_INVENTORY_SET_PICKED_ITEM,

    /**
     * Fired from {@link net.minecraft.world.entity.player.Inventory#pickSlot(int)}
     * by the mixin {@link InventoryMixin}
     *
     * @since 1.0.0
     */
    CLIENT_INVENTORY_PICK_SLOT,

    /**
     * Fired from {@link net.minecraft.world.entity.player.Inventory#swapPaint(double)}
     * by the mixin {@link InventoryMixin}
     *
     * @since 1.0.0
     */
    CLIENT_INVENTORY_SWAP_PAINT,

    /**
     * Fired from {@link net.minecraft.world.entity.player.Inventory#replaceWith(Inventory)}
     * by the mixin {@link InventoryMixin}
     *
     * @since 1.0.0
     */
    CLIENT_INVENTORY_REPLACE_WITH,
}
