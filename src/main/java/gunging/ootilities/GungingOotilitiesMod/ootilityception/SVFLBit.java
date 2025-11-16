package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import org.jetbrains.annotations.NotNull;

/**
 * A tiny package or builder containing SVL
 * coordinates so that you don't have to
 * specify 12 different numbers very time
 *
 * @since 1.0.0
 * @author Gunging
 */
public class SVFLBit implements Cloneable {

    /**
     * The numeric coordinate for this axis
     *
     * @since 1.0.0
     */
    double s, v, f, l, x, y, z;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getS() { return s; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getV() { return v; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getF() { return f; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getL() { return l; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getX() { return x; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getY() { return y; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getZ() { return z; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setS(double s) { this.s = s; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setV(double v) { this.v = v; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setF(double f) { this.f = f; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setL(double l) { this.l = l; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setX(double x) { this.x = x; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setY(double y) { this.y = y; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setZ(double z) { this.z = z; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public SVFLBit(double s, double v, double f, double l, double x, double y, double z) {
        this.s = s;
        this.v = v;
        this.f = f;
        this.l = l;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public SVFLBit() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public SVFLBit(double s, double v, double f) {
        this(s, v, f, 0, 0, 0, 0);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public SVFLBit(double s, double v, double f, double l) {
        this(s, v, f, l, 0, 0, 0);
    }

    /**
     * Sets the X, Y, Z components of this
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public SVFLBit withXYZ(double x, double y,  double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Sets the S, V, F components of this
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public SVFLBit withSVF(double s, double v,  double f) {
        this.s = s;
        this.v = v;
        this.f = f;
        return this;
    }

    /**
     * Sets the S, V, F, and L components of this
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public SVFLBit withSVFL(double s, double v,  double f,  double l) {
        this.s = s;
        this.v = v;
        this.f = f;
        this.l = l;
        return this;
    }

    /**
     * If the S, V, and F components are zero
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isZeroSVF() { return this.s == 0 && this.v == 0 && this.f == 0; }

    /**
     * If the X, Y, and Z components are zero
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isZeroXYZ() { return this.x == 0 && this.y == 0 && this.z == 0; }

    /**
     * If the S, V, F, and L components are zero
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isZeroSVFL() { return isZeroSVF() && this.l == 0; }

    /**
     * If all components of this bit are zero
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isZero() { return isZeroSVFL() && isZeroXYZ(); }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public SVFLBit clone() { return new SVFLBit(s, v, f, l, x, y, z); }
}
