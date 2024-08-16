package model;

import controller.GameController;

public interface PlayerAction {
    void makeMove(GameController gc, int mover);
}