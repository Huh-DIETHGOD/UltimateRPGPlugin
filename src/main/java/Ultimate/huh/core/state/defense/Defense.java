package Ultimate.huh.core.state.defense;

import org.bukkit.entity.Player;

public class Defense {
    private Player player;
    /**
     * physicalDefense use precentage calculation
     * 0% is original defense value
     * 100% means player wont take any physical damage
     */
    private double physicalDefense = 0.0;
    private double magicalDefense = 0.0;
    private double trueDefense = 0.0;

    public double physicalDamageCalculator(Player player, double originalDamage, double physicalDefense, String hyperCommand) {
        double calculatedDamage;

        calculatedDamage = originalDamage*physicalDefense;

        return calculatedDamage;
    }

    public double magicalDamageCalculator(Player player, double originalDamage, double magicalDefense, String hyperCommand) {
        double calculatedDamage;

        calculatedDamage = originalDamage*(magicalDefense-0.045);

        return calculatedDamage;
    }
}
