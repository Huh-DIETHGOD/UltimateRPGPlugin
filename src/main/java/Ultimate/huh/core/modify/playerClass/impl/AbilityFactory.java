package Ultimate.huh.core.modify.playerClass.impl;

import org.jetbrains.annotations.Nullable;

public class AbilityFactory {
    public AbilityFactory(){}
    public Ability createAbility(
            String name,@Nullable int cooldown,
            @Nullable int duration, @Nullable float manaCost, @Nullable float healthCost){



        return new Ability(name, cooldown, duration, manaCost, healthCost);
    }
}
