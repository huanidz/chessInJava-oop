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
public class Queen extends Piece {

    //Hợp của Rook và Bishop
    public final static int[] HowCanThisMove = {-9, -8 ,-7 , -1, 1, 7 , 8 , 9};
    
    public Queen(int piecePos, Alliance pieceAlliance) {
        super(PieceType.QUEEN,piecePos, pieceAlliance, true);
    }
    
    public Queen(int piecePos, Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.QUEEN,piecePos, pieceAlliance, isFirstMove);
    }


    @Override
    public List<Move> PossibleMove(Board board) {
         final List<Move> legalMove = new ArrayList<>();
         
         for(int i : HowCanThisMove)
         {
             int RealCoordinateMove = this.piecePos;
             
             while(BoardUtils.isValidSpot(RealCoordinateMove)){
                 
                 //2 Trường hợp ngoại lệ
                 if(isOnFirstColumnException(RealCoordinateMove, i) ||
                    isOnEighthColumnException(RealCoordinateMove, i))
                 {
                     break;
                 }
                 
                 RealCoordinateMove += i;
                 if(BoardUtils.isValidSpot(RealCoordinateMove))
                 {
                    //Kiểm tra ô đích (các ô RealCoordinateMove) xem có hợp lệ để đi tới ko
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
                    
                            //Tại đây nếu gặp ô đang chứa quân, các ô phía sau của đường chéo hay đường thẳng đều ko cần tính toán, hay có thể hiểu do bị chặn đường
                            break;
                    }
                 }
             }
         }
         
         return legalMove;
    }
    
    public String toString()
    {
        return Piece.PieceType.QUEEN.toString();
    }
    
    @Override
    public Queen pieceMove(Move move) {
        return new Queen(move.getDestinationCoordinate(),move.getPieceMove().getPieceAlliance());
    }
    
    public static boolean isOnFirstColumnException(int currentPos, int howThisMove)
    {
        return BoardUtils.FIRST_COLUMN[currentPos] && (howThisMove == -9 || howThisMove == 7 || howThisMove == -1);
    }
    
    public static boolean isOnEighthColumnException(int currentPos, int howThisMove)
    {
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (howThisMove == -7 || howThisMove == 9 || howThisMove == 1);
    }
    
}
