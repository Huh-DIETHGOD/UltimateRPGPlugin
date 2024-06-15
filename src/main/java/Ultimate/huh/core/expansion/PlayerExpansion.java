package Ultimate.huh.core.expansion;

import org.bukkit.entity.Player;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Attribute;

public interface PlayerExpansion extends Player {

    /**
     * Returns the Intelligence of the player
     */
    static Float getIntelligence() {
        return Intelligence;
    }

}