package utils;

import java.util.Timer;
import java.util.TimerTask;

public class SchedulerUtil {

    private static final int MOVE_DELAY = 1000; // 1 second delay

    /**
     * Schedules a move to be executed after a specified delay.
     * 
     * @param row The row index of the cell where the move will be executed.
     * @param col The column index of the cell where the move will be executed.
     * @param cellChooser The action to perform when the delay expires.
     */
    public static void scheduleMove(int row, int col, Runnable cellChooser) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cellChooser.run();
            }
        }, MOVE_DELAY);
    }
}