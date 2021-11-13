/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.player;

import chessproject.board.*;

/**
 *
 * @author hoain
 */
public class MoveTransition {
    
    public Board Board;
    public Move move;
    public MoveChecker moveStatus;
    public Board fromBoard;
    public Board toBoard;

    public MoveTransition(Board board, Move move, MoveChecker moveStatus) {
        this.Board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    public MoveTransition(Board fromBoard, Board toBoard, Move move, MoveChecker moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveChecker getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTheBoardAfter() {
        return this.Board;
    }
    
    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Board getToBoard() {
         return this.toBoard;
    }
}
