package Ultimate.huh.core.modify.playerClass.MageClass;

import Ultimate.huh.core.modify.playerClass.impl.ClassFactory;

public abstract class Mage extends ClassFactory{
    public void leftClickAbility() {
        AbilityLaserBeam laserBeam = new AbilityLaserBeam();
        laserBeam.laser();
        // I am tired
        // swap to mageaddons mod
    }
}
