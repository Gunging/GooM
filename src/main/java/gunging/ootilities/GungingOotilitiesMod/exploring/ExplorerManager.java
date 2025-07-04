package gunging.ootilities.GungingOotilitiesMod.exploring;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPExplorerStatements;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * The manager class where Item Stack Statements are registered
 * to so they are accessible from anywhere.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ExplorerManager {

    /**
     * The list of registered explorer statements, indexed by namespace
     *
     * @since 1.0.0
     */
    @NotNull static final HashMap<String, HashMap<String, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>>> statementsByNamespace = new HashMap<>();

    /**
     * An index by integer numbers, not guaranteed to persist between sessions since
     * it is assigned at runtime, synced between server and clients when needed. This
     * allows to send statements as packets not by their Resource Location but by this
     * one index.
     *
     * @since 1.0.0
     */
    @NotNull static final HashMap<Integer, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> statementsByNetwork = new HashMap<>();

    /**
     * The list of registered explorer statements
     *
     * @since 1.0.0
     */
    @NotNull static final HashMap<ResourceLocation, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> registeredStatements = new HashMap<>();

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public static HashMap<ResourceLocation, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> getRegisteredStatements() { return registeredStatements; }

    /**
     * Clears the registry of statements.
     * <br>
     * <i>Beware of old instances containing outdated network indices. </i>
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void clearRegisteredStatements() {
        registeredStatements.clear();
        statementsByNamespace.clear();
        statementsByNetwork.clear();
    }

    /**
     * @param n The Network Index of this statement
     * @return The statement associated with this network index, if it exists
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> getByNetwork(int n) {
        return statementsByNetwork.get(n);
    }

    /**
     * @param statement The statement object itself
     * @param aliases The aliases this statement may be addressed by
     *
     * @return If this statement was successfully registered
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static boolean registerStatement(@NotNull ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> statement, boolean isServer, @NotNull String... aliases) {

        boolean registered = false;

        // Try registering its internal name, straight-up
        if (!registeredStatements.containsKey(statement.getStatementName())) {
            registeredStatements.put(statement.getStatementName(), statement);
            HashMap<String, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> byNamespace = statementsByNamespace.computeIfAbsent(statement.getStatementName().getNamespace(), k -> new HashMap<>());
            byNamespace.put(statement.getStatementName().getPath(), statement);
            registered = true;
        }

        // Try registering every alias
        for (String alias : aliases) {

            // Create a new resource key to register it by
            ResourceLocation aliasKey = ResourceLocation.fromNamespaceAndPath(statement.getStatementName().getNamespace(), alias);

            // Attempt to register it
            if (!registeredStatements.containsKey(aliasKey)) {
                registeredStatements.put(aliasKey, statement);
                HashMap<String, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> byNamespace = statementsByNamespace.computeIfAbsent(aliasKey.getNamespace(), k -> new HashMap<>());
                byNamespace.put(aliasKey.getPath(), statement);
                registered = true;
            }
        }

        // Assign network index, but only on the server-side
        if (registered && isServer) {
            int networkIndex = statementsByNetwork.size() + 1;
            statement.setNetworkIndex(networkIndex);
            statementsByNetwork.put(networkIndex, statement);
        }

        // Success if any alias succeeded
        return registered;
    }

    /**
     * @param namespacedKey The provided namespace:key identifier
     *
     * @return The Item Explorer Statement associated with this identifier
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null -> null")
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> findStatement(@Nullable ResourceLocation namespacedKey) {
        if (namespacedKey == null) { return  null; }
        return registeredStatements.get(namespacedKey);
    }

    /**
     * @param name The key identifier provided, using the default namespace {@link GungingOotilitiesMod#MODID}
     *
     * @return The Item Explorer Statement associated with this identifier
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null -> null")
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> findStatement(@Nullable String name) {
        if (name == null) { return  null; }
        return findStatement(GungingOotilitiesMod.MODID, name);
    }

    /**
     * @param namespace The provided namespace
     * @param name The provided key identifier
     *
     * @return The Item Explorer Statement associated with this identifier
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null,_ -> null;_,null -> null")
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> findStatement(@Nullable String namespace, @Nullable String name) {
        if (name == null || namespace == null) { return  null; }

        // Build the Resource Location
        ResourceLocation namespacedKey = null;

        try {
            // Try to parse the namespaced key, either by a separator or by combining namespace and name
            if (name.contains(":")) { namespacedKey = ResourceLocation.tryBySeparator(name, ':'); }
            if (namespacedKey == null) { namespacedKey = ResourceLocation.fromNamespaceAndPath(namespace, name); }
        } catch (ResourceLocationException ignored) { return null; }

        // Well, try to find that namespaced key statement
        return findStatement(namespacedKey);
    }

    /**
     * The main function of this method is to decode which part of
     * the "keyOptions" argument is the path of the statement, and
     * what part of the statement is its options.
     *
     * @param namespace The namespace to be used
     * @param keyOptions The name of the statement and its options
     *
     * @return A built explorer statement if it could be decoded
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null,_ -> null;_,null -> null")
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> decodeStatement(@Nullable String namespace, @Nullable String keyOptions) {
        if (namespace == null || keyOptions == null) { return null; }

        /*
         * Honestly I can't think of a better way than a brute force attack.
         * This is because I don't want to make any assumption about the options
         */
        HashMap<String, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> byNamespace = statementsByNamespace.get(namespace);
        for (Map.Entry<String, ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?>> pair : byNamespace.entrySet()) {
            String statement = pair.getKey();

            // Okay we found the one
            if (keyOptions.startsWith(statement)) {
                String options = keyOptions.substring(statement.length());

                // Yah gg
                return pair.getValue().withOptions(options);
            }
        }

        // None was found, so we use the default STANDARD
        return ISPExplorerStatements.STANDARD.withOptions(keyOptions);
    }

    /**
     * If there is no namespace, assumes the default namespace {@link GungingOotilitiesMod#MODID}.
     * Some statements do not have options, and a popular form of options is a number range, some
     * examples:
     * <br>
     * <code>gungingoom:mainhand</code>
     * <br>
     * <code>gungingoom:enderchest3..18</code>
     * <br>
     * <code>offhand</code> - autocompleted to <code>gungingoom:offhand</code>
     * <br>
     * <code>0..5</code> - autocompleted to <code>gungingoom:standard0..5</code>
     *
     * @param statement A statement in the form namespace:keyOPTIONS
     *
     * @return A built explorer statement if it could be decoded
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null -> null")
    @Nullable public static ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> decodeStatement(@Nullable String statement) {
        if (statement == null) { return null; }
        String namespace = GungingOotilitiesMod.MODID;
        String keyOptions = statement;
        if (statement.contains(":")) {
            int namespaceDiv = statement.indexOf(':');
            namespace = statement.substring(0, namespaceDiv);
            keyOptions = statement.substring(namespaceDiv + 1);
        }

        // Decode key options
        return decodeStatement(namespace, keyOptions);
    }

    /**
     * @param statement The explorer statement as provided by the user
     *
     * @return A built Item Stack Explorer, if it could be built
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract("null -> null")
    @Nullable public static ItemStackExplorer<? extends ItemExplorerElaborator<?>, ?> buildItemStackExplorer(@Nullable String statement) {
        if (statement == null) { return null; }
        ItemExplorerStatement<? extends ItemExplorerElaborator<?>, ?> decoded = decodeStatement(statement);
        if (decoded == null) { return null; }
        return decoded.prepareExplorer();
    }
}
