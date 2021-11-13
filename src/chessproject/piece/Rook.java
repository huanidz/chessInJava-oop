
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
public class Rook extends Piece{

    public final static int[] HowCanThisMove = {-8, -1, 1 , 8};
    
    public Rook(int piecePos, Alliance pieceAlliance) {
        super(PieceType.ROOK,piecePos, pieceAlliance, true);
    }
    
    public Rook(int piecePos, Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.ROOK,piecePos, pieceAlliance, isFirstMove);
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
                    
                            //Tại đây nếu gặp ô đang chứa quân, các ô phía sau của đường thẳng đều ko cần tính toán, hay có thể hiểu do bị chặn đường
                            break;
                    }
                 }
             }
         }
         
         
         
         
         return legalMove;
    }
    
    public String toString()
    {
        return Piece.PieceType.ROOK.toString();
    }
    
    @Override
    public Rook pieceMove(Move move) {
        return new Rook(move.getDestinationCoordinate(),move.getPieceMove().getPieceAlliance());
    }
    
    public static boolean isOnFirstColumnException(int currentPos, int howThisMove)
    {
        return BoardUtils.FIRST_COLUMN[currentPos] && (howThisMove == -1);
    }
    
    public static boolean isOnEighthColumnException(int currentPos, int howThisMove)
    {
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (howThisMove == 1);
    }
    
}
