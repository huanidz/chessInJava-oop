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
public class WhitePlayer extends Player {

    public WhitePlayer(Board board, List<Move> whiteLegalMoves, List<Move> blackLegalMoves) {
       super(board,whiteLegalMoves,blackLegalMoves);
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.boardplay.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getEnemy() {
        return this.boardplay.getBlackPlayer();
    }

    @Override
    public String toString()
    {
        return "White";
    }
    
    @Override
    public List<Move> calculateKingCastle(List<Move> playerLegalMoves, List<Move> enemyLegalMoves) {
        List<Move> kingCastles = new ArrayList<>();
        
        if(this.playerKing.isFirstMove() && !this.isInCheck())
        {
            
            //King side
            if(!this.boardplay.getSpot(61).isContainAnything() && !this.boardplay.getSpot(61).isContainAnything())
            {
                Spot SpotOfRook = this.boardplay.getSpot(63);
                
                if(SpotOfRook.isContainAnything() && SpotOfRook.getPiece().isFirstMove())
                {
                    if(Player.calculateAttackOnSpot(61, enemyLegalMoves).isEmpty() &&
                       Player.calculateAttackOnSpot(62, enemyLegalMoves).isEmpty() &&
                       SpotOfRook.getPiece().getPieceType() == Piece.PieceType.ROOK)
                    {
                        kingCastles.add(new Move.KingSideCastleMove(this.boardplay, this.playerKing, 62, (Rook)SpotOfRook.getPiece(), SpotOfRook.getSpotCoordinate(), 61));
                    }
                }
            }
            
            //Queen Side
            if(!this.boardplay.getSpot(57).isContainAnything() && !this.boardplay.getSpot(58).isContainAnything() && !this.boardplay.getSpot(59).isContainAnything())
            {
                Spot SpotOfRook = this.boardplay.getSpot(56);
                
                if(SpotOfRook.isContainAnything() && SpotOfRook.getPiece().isFirstMove())
                {
                    if(Player.calculateAttackOnSpot(58, enemyLegalMoves).isEmpty() &&
                       Player.calculateAttackOnSpot(59, enemyLegalMoves).isEmpty() &&
                       SpotOfRook.getPiece().getPieceType() == Piece.PieceType.ROOK)
                    {
                        kingCastles.add(new Move.QueenSideCastleMove(this.boardplay, this.playerKing, 58, (Rook)SpotOfRook.getPiece(), SpotOfRook.getSpotCoordinate(), 59));
                    }
                }
            }
            
        }

        return kingCastles;
    }
    
}
