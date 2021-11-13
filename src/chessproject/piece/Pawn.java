/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.piece;

import chessproject.board.Board;
import chessproject.board.BoardUtils;
import chessproject.board.Move;
import chessproject.board.Move.PawnAttackMove;
import chessproject.board.Move.PawnJump;
import chessproject.board.Move.PawnMove;
import chessproject.board.Move.PawnPromoteMove;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author hoain
 */
public class Pawn extends Piece {

    public static int[] HowCanThisMove = {8,16 ,7,9};
    
    public Pawn(int piecePos, Alliance pieceAlliance) {
        super(PieceType.PAWN,piecePos, pieceAlliance, true);
    }
    
    public Pawn(int piecePos, Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.PAWN,piecePos, pieceAlliance, isFirstMove);
    }

    
    @Override
    public List<Move> PossibleMove(Board board) {
        
        final List<Move> legalMove = new ArrayList<>();
        
        for(int i : HowCanThisMove)
        {
            int RealCoordinateMove = this.piecePos + i*this.pieceAlliance.getDirection();
            
            if(!BoardUtils.isValidSpot(RealCoordinateMove))
            {
                continue;
            }
            
            //Đi 1 nước, nước đó đi đến ô trống
            if(i == 8 && !board.getSpot(RealCoordinateMove).isContainAnything())
            {
                if(this.pieceAlliance.isPawnPromotionSpot(RealCoordinateMove))
                {
                    legalMove.add(new PawnPromoteMove(new PawnMove(board, this, RealCoordinateMove)));
                }
                else
                {
                    legalMove.add(new PawnMove(board,this,RealCoordinateMove));
                }   
            }
            //Đi 2 nước ở bước đầu tiên
            else if(i == 16 && this.isFirstMove() && 
                    ((BoardUtils.SEVENTH_RANK[this.piecePos] && this.getPieceAlliance().isBlack()) || 
                    (BoardUtils.SECOND_RANK[this.piecePos] && this.getPieceAlliance().isWhite()))  )
            {
                
                 int behindRealCoordinateMove = this.piecePos + (this.pieceAlliance.getDirection()*8);
                 
                 //Kiểm tra xem cả 2 ô (ô đi đến và ô trước ô đi đến) có chứa quân nào ko
                 if(!board.getSpot(behindRealCoordinateMove).isContainAnything() 
                         && !board.getSpot(RealCoordinateMove).isContainAnything() )
                 {
                      legalMove.add(new PawnJump(board,this,RealCoordinateMove));
                 }
                
            }
            
            //Ăn chéo, kiểm tra luôn các trương hợp ngoại lệ cho mỗi bên
            else if(i == 7 && !((BoardUtils.EIGHTH_COLUMN[this.piecePos] && this.pieceAlliance.isWhite()) 
                            || (BoardUtils.FIRST_COLUMN[this.piecePos] && this.pieceAlliance.isBlack())) )
            {
                if(board.getSpot(RealCoordinateMove).isContainAnything())
                {
                    Piece tempPiece = board.getSpot(RealCoordinateMove).getPiece();
                    if(this.pieceAlliance != tempPiece.pieceAlliance)
                    {
                        if(this.pieceAlliance.isPawnPromotionSpot(RealCoordinateMove))
                        {
                            legalMove.add(new PawnPromoteMove(new PawnAttackMove(board,this,RealCoordinateMove,tempPiece)));
                        }
                        else
                        {
                            legalMove.add(new PawnAttackMove(board,this,RealCoordinateMove,tempPiece));
                        }
                    }
                }
                //En PASSANT
                 else if(board.getEnPassantPawn() != null)
                {
                    if(board.getEnPassantPawn().getPiecePos() == (this.piecePos + this.pieceAlliance.getOppositeDirection()))
                    {
                        Piece pieceGotEnPassant = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceGotEnPassant.pieceAlliance)
                        {
                            legalMove.add(new Move.PawnEnpassantAttackMove(board, this, RealCoordinateMove, pieceGotEnPassant));
                        }
                    }
                }
                
            }
            else if(i == 9 && !((BoardUtils.FIRST_COLUMN[this.piecePos] && this.pieceAlliance.isWhite()) 
                            || (BoardUtils.EIGHTH_COLUMN[this.piecePos] && this.pieceAlliance.isBlack())) )
            {
                if(board.getSpot(RealCoordinateMove).isContainAnything())
                {
                    Piece tempPiece = board.getSpot(RealCoordinateMove).getPiece();
                    if(this.pieceAlliance != tempPiece.pieceAlliance)
                    {
                        if(this.pieceAlliance.isPawnPromotionSpot(RealCoordinateMove))
                        {
                            legalMove.add(new PawnPromoteMove(new PawnAttackMove(board,this,RealCoordinateMove,tempPiece)));
                        }
                        else
                        {
                            legalMove.add(new PawnAttackMove(board,this,RealCoordinateMove,tempPiece));
                        }   
                    }
                }
                //En PASSANT
                 else if(board.getEnPassantPawn() != null)
                {
                    if(board.getEnPassantPawn().getPiecePos() == (this.piecePos - this.pieceAlliance.getOppositeDirection()))
                    {
                        Piece pieceGotEnPassant = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceGotEnPassant.pieceAlliance)
                        {
                            legalMove.add(new Move.PawnEnpassantAttackMove(board, this, RealCoordinateMove, pieceGotEnPassant));
                        }
                    }
                }
            } 
        }
        
        return legalMove; 
    }
    
    @Override
    public String toString()
    {
        return Piece.PieceType.PAWN.toString();
    }
    
    @Override
    public Pawn pieceMove(Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getPieceMove().getPieceAlliance());
    }
    
    public Piece getPromotionPiece()
    {
        String[] options = new String[]{"Queen", "Rook", "Knight", "Bishop"};
        int value = JOptionPane.showOptionDialog(null, "Chọn quân muốn phong", "Chọn quân phong", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        switch(value)
        {
            case 0:
                return new Queen(this.piecePos,this.pieceAlliance,false);
            case 1:
                return new Rook(this.piecePos,this.pieceAlliance,false);
            case 2:
                return new Knight(this.piecePos,this.pieceAlliance,false);
            case 3:
                return new Bishop(this.piecePos,this.pieceAlliance,false);
        }
        return new Queen(this.piecePos,this.pieceAlliance,false);
    }
}
