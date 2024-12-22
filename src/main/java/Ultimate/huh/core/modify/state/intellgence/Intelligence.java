package Ultimate.huh.core.modify.state.intellgence;

import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class Intelligence implements Player{
    private Player player;
    private float intelligence;
    private float basicIntelligence = 100;
    private String icon = "";
    private HashMap map;
    private float[] bonus = {0, 0, 0}; //[potions, eventBonus, playerBonus]

    public Intelligence(Player player, float[] bonus) {
        this.player = player;
        this.bonus = bonus;
    }

    public float getIntelligence(){
        return intelligence;
    }

    public void setIntelligence(float intelligence) {
        this.intelligence = intelligence;
    }

    public void addIntelligence(float addUp){
        this.intelligence = this.intelligence + addUp;
    }

    public float calculator(float[] bonus) {
        //final intelligence = basicIntelligence * bonus[0] * bonus[1] * bonus[2]
        // get player class level
        intelligence = basicIntelligence*bonus[0]*bonus[1]*bonus[2];
        return intelligence;
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



