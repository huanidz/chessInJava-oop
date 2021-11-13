/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.piece;

import chessproject.board.Board;
import chessproject.board.BoardUtils;
import chessproject.board.Move;
import chessproject.board.Spot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoain
 */
public class King extends Piece {

    public final static int[] HowCanThisMove = {-9, -8, -7 , -1 , 1, 7, 8 , 9};
    
    public King(int piecePos, Alliance pieceAlliance) {
        super(PieceType.KING,piecePos, pieceAlliance, true);
    }
    
    public King(int piecePos, Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.KING,piecePos, pieceAlliance, isFirstMove);
    }

    @Override
    public List<Move> PossibleMove(Board board) {
        
        final List<Move> legalMove = new ArrayList<>();
        
        int RealCoordinateMove;
        
        
        //Xây dựng tương tự như class Knight
        for(int i : HowCanThisMove)
        {
            RealCoordinateMove = this.piecePos + i;
            
            if(isOnFirstColumnException(this.piecePos, i) ||
               isOnEighthColumnException(this.piecePos, i))
               {
                  continue;
               }
            
            if(BoardUtils.isValidSpot(RealCoordinateMove)){
                Spot DestinationSpot = board.getSpot(RealCoordinateMove);
                
                //Nếu ô đã tồn tại 1 quân
                if(!DestinationSpot.isContainAnything())
                {
                    legalMove.add(new Move.MajorMove(board,this,RealCoordinateMove)); 
                }
                else //Trường hợp ô đích có tồn tại quân 
                {
                    //Lấy thông tin của Piece đang tồn tại ở ô đó
                    Piece tempPiece = DestinationSpot.getPiece();
                    Alliance tempAlliance = tempPiece.getPieceAlliance();
                    
                    //Trường hợp là Quân địch
                    if(this.pieceAlliance != tempAlliance)
                    {
                        legalMove.add(new Move.AttackMove(board, this, RealCoordinateMove, tempPiece));
                    }
                }
            }
          
        }

        
        return legalMove;
         
    }
    
    @Override
    public String toString()
    {
        return Piece.PieceType.KING.toString();
    }
    
    public static boolean isOnFirstColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.FIRST_COLUMN[currentPos] == true)
        {
            if(howThisMove == -9 || howThisMove == -1 || howThisMove == 7)
                return true;
        }
        return false;
    }
    
    @Override
    public King pieceMove(Move move) {
        return new King(move.getDestinationCoordinate(),move.getPieceMove().getPieceAlliance(),false);
    }
    
    public static boolean isOnEighthColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.EIGHTH_COLUMN[currentPos] == true)
        {
            if(howThisMove == -7 || howThisMove == 1 || howThisMove == 9)
                return true;
        }
        return false;
    }
}
