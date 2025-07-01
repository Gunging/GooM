package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class that handles 3D-Space math operations
 *
 * @since 0.0.0
 * @author Gunging
 */
@SuppressWarnings("unused")
public class OotilityVectors {

    /**
     * SVF: Side-Vertical-Offset(-Level) relative coordinates, not to be confused with DAH (Dodge-Above-Heading[-Base])
     * or the absolute XYZ coords. The SVF coordinate system points in the direction of an entity, usually a player or
     * caster of an action. Sometimes, this action involves a second body with its own relative coordinates DAH, but the
     * math for those is identical to SVF just with different names to avoid confusion when combining SVF and DAH.
     *
     * @param who The entity whose facing to use as relative axes.
     *
     * @param sOff Relative sideways direction offset
     * @param vOff Relative vertical direction offset
     * @param fOff Relative forward direction offset
     * @param xOff Absolute X-direction offset
     * @param yOff Absolute Y-direction offset
     * @param zOff Absolute Z-direction offset
     *
     * @return A vector (not normalized) representing this SVF transformation.
     */
    @NotNull public static Vec3 entityTransformSVF(@NotNull Entity who, double sOff, double vOff, double fOff, double xOff, double yOff, double zOff) {
        return transformSVF(Math.toRadians(who.getXRot()), Math.toRadians(who.getYRot()), sOff, vOff, fOff, xOff, yOff, zOff);
    }

    /**
     * SVF: Side-Vertical-Offset(-Level) relative coordinates, not to be confused with DAH (Dodge-Above-Heading[-Base])
     * or the absolute XYZ coords. The SVF coordinate system points in the direction of an entity, usually a player or
     * caster of an action. Sometimes, this action involves a second body with its own relative coordinates DAH, but the
     * math for those is identical to SVF just with different names to avoid confusion when combining SVF and DAH.
     *
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @param sOff Relative sideways direction offset
     * @param vOff Relative vertical direction offset
     * @param fOff Relative forward direction offset
     * @param xOff Absolute X-direction offset
     * @param yOff Absolute Y-direction offset
     * @param zOff Absolute Z-direction offset
     *
     * @return A vector (not normalized) representing this SVF transformation.
     */
    @NotNull public static Vec3 transformSVF(double pitch, double yaw, double sOff, double vOff, double fOff, double xOff, double yOff, double zOff) {
        return transformSVFL(pitch, yaw, sOff, vOff, fOff, 0, xOff, yOff, zOff);
    }

    /**
     * @param who The entity whose facing to use as relative axes.
     *
     * @param sOff Relative sideways direction offset
     * @param vOff Relative vertical direction offset
     * @param fOff Relative forward direction offset
     * @param lOff Relative level direction offset, which is in the same direction as forward but no vertical component.
     * @param xOff Absolute X-direction offset
     * @param yOff Absolute Y-direction offset
     * @param zOff Absolute Z-direction offset
     *
     * @return A vector (not normalized) representing this SVF transformation.
     */
    @NotNull public static Vec3 entityTransformSVFL(@NotNull Entity who, double sOff, double vOff, double fOff, double lOff, double xOff, double yOff, double zOff) {
        return transformSVFL(Math.toRadians(who.getXRot()), Math.toRadians(who.getYRot()), sOff, vOff, fOff, lOff, xOff, yOff, zOff);
    }

