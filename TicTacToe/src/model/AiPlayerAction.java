package model;

import controller.GameController;
import utils.SchedulerUtil;

/*public class AiPlayerAction implements PlayerAction {

    private final String symbol;

    public AiPlayerAction(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void makeMove(GameController gc, int mover) {
        AiPlayer ai = new AiPlayer(symbol);
        ai.findBestMove(gc.getGameBoard().getBoard());
        int bestRow = ai.getBestRow();
        int bestCol = ai.getBestCol();

        SchedulerUtil.scheduleMove(bestRow, bestCol, () -> {
        	gc.getGameBoard().getCells()[bestRow][bestCol].chooseCell();
    });
}
    }*/