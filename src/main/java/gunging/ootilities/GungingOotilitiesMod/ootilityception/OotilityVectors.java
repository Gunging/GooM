package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class that handles 3D-Space math operations
 *
 * @since 0.0.0
 * @author Gunging
 */
public class OotilityVectors {

    /**
     * @param origin The first point
     * @param target The second point
     * @param range The acceptable distance from the origin
     *
     * @return If the target point is in range of the origin
     *
     * @since 0.0.0
     * @author Gunging
     */
    public static boolean inRange(@NotNull Vec3 origin, @NotNull Vec3 target, double range) {

        // Silly math
        double dx = (target.x - origin.x);
        double dy = (target.y - origin.y);
        double dz = (target.z - origin.z);
        double D = dx * dx + dy * dy + dz * dz;

        // The range must exceed or equal the distance
        return (range * range) >= D;
    }
}
