import java.util.Collection;
import java.util.concurrent.Executor;

public class DeterministicExecutor implements Executor {
    @Override public void execute(Runnable command) { command.run(); }
    public void runUntilIdle() {}
}


