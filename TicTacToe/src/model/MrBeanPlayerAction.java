package model;

import controller.GameController;
import utils.SchedulerUtil;

public class MrBeanPlayerAction implements PlayerAction {

    @Override
    public void makeMove(GameController gc, int mover) {
        RandomPlayer mrBean = new RandomPlayer();
        mrBean.playRandomMove(gc.getGameBoard().getBoard());
        int row = mrBean.getRow();
        int col = mrBean.getColumn();
        
        SchedulerUtil.scheduleMove(row, col, () -> {
            gc.getGameBoard().getCells()[row][col].chooseCell();
        });
    }
}
