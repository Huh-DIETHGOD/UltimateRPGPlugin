package Ultimate.huh.core.state.intellgence;

import org.bukkit.entity.Player;

import java.awt.*;
import java.util.HashMap;

public abstract class Intelligence implements Player{
    private Player player;
    private float intelligence;
    private String icon = "";
    private HashMap map;

    public float getIntelligence(){
        return intelligence;
    }

    public void setIntelligence(float intelligence) {
        this.intelligence = intelligence;
    }
}



