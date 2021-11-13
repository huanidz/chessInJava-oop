/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;

import chessproject.board.Board.*;
import chessproject.board.Move.MoveCreator;
import chessproject.piece.*;
import chessproject.player.MoveTransition;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hoain
 */
public class PieceTest {
    
    @Test
    public void CenterQueen() {
        BoardBuilder builder = new BoardBuilder();
        // Black 
        builder.setPiece(new King(4,Alliance.BLACK, false));
        
        // White 
        builder.setPiece(new Queen(36,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false));
        
        // Set player
        builder.setMoveTurn(Alliance.WHITE);
        
        Board board = builder.build();
        List<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        List<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 31);
        assertEquals(blackLegals.size(), 5);
        
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("h4"))));
    }
    
    @Test
    public void CornerKnight() {
        BoardBuilder boardBuilder = new BoardBuilder();
        
        boardBuilder.setPiece(new King(4,Alliance.BLACK, false));
        boardBuilder.setPiece(new Knight(0,Alliance.BLACK));
        
        boardBuilder.setPiece(new Knight(56,Alliance.WHITE));
        boardBuilder.setPiece(new King(60,Alliance.WHITE, false));
        
        boardBuilder.setMoveTurn(Alliance.WHITE);
        
        Board board = boardBuilder.build();
        List<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        List<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 7);
        assertEquals(blackLegals.size(), 7);
        
        Move wm1 = Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("a1"), BoardUtils.getCoordinateAtPos("b3"));
        Move wm2 = Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("a1"), BoardUtils.getCoordinateAtPos("c2"));
        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        
        Move bm1 = Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("a8"), BoardUtils.getCoordinateAtPos("b6"));
        Move bm2 = Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("a8"), BoardUtils.getCoordinateAtPos("c7"));
        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }
    
    @Test
    public void CenterBishop() {
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new King(4,Alliance.BLACK, false));
        
        builder.setPiece(new Bishop(35,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false));
       
        builder.setMoveTurn(Alliance.WHITE);
        
        Board board = builder.build();
        
        List<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        List<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("a7"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("b6"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("c5"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("f2"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("d4"), BoardUtils.getCoordinateAtPos("g1"))));
    }
    
    @Test
    public void CenterKnight(){
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new King(4, Alliance.BLACK, false));
        builder.setPiece(new Knight(26, Alliance.BLACK));
        
        builder.setPiece(new King(60, Alliance.WHITE, false));
        builder.setPiece(new Knight(37, Alliance.WHITE));
        
        builder.setMoveTurn(Alliance.WHITE);
        Board board = builder.build();
        
        List<Move> whiteMoves = board.getWhitePlayer().getLegalMoves();
        List<Move> blackMoves = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteMoves.size(), 13);
        assertEquals(blackMoves.size(), 13);
        
        assertTrue(blackMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("c5"), BoardUtils.getCoordinateAtPos("d3"))));
        assertTrue(blackMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("c5"), 41)));
        assertTrue(blackMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("c5"), 32)));
        assertTrue(blackMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("c5"), 16)));
        assertTrue(blackMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("c5"), 9)));
        
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("f4"), 52)));
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("f4"), 54)));
    }
    
    @Test
    public void CenterRook() {
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new King(4, Alliance.BLACK, false));
        
        builder.setPiece(new Rook(36,Alliance.WHITE));
        builder.setPiece(new King(60, Alliance.WHITE, false));
       
        builder.setMoveTurn(Alliance.WHITE);
        Board board = builder.build();
        
        List<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        List<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("e4"), BoardUtils.getCoordinateAtPos("h4"))));
    }
    
    @Test
    public void PromotedPawn() {
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new Rook(3,Alliance.BLACK));
        builder.setPiece(new King(22, Alliance.BLACK, false));
        
        builder.setPiece(new Pawn(15,Alliance.WHITE));
        builder.setPiece(new King(52, Alliance.WHITE, false));
        
        builder.setMoveTurn(Alliance.WHITE);
        
        Board board = builder.build();
        
        Move m1 = Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h7"), BoardUtils.getCoordinateAtPos("h8"));
        
        MoveTransition trans1 = board.getCurrentPlayer().makeMove(m1);
        assertTrue(trans1.getMoveStatus().isDone());
        
        Move m2 = Move.MoveCreator
                .createMove(trans1.getToBoard(), BoardUtils.getCoordinateAtPos("d8"), BoardUtils.getCoordinateAtPos("h8"));
        
        MoveTransition trans2 = trans1.getToBoard().getCurrentPlayer().makeMove(m2);
        assertTrue(trans2.getMoveStatus().isDone());
    }
    
    @Test
    public void CornerBishop() {
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new King(4,Alliance.BLACK, false));
        
        builder.setPiece(new Bishop(63,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false));
        
        builder.setMoveTurn(Alliance.WHITE);
        
        Board board = builder.build();
        List<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        List<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveCreator
                .createMove(board, BoardUtils.getCoordinateAtPos("h1"), BoardUtils.getCoordinateAtPos("a8"))));
    }
    
    @Test
    public void CornerQueen(){
        BoardBuilder builder = new BoardBuilder();
        
        builder.setPiece(new King(4, Alliance.BLACK, false));
        
        builder.setPiece(new King(60, Alliance.WHITE, false));
        builder.setPiece(new Queen(63, Alliance.WHITE));
        
        builder.setMoveTurn(Alliance.WHITE);
        
        Board board = builder.build();
        
        List<Move> whiteMoves = board.getWhitePlayer().getLegalMoves();
        List<Move> blackMoves = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteMoves.size(), 21);
        assertEquals(blackMoves.size(), 5);
        
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, 63,62 )));
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, 63,18 )));
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, 63,45 )));
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, 63,23 )));
        assertTrue(whiteMoves.contains(MoveCreator
                .createMove(board, 63,7 )));
    }
}
    
