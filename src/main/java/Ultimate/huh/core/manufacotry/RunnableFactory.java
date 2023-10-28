package Ultimate.huh.core.manufacotry;

import Ultimate.huh.core.scheduling.RunnableTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface RunnableFactory {
    RunnableTask create(@NotNull Consumer<RunnableTask> var1);
}
