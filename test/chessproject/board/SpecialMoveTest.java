/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;
import chessproject.player.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 *
 * @author hoain
 */
public class SpecialMoveTest {
    
    @Test
    public void KingSideCastle() {
        Board board = Board.createNormalBoard();
        MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(board, BoardUtils.getCoordinateAtPos("e2"),
                        BoardUtils.getCoordinateAtPos("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        
        MoveTransition t2 = t1.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPos("e7"),
                        BoardUtils.getCoordinateAtPos("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        
        MoveTransition t3 = t2.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPos("g1"),
                        BoardUtils.getCoordinateAtPos("f3")));
        assertTrue(t3.getMoveStatus().isDone());
        
        MoveTransition t4 = t3.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPos("d7"),
                        BoardUtils.getCoordinateAtPos("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        
        MoveTransition t5 = t4.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPos("f1"),
                        BoardUtils.getCoordinateAtPos("e2")));
        assertTrue(t5.getMoveStatus().isDone());
        
        MoveTransition t6 = t5.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPos("d6"),
                        BoardUtils.getCoordinateAtPos("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        
        Move wm1 = Move.MoveCreator
                .createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPos(
                        "e1"), BoardUtils.getCoordinateAtPos("g1"));
        assertTrue(t6.getToBoard().getCurrentPlayer().getLegalMoves().contains(wm1));
        MoveTransition t7 = t6.getToBoard().getCurrentPlayer().makeMove(wm1);
        assertTrue(t7.getMoveStatus().isDone());
    }
    
    @Test
    public void QueenSideCastle() {
        Board board = Board.createNormalBoard();
        
        MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(board, BoardUtils.getCoordinateAtPos("e2"),
                        BoardUtils.getCoordinateAtPos("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        
        MoveTransition t2 = t1.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t1.getToBoard(), BoardUtils.getCoordinateAtPos("e7"),
                        BoardUtils.getCoordinateAtPos("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        
        MoveTransition t3 = t2.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t2.getToBoard(), BoardUtils.getCoordinateAtPos("d2"),
                        BoardUtils.getCoordinateAtPos("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        
        MoveTransition t4 = t3.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t3.getToBoard(), BoardUtils.getCoordinateAtPos("d7"),
                        BoardUtils.getCoordinateAtPos("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        
        MoveTransition t5 = t4.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t4.getToBoard(), BoardUtils.getCoordinateAtPos("c1"),
                        BoardUtils.getCoordinateAtPos("d2")));
        assertTrue(t5.getMoveStatus().isDone());
        
        MoveTransition t6 = t5.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t5.getToBoard(), BoardUtils.getCoordinateAtPos("d6"),
                        BoardUtils.getCoordinateAtPos("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        
        MoveTransition t7 = t6.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t6.getToBoard(), BoardUtils.getCoordinateAtPos("d1"),
                        BoardUtils.getCoordinateAtPos("e2")));
        assertTrue(t7.getMoveStatus().isDone());
        
        MoveTransition t8 = t7.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t7.getToBoard(), BoardUtils.getCoordinateAtPos("h7"),
                        BoardUtils.getCoordinateAtPos("h6")));
        assertTrue(t8.getMoveStatus().isDone());
        
        MoveTransition t9 = t8.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t8.getToBoard(), BoardUtils.getCoordinateAtPos("b1"),
                        BoardUtils.getCoordinateAtPos("c3")));
        assertTrue(t9.getMoveStatus().isDone());
        
        MoveTransition t10 = t9.getToBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveCreator.createMove(t9.getToBoard(), BoardUtils.getCoordinateAtPos("h6"),
                        BoardUtils.getCoordinateAtPos("h5")));
        assertTrue(t10.getMoveStatus().isDone());
        
        Move wm1 = Move.MoveCreator
                .createMove(t10.getToBoard(), BoardUtils.getCoordinateAtPos(
                        "e1"), BoardUtils.getCoordinateAtPos("c1"));
        assertTrue(t10.getToBoard().getCurrentPlayer().getLegalMoves().contains(wm1));
        MoveTransition t11 = t10.getToBoard().getCurrentPlayer().makeMove(wm1);
        assertTrue(t11.getMoveStatus().isDone());
    }
    
}
