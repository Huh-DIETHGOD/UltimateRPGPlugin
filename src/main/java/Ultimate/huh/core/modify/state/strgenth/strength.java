package Ultimate.huh.core.modify.state.strgenth;

import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class strength implements Player{
    private Player player;
    private float strength;
    private float basicStrength = 100;
    private String icon = "";
    private HashMap map;
    private float[] bonus = {0, 0, 0}; //[potions, eventBonus, playerBonus]

    public strength(Player player, float[] bonus) {
        this.player = player;
        this.bonus = bonus;
    }

    public float getIntelligence(){
        return strength;
    }

    public void setIntelligence(float intelligence) {
        this.strength = intelligence;
    }

    public void addIntelligence(float addUp){
        this.strength = this.strength + addUp;
    }

    public float calculator(float[] bonus, HashMap map) {
        //final intelligence = basicIntelligence * bonus[0] * bonus[1] * bonus[2]
        // get player class level
        strength = basicStrength*bonus[0]*bonus[1]*bonus[2];
        return strength;
    }

    public float[] getBonus() {
        return bonus;
    }

    public void setPotionBonus(float PotionBonus) {
        this.bonus[0] = PotionBonus;
    }
    
    public void setEventsBonus(float EventsBonus) {
        this.bonus[1] = EventsBonus;
    }
    
    public void setPlayerBonus(float PlayerBonus) {
        this.bonus[2] = PlayerBonus;
    }
}



