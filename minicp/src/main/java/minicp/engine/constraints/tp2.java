package minicp.engine.constraints;

import minicp.engine.core.AbstractConstraint;
import minicp.engine.core.IntVar;

/**
 * tp2 constraint between two variables
 */
public class tp2 extends AbstractConstraint {
    private final IntVar x, y, z;

    public tp2(IntVar x, IntVar y, IntVar z) { // if x = 0, y >= z, else y <= z
        super(x.getSolver());
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void post() {
        if (x.isBound() && x.contains(0)) {
            int minY = y.min();
            int maxZ = z.max();
            z.removeBelow(minY);
            y.removeAbove(maxZ);
        }
        else if (x.isBound() && !x.contains(0)){
            int minZ = z.min();
            int maxY = y.max();
            y.removeBelow(minZ);
            z.removeAbove(maxY);
        }
        else {
            z.propagateOnBind(this);
            y.propagateOnBind(this);
        }
    }

    @Override
    public void propagate() {
        if (y.isBound())
            x.remove(y.min());
        else y.remove(x.min());
        setActive(false);
    }
}