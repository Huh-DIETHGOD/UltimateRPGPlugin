package Ultimate.huh.core.modify.playerClass.MageClass;

import Ultimate.huh.core.events.EventPlayerAttack;
import Ultimate.huh.core.expansion.PlayerExpansion;
import Ultimate.huh.core.modify.playerClass.impl.AbilityFactory;
import Ultimate.huh.core.modify.state.health.Health;
import Ultimate.huh.core.modify.state.intellgence.Intelligence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AbilityLaserBeam extends AbilityFactory {
    private float finalDamage;
    Player player;
    EventPlayerAttack onAttack;
    Intelligence intelligence = PlayerExpansion.getIntelligence(player);
    Health health;

    public AbilityLaserBeam() {
    }

    public void laser(){
        player = onAttack.getPlayer();
        if (player != null) {
            intelligence.calculator(null);
            Location location = player.getLocation();
            List<LivingEntity> hitEntities = new ArrayList<>();
            float damage = 0;

            for (double t = 0; t < 10; t += 0.1) {
                double x = location.getX() + t * Math.sin(Math.toRadians(location.getYaw()));
                double y = location.getY() + t * Math.sin(Math.toRadians(location.getPitch()));
                double z = location.getZ() + t * Math.cos(Math.toRadians(location.getYaw()));
                Location particleLocation = new Location(location.getWorld(), x, y, z);

                particleLocation.getWorld().spawnParticle(Particle.END_ROD, particleLocation, 1);

                for (Entity entity : particleLocation.getWorld().getEntities()) {
                    if (entity instanceof LivingEntity && entity.getLocation().distance(particleLocation) < 0.5 && !entity.equals(player)) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(damage);
                    }
                }


            }

            // Beam damage function:
            // Dmg(strength, critical chance, critical damage, calculator, )
            // =(strength * critical damage)*
        }

    }
}
