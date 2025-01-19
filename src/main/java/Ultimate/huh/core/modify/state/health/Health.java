package Ultimate.huh.core.modify.state.health;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.Metadatable;

public abstract class Health implements Entity {

    private float health;
    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