    /**
     * SVF: Side-Vertical-Offset-Level relative coordinates, not to be confused with DAH (Dodge-Above-Heading-Base)
     * or the absolute XYZ coords. The SVFL coordinate system points in the direction of an entity, usually a player or
     * caster of an action. Sometimes, this action involves a second body with its own relative coordinates DAHB, but the
     * math for those is identical to SVF just with different names to avoid confusion when combining SVFL and DAHB.
     * <p></p>
     * As for the Level (or Base) vector, it is in the same direction as the forward one but with no vertical component.
     * Often useful because it just feels right for some things to move only in this direction.
     *
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @param sOff Relative sideways direction offset
     * @param vOff Relative vertical direction offset
     * @param fOff Relative forward direction offset
     * @param lOff Relative level direction offset, which is in the same direction as forward but no vertical component.
     * @param xOff Absolute X-direction offset
     * @param yOff Absolute Y-direction offset
     * @param zOff Absolute Z-direction offset
     *
     * @return A vector (not normalized) representing this SVF transformation.
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    @NotNull public static Vec3 transformSVFL(double pitch, double yaw, double sOff, double vOff, double lOff, double fOff, double xOff, double yOff, double zOff) {

        // Polar angle Theta
        double tF = (0.5 * Math.PI) + pitch;
        double tV = pitch;
        //double tS = (0.5 * Math.PI);
        //double tL = (0.5 * Math.PI);

        // Azimuthal angle Phi
        double pF = -yaw;
        double pV = pF;
        double pS = pF + (0.5 * Math.PI);
        double pL = pF;

        // Calculate absolutes
        double x = xOff + (fOff * Math.sin(pF) * Math.sin(tF)) + (vOff * Math.sin(pV) * Math.sin(tV))  + (sOff * Math.sin(pS))   + (lOff * Math.sin(pL));
        double y = yOff + (fOff * Math.cos(tF))                + (vOff * Math.cos(tV));
        double z = zOff + (fOff * Math.cos(pF) * Math.sin(tF)) + (vOff * Math.cos(pV) * Math.sin(tV))  + (sOff * Math.cos(pS))   + (lOff * Math.cos(pL));

        //double x = xOff + (fOff * Math.sin(pF) * Math.sin(tF)) + (vOff * Math.sin(pV) * Math.sin(tV))  + (sOff * Math.sin(pS) * Math.sin(tS))   + (lOff * Math.sin(pL) * Math.sin(tL));
        //double y = yOff + (fOff * Math.cos(tF))                + (vOff * Math.cos(tV))                 + (sOff * Math.cos(tS))                  + (lOff * Math.cos(tL));
        //double z = zOff + (fOff * Math.cos(pF) * Math.sin(tF)) + (vOff * Math.cos(pV) * Math.sin(tV))  + (sOff * Math.cos(pS) * Math.sin(tS))   + (lOff * Math.cos(pL) * Math.sin(tL));

        // Bake vector
        return new Vec3(x, y, z);
    }

    /**
     * @param who The entity in question
     *
     * @return The side unit vector, rectangular to the forward vector and having no component along the Y-axis
     *
     * @see #entityVertical(Entity)
     * @see #entityForward(Entity)
     * @see #entityTransformSVF(Entity, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 entitySide(@NotNull Entity who) {
        return side(0, Math.toRadians(who.getYRot()));
    }

    /**
     * @param who The entity in question
     *
     * @return The vertical unit vector, rectangular to the forward and side vectors
     *
     * @see #entitySide(Entity)
     * @see #entityForward(Entity)
     * @see #entityTransformSVF(Entity, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 entityVertical(@NotNull Entity who) {
        return vertical(Math.toRadians(who.getXRot()), Math.toRadians(who.getYRot()));
    }

    /**
     * @param who The entity in question
     *
     * @return The forward vector, in the direction they entity is looking.
     *
     * @see #entitySide(Entity)
     * @see #entityVertical(Entity)
     * @see #entityTransformSVF(Entity, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 entityForward(@NotNull Entity who) {
        return forward(Math.toRadians(who.getXRot()), Math.toRadians(who.getYRot()));
    }

    /**
     * @param who The entity in question
     *
     * @return The level unit vector, in the direction as the forward vector but no vertical component
     *
     * @see #entitySide(Entity)
     * @see #entityVertical(Entity)
     * @see #entityForward(Entity)
     * @see #entityTransformSVFL(Entity, double, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 entityLevel(@NotNull Entity who) {
        return level(Math.toRadians(who.getXRot()), Math.toRadians(who.getYRot()));
    }

    /**
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians. Ignored.
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @return The side unit vector, rectangular to the forward vector and having no component along the Y-axis
     *
     * @see #forward(double, double)
     * @see #vertical(double, double)
     * @see #transformSVF(double, double, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 side(double pitch, double yaw) {

        // Polar angle Theta
        //double tS = (0.5 * Math.PI);

        // Azimuthal angle Phi
        double pS = (0.5 * Math.PI) - yaw;

        // Bake vector
        return new Vec3(Math.sin(pS), 0, Math.cos(pS));
        //return new Vec3(Math.sin(pS) * Math.sin(tS), Math.cos(tS), Math.cos(pS) * Math.sin(tS));
    }

    /**
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @return The vertical unit vector, rectangular to the forward and side vectors
     *
     * @see #forward(double, double)
     * @see #side(double, double)
     * @see #transformSVF(double, double, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    @NotNull public static Vec3 vertical(double pitch, double yaw) {

        // Polar angle Theta
        double tV = pitch;

        // Azimuthal angle Phi
        double pV = -yaw;

        // Bake vector
        return new Vec3(Math.sin(pV) * Math.sin(tV), Math.cos(tV), Math.cos(pV) * Math.sin(tV));
    }

    /**
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @return The forward unit vector, in the direction these angles are facing
     *
     * @see #vertical(double, double)
     * @see #side(double, double)
     * @see #transformSVF(double, double, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 forward(double pitch, double yaw) {

        // Polar angle Theta
        double tF = (0.5 * Math.PI) + pitch;

        // Azimuthal angle Phi
        double pF = -yaw;

        // Bake vector
        return new Vec3(Math.sin(pF) * Math.sin(tF), Math.cos(tF), Math.cos(pF) * Math.sin(tF));
    }

    /**
     * @param pitch The rotation along the X-axis, in the direction from the Y-axis to the Z-axis, in radians. Ignored.
     * @param yaw The rotation along the Y-axis, in the direction from the Z-axis to the X-axis, in radians
     *
     * @return The level unit vector, in the direction as the forward vector but no vertical component
     *
     * @see #forward(double, double)
     * @see #vertical(double, double)
     * @see #side(double, double)
     * @see #transformSVFL(double, double, double, double, double, double, double, double, double)
     *
     * @since 0.0.4
     * @author Gunging
     */
    @NotNull public static Vec3 level(double pitch, double yaw) {

        // Polar angle Theta
        //double tL = (0.5 * Math.PI);

        // Azimuthal angle Phi
        double pL = -yaw;

        // Bake vector
        return new Vec3(Math.sin(pL), 0, Math.cos(pL));
        //return new Vec3(Math.sin(pL) * Math.sin(tL), Math.cos(tL), Math.cos(pL) * Math.sin(tL));
    }

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
