package yuriitsap.example.com.custompicasso.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class FixedExecutor implements Runnable {

    private static final int WORKER_THREAD_COUNT = 2;
    private List<Thread> mThreads;
    private LinkedList<Runnable> mTasks;

    public FixedExecutor() {
        mThreads = new ArrayList<>(WORKER_THREAD_COUNT);
        mTasks = new LinkedList<>();
        for (int i = WORKER_THREAD_COUNT; i > 0; i--) {
            Thread thread = new Thread(this);
            mThreads.add(thread);
            thread.start();
        }
    }

    @Override
    public void run() {
        Runnable runnable = null;
        while (true) {
            synchronized (this) {
                runnable = mTasks.poll();
                if (runnable != null) {
                    runnable.run();
                } else {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void execute(Runnable runnable) {
        mTasks.offer(runnable);
        synchronized (this){
            this.notify();
        }
    }
}
