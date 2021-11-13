/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.piece;

import chessproject.board.Board;
import chessproject.board.BoardUtils;
import static chessproject.board.BoardUtils.isValidSpot;
import chessproject.board.Move;
import chessproject.board.Move.AttackMove;
import chessproject.board.Move.MajorMove;
import chessproject.board.Spot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoain
 */
public class Knight extends Piece {

    //Thuộc tính chỉ ra nơi mà quân này có thể di chuyển bằng cách cộng trừ vị trí các Spot từ Spot nó đang đứng
    public static final int[] HowCanThisMove = {-17, -15,-10, -6, 6, 10, 15, 17};
    
    public Knight(int piecePos, Alliance pieceAlliance) {
        super(PieceType.KNIGHT,piecePos, pieceAlliance, true);
    }
    
    public Knight(int piecePos, Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.KNIGHT,piecePos, pieceAlliance,isFirstMove);
    }

    
    @Override
    public List<Move> PossibleMove(Board board) {
        
        int RealCoordinateMove;
        List<Move> legalMove = new ArrayList<>();
        
        for(int i : HowCanThisMove)
        {
            //Tọa độ thực cộng với Offset để ra những vị trí thật trên bàn cờ có thể di chuyển
            RealCoordinateMove = this.piecePos + i;
            
            if(isValidSpot(RealCoordinateMove))
            {
                //Kiểm tra ô vs nước đi có nằm trong bàn cờ không, nếu không thì bỏ qua và continue
                //Kiểm tra các tình huống ngoại lệ
                if(isOnFirstColumnException(this.piecePos, i) ||
                   isOnSecondColumnException(this.piecePos, i) ||
                   isOnSeventhColumnException(this.piecePos, i) ||
                   isOnEighthColumnException(this.piecePos, i))
                {
                    continue;
                }
                
                //Kiểm tra ô đích (các ô RealCoordinateMove) xem có hợp lệ để đi tới ko
                Spot DestinationSpot = board.getSpot(RealCoordinateMove);
                
                //Nếu ô đã tồn tại 1 quân
                if(!DestinationSpot.isContainAnything())
                {
                    legalMove.add(new MajorMove(board,this,RealCoordinateMove)); 
                }
                else //Trường hợp ô đích có tồn tại quân 
                {
                    //Lấy thông tin của Piece đang tồn tại ở ô đó
                    Piece tempPiece = DestinationSpot.getPiece();
                    Alliance tempAlliance = tempPiece.getPieceAlliance();
                    
                    //Trường hợp là Quân địch
                    if(this.pieceAlliance != tempAlliance)
                    {
                        legalMove.add(new AttackMove(board, this, RealCoordinateMove, tempPiece));
                    }
                }
               
            }
        }
 
        return legalMove;
    }
    
    
    public String toString()
    {
        return Piece.PieceType.KNIGHT.toString();
    }
    
    @Override
    public Knight pieceMove(Move move) {
        return new Knight(move.getDestinationCoordinate(),move.getPieceMove().getPieceAlliance());
    }
    
    //Trường hợp ngoại lệ khi mã đứng ở các cạnh hoặc gần cạnh
    public static boolean isOnFirstColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.FIRST_COLUMN[currentPos] == true)
        {
            if(howThisMove == -17 || howThisMove == -10 || howThisMove == 6 || howThisMove == 15)
                return true;
        }
        return false;
    }
    
    public static boolean isOnSecondColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.SECOND_COLUMN[currentPos] == true)
        {
            if(howThisMove == -10 || howThisMove == 6)
                return true;
        }
        return false;
    }
    
    public static boolean isOnSeventhColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.SEVENTH_COLUMN[currentPos] == true)
        {
            if(howThisMove == 10 || howThisMove == -6)
                return true;
        }
        return false;
    }
    
    public static boolean isOnEighthColumnException(int currentPos, int howThisMove)
    {
        if(BoardUtils.EIGHTH_COLUMN[currentPos] == true)
        {
            if(howThisMove == -15 || howThisMove == -6 || howThisMove == 10 || howThisMove == 17)
                return true;
        }
        return false;
    }
    
}
