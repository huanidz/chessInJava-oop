/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;

import chessproject.board.Move.*;
import chessproject.player.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hoain
 */
public class CheckmateTest {
     
    @Test
    public void fastestCheckmate() {

         Board board = Board.createNormalBoard();
         MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(MoveCreator.createMove(board, BoardUtils.getCoordinateAtPos("f2"),
                                BoardUtils.getCoordinateAtPos("f3")));

        assertTrue(t1.getMoveStatus().isDone());

         MoveTransition t2 = t1.getToBoard()
                .getCurrentPlayer()
                .makeMove(MoveCreator.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPos("e7"),
                                BoardUtils.getCoordinateAtPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

         MoveTransition t3 = t2.getToBoard()
                .getCurrentPlayer()
                .makeMove(MoveCreator.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPos("g2"),
                                BoardUtils.getCoordinateAtPos("g4")));

        assertTrue(t3.getMoveStatus().isDone());

         MoveTransition t4 = t3.getToBoard()
                .getCurrentPlayer()
                .makeMove(MoveCreator.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPos("d8"),
                                BoardUtils.getCoordinateAtPos("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        assertTrue(t4.getToBoard().getCurrentPlayer().isInCheckmate());

    }
}
