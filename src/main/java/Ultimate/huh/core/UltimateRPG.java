package Ultimate.huh.core;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class UltimateRPG {
    private static Handler handler;

    @ApiStatus.Internal
    public static void setHandler(@NotNull Handler handler) {
        Validate.isTrue(UltimateRPG.handler == null, "Already initialized!");
        UltimateRPG.handler = handler;
    }

    public static Handler getHandler() {
        return handler;
    }


}
