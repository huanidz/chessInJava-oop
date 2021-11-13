/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;

import chessproject.board.Board.BoardBuilder;
import static chessproject.board.Board.*;
import chessproject.piece.Alliance;
import chessproject.piece.Pawn;
import chessproject.piece.Piece;
import chessproject.piece.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hoain
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void NormalBoard() {

        Board board = createNormalBoard();
        
        assertEquals(board.getCurrentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.getCurrentPlayer().getEnemy().getLegalMoves().size(), 20);
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckmate());
        assertFalse(board.getCurrentPlayer().isCastled());
        
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrentPlayer().getEnemy(), board.getBlackPlayer());
        assertFalse(board.getCurrentPlayer().getEnemy().isInCheck());
        assertFalse(board.getCurrentPlayer().getEnemy().isInCheckmate());
        assertFalse(board.getCurrentPlayer().getEnemy().isCastled());

        assertTrue(board.getWhitePlayer().toString().equals("White"));
        assertTrue(board.getBlackPlayer().toString().equals("Black"));

        List<Piece> allPieces = board.getWhitePieces();
        allPieces.addAll(board.getBlackPieces());
        List<Move> allMoves = board.getAllLegalMoves();
        for(Move move : allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastleMove());
        }
        
        assertEquals(allMoves.size(), 40);
        assertEquals(allPieces.size(), 32);
        assertEquals(board.getSpot(35).getPiece(), null);
    }
    
    @Test(expected=RuntimeException.class)
    public void testInvalidBoard() {

        BoardBuilder builder = new BoardBuilder();
        // Black 
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        builder.setPiece(new Pawn(12,Alliance.BLACK));
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));
        
        // White 
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setPiece(new Pawn(49,Alliance.WHITE));
        builder.setPiece(new Pawn(50,Alliance.WHITE));
        builder.setPiece(new Pawn(51,Alliance.WHITE));
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));

        builder.setMoveTurn(Alliance.WHITE);
        
        builder.build();
    }

    @Test
    public void testPosAtCoord() {
        assertEquals(BoardUtils.getPosAtCoordinate(0), "a8");
        assertEquals(BoardUtils.getPosAtCoordinate(1), "b8");
        assertEquals(BoardUtils.getPosAtCoordinate(2), "c8");
        assertEquals(BoardUtils.getPosAtCoordinate(3), "d8");
        assertEquals(BoardUtils.getPosAtCoordinate(4), "e8");
        assertEquals(BoardUtils.getPosAtCoordinate(5), "f8");
        assertEquals(BoardUtils.getPosAtCoordinate(6), "g8");
        assertEquals(BoardUtils.getPosAtCoordinate(7), "h8");
    }

}
