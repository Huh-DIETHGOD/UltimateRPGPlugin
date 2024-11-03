package Ultimate.huh.core.modify.playerClass.MageClass;

import Ultimate.huh.core.events.EventPlayerAttack;
import Ultimate.huh.core.expansion.PlayerExpansion;
import Ultimate.huh.core.modify.playerClass.impl.AbilityFactory;
import Ultimate.huh.core.modify.state.intellgence.Intelligence;
import org.bukkit.entity.Player;

public class AbilityLaserBeam extends AbilityFactory {
    private float finalDamage;
    Player player;
    EventPlayerAttack onAttack;
    Intelligence intelligence = PlayerExpansion.getIntelligence(player);


    public AbilityLaserBeam() {
        player = onAttack.getPlayer();
        if (player != null){
            intelligence.calculator(null, null);
        }
        // Beam damage function:
        // Dmg(strength, critical chance, critical damage, calculator, )
        // =(strength * critical damage)*
    }

    public void laserBeam() {

    }
}
