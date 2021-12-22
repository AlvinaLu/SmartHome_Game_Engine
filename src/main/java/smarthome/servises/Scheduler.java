package smarthome.servises;

import java.util.Set;
import java.util.concurrent.*;

public class Scheduler {
    private static ScheduledExecutorService sysScheduler = Executors.newScheduledThreadPool(100);
    private static Scheduler instance;

    public static Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();
            instance.mainThread = Thread.currentThread();

            new Thread(()->{
                while (true) {
                    instance.checkFinish();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        return instance;
    }

    private long timeScale = 1;


    private Thread mainThread;

    private Set<Future<?>> tasks = ConcurrentHashMap.newKeySet();

    public void setTimeScale(long timeScale) {
        this.timeScale = timeScale;
    }

    public long getTimeScale() {
        return timeScale;
    }

    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay, TimeUnit unit) {

        long millis = unit.toMillis(delay);
        ScheduledFuture<?> schedule = sysScheduler.schedule(() -> {
            Thread.currentThread().setName(command.getClass().getName());
            try {
                command.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, (millis / timeScale), TimeUnit.MILLISECONDS);

        tasks.add(schedule);

        return schedule;
    }

    private void checkFinish() {
        if (!mainThread.isAlive()
                && (tasks.stream().allMatch(it->it.isDone() || it.isCancelled()))
        ) {
            System.exit(0);
        } else {
            tasks.removeIf(task -> task.isDone() || task.isCancelled());
        }
    }

}
