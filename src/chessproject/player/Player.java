    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.player;

import chessproject.board.*;
import chessproject.piece.King;
import chessproject.piece.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoain
 */
public abstract class Player {
    
    Board boardplay;
    King playerKing;
    List<Move> legalMoves;
    boolean isInCheck;
    String playerName;
    boolean isCastle;
    
    public Player(Board board, List<Move> playerlegalMoves, List<Move> enemyLegalMoves)
    {
     this.boardplay = board;
     this.playerKing = setUpKing();
     
     this.isInCheck = !calculateAttackOnSpot(this.playerKing.getPiecePos(), enemyLegalMoves).isEmpty();
     this.legalMoves = playerlegalMoves;
     this.legalMoves.addAll(calculateKingCastle(playerlegalMoves, enemyLegalMoves));
     this.isCastle = false;
    }

    
    @Override
    public int hashCode()
    {
        int code = 1;
        code = 31 * code;
        code = 31 * code + (this.isInCheck == true ? 69 : 96);
        return code;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(this == other)
        {
            return true;
        }
        if(!(other instanceof Player))
        {
            return false;
        }
        
        Player player = (Player)other;
        return (this.getAlliance() == player.getAlliance() &&
                this.getLegalMoves() == player.getLegalMoves() &&
                this.getPlayerKing() == player.getPlayerKing());
        
    }
    
    
    //Kiểm tra các nước tấn công của đối phương vào 1 ô
    public static List<Move> calculateAttackOnSpot(int piecePos, List<Move> enemyMoves)
    {
       List<Move> attackMoves = new ArrayList<>();
       for(Move move : enemyMoves)
       {
           if(piecePos == move.getDestinationCoordinate())
           {
               attackMoves.add(move);
           }
       }
       
       return attackMoves;
    }
    
    private King setUpKing() {
        
        //Duyệt các quân trên bàn cờ, nếu có Vua thì trả về vua, nếu không, bàn cờ không hợp lệ
        for(Piece piece : getActivePieces())
        {
            if(piece.getPieceType() == Piece.PieceType.KING)
            {
                return (King) piece;
            }
        }
        throw new RuntimeException("Bàn cờ bị sai, thiếu Vua");
    }
    
    //Kiểm tra nước đi có hợp lệ hay ko, nếu nằm trong List legalmove thì hợp lệ
    public boolean isMoveLegal(Move move)
    {
        return this.legalMoves.contains(move);
    }
    
    public boolean isInCheck()
    {
        return this.isInCheck;
    }
    
    public boolean isInCheckmate()
    {
        
        if(this.isInCheck && !canEscape()){
            System.out.println("CHECKMATE");
            if(this.getAlliance() == Alliance.WHITE) {  
                System.out.println("Black Win");
            }
            else
            {
                System.out.println("White Win");
            }
            return true;
        }
        else
            return false;
    }
    
    //Hàm kiểm tra xem vua có thể chạy đc ko
    public boolean canEscape() {
        
        //Chạy toàn bộ các nước đi có thể
        for(Move move : legalMoves)
        {
            MoveTransition trans = makeMove(move);
            
            //Nếu hoàn thành được nước đi đó, tức hợp lệ thì sẽ trả về true, hay Vua có thể chạy
            if(trans.getMoveStatus().isDone())
            {
                return true;
            }
        }
        //Nếu không được thực hiện được nước nào, trả về false
        return false;
    }
    
    public boolean isInStalemate()
    {
        return (!this.isInCheck && !canEscape());
    }
    
    public boolean isCastled()
    {
        return false;
    }

    public void setLegalMoves(List<Move> legalMoves) {
        this.legalMoves = legalMoves;
    }
    
    
    //Hàm đưa ra nước đi
    public MoveTransition makeMove(Move move)
    {
        //Nếu nước đi đó không hợp lệ, trả về 1 bàn với thuộc tính của move đó là Illegal
        if(!isMoveLegal(move))
        {
            //return new MoveTransition(this.boardplay, move, MoveChecker.ILLEGAL_MOVE);
            return new MoveTransition(this.boardplay,this.boardplay, move, MoveChecker.ILLEGAL_MOVE);
        }
        
        //Các trường hợp còn lại, tác động lên một bàn trung gian
        Board tempBoard = move.runMove();
        
        //Với người chơi đang ở lượt này, khi chạy (runMove) 1 nước, ta kiểm tra xem Vua của đối phương có bị đưa vào trạng thái chiếu ko
        List<Move> kingAttackMoves = Player.calculateAttackOnSpot(tempBoard.getCurrentPlayer().getEnemy().getPlayerKing().getPiecePos(),
                tempBoard.getCurrentPlayer().getLegalMoves());
        
        //Nếu List trên có tồn tại, tức vua của người đối diện bị chiếu sau nước vừa đi
        if(!kingAttackMoves.isEmpty())
        {
            //return new MoveTransition(this.boardplay, move, MoveChecker.LEAVE_PLAYER_IN_CHECK);
            return new MoveTransition(this.boardplay,this.boardplay, move, MoveChecker.LEAVE_PLAYER_IN_CHECK);
        }
        //Nếu không, nước đi được hoàn thành như bình thường
        return new MoveTransition(this.boardplay,tempBoard, move, MoveChecker.DONE);
    }

    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setIsCastle(boolean isCastle) {
        this.isCastle = isCastle;
    }
    
    public abstract List<Piece> getActivePieces();
    
    public abstract Alliance getAlliance();
    
    public abstract Player getEnemy();
    
    public abstract List<Move> calculateKingCastle(List<Move> playerLegalMoves, List<Move> enemyLegalMoves);

}
