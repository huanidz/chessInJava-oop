/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.player;

import chessproject.board.Board;
import chessproject.board.Move;
import chessproject.board.Spot;
import chessproject.piece.Alliance;
import chessproject.piece.Piece;
import chessproject.piece.Rook;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoain
 */
public class BlackPlayer extends Player {

    public BlackPlayer(Board board, List<Move> whiteLegalMoves, List<Move> blackLegalMoves) {
        super(board, blackLegalMoves,whiteLegalMoves);
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.boardplay.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
         return Alliance.BLACK;
    }

    @Override
    public Player getEnemy() {
        return this.boardplay.getWhitePlayer();
    }

    @Override
    public String toString()
    {
        return "Black";
    }
    
    @Override
    public List<Move> calculateKingCastle(List<Move> playerLegalMoves, List<Move> enemyLegalMoves) {
        List<Move> kingCastles = new ArrayList<>();
        
        if(this.playerKing.isFirstMove() && !this.isInCheck())
        {
            
            //King side
            if(!this.boardplay.getSpot(5).isContainAnything() && !this.boardplay.getSpot(6).isContainAnything())
            {
                Spot SpotOfRook = this.boardplay.getSpot(7);
                
                if(SpotOfRook.isContainAnything() && SpotOfRook.getPiece().isFirstMove())
                {
                    if(Player.calculateAttackOnSpot(5, enemyLegalMoves).isEmpty() &&
                       Player.calculateAttackOnSpot(6, enemyLegalMoves).isEmpty() &&
                       SpotOfRook.getPiece().getPieceType() == Piece.PieceType.ROOK)
                    {
                        kingCastles.add(new Move.KingSideCastleMove(this.boardplay, this.playerKing, 6, (Rook)SpotOfRook.getPiece(), SpotOfRook.getSpotCoordinate(), 5));
                    }
                }
            }
            
            //Queen Side
            if(!this.boardplay.getSpot(1).isContainAnything() && !this.boardplay.getSpot(2).isContainAnything() && !this.boardplay.getSpot(3).isContainAnything())
            {
                Spot SpotOfRook = this.boardplay.getSpot(0);
                
                if(SpotOfRook.isContainAnything() && SpotOfRook.getPiece().isFirstMove())
                {
                    if(Player.calculateAttackOnSpot(2, enemyLegalMoves).isEmpty() &&
                       Player.calculateAttackOnSpot(3, enemyLegalMoves).isEmpty() &&
                       SpotOfRook.getPiece().getPieceType() == Piece.PieceType.ROOK)
                    {
                        kingCastles.add(new Move.QueenSideCastleMove(this.boardplay, this.playerKing, 2, (Rook)SpotOfRook.getPiece(), SpotOfRook.getSpotCoordinate(), 3));
                    }
                }
            }
            
        }

        return kingCastles;
    }
    
    
}
