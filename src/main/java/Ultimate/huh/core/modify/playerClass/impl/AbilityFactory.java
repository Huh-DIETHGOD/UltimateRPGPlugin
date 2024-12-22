package Ultimate.huh.core.modify.playerClass.impl;

import org.jetbrains.annotations.Nullable;

public class AbilityFactory {
    public AbilityFactory(){}
    public Ability createAbility(
            String name,@Nullable int cooldown,
            @Nullable int duration, @Nullable float manaCost, @Nullable float healthCost, @Nullable float chargeTime){
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Ability name cannot be null or empty");
        if(cooldown < 0) cooldown =0;
        if(manaCost < 0) manaCost = 0;
        if(healthCost == 0) healthCost = 0;
        if(duration < 0) duration = 0;
        if(chargeTime < 0) chargeTime = 0;
        // ability
        return new Ability(name, cooldown, duration, manaCost, healthCost);
    }
}
