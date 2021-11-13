        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;

import chessproject.board.Board.BoardBuilder;
import chessproject.piece.Pawn;
import chessproject.piece.Piece;
import chessproject.piece.Rook;

/**
 *
 * @author hoain
 */
public abstract class Move {
    
    public Board board;
    public Piece pieceMove;
    public int DestinationCoordinate;
    public boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    public Move(Board board, Piece pieceMove, int DestinationCoordinate) {
        this.board = board;
        this.pieceMove = pieceMove;
        this.DestinationCoordinate = DestinationCoordinate;
        this.isFirstMove = pieceMove.isFirstMove();
    }
    
    public Move(Board board, int DestinationCoordinate) {
        this.board = board;
        this.pieceMove = null;
        this.DestinationCoordinate = DestinationCoordinate;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode()
    {
        int code = 1;
        code = 31 * code + this.DestinationCoordinate;
        code = 31 * code + this.pieceMove.hashCode();
        code = 31 * code + this.pieceMove.getPiecePos();
        return code;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(this == other)
        {
            return true;
        }
        if(!(other instanceof Move))
        {
            return false;
        }
        
        Move move = (Move)other;
        return (this.getCurrentCoordinate() == move.getCurrentCoordinate() &&
                this.getDestinationCoordinate() == move.getDestinationCoordinate() &&
                this.getPieceMove().equals(move.getPieceMove()));
        
    }
    
    
    public boolean isAttack()
    {
        return false;
    }
    
    public boolean isCastleMove()
    {
        return false;
    }
    
    public Piece getAttackedPiece()
    {
        return null;
    }
    
    public Board runMove()
    {
        //build một Board mới, nước đi sẽ diễn ra trên board đó (hợp lệ, ko hợp lệ, check, ..) và sẽ gán lại cho board chính
            Board.BoardBuilder builder = new Board.BoardBuilder(); 
            
            //Dùng builder, duyệt qua toàn bộ các quân còn trên bàn của người chơi lượt hiện tại để lọc ra quân mà ng chơi di chuyển
            //Các quân ko phải quân di chuyển sẽ được set lại như cũ
            for(Piece piece : this.board.currentPlayer.getActivePieces())
            {
                if(!this.pieceMove.equals(piece))
                {
                    builder.setPiece(piece);
                }
            }
            
            //Quân của đối phương sẽ được set lại như cũ toàn bộ
            for(Piece piece : this.board.currentPlayer.getEnemy().getActivePieces())
            {
                builder.setPiece(piece);
            }

            //Set piece (các thong tin) cho quân di chuyển
            builder.setPiece(this.pieceMove.pieceMove(this));
            
            //Chuyển lượt của người hiện tại sang người kia
            builder.setMoveTurn(this.board.currentPlayer.getEnemy().getAlliance());
            
            
            return builder.build();    
    }


    //Nước đi thông thường
    public static class MajorMove extends Move {

        public MajorMove(Board board, Piece pieceMove, int DestinationCoordinate) {
            super(board, pieceMove, DestinationCoordinate);
        } 
        
        @Override
        public boolean equals (Object other)
        {
            return this == other || other instanceof MajorMove && super.equals(other);
        }
        
        @Override
        public String toString()
        {
            return pieceMove.getPieceType().toString() + BoardUtils.getPosAtCoordinate(this.getDestinationCoordinate());
        }
    }
    
    //Nước đi tấn công
    public static class AttackMove extends Move{

        public Piece AttackedPiece;
        public AttackMove(Board board, Piece pieceMove, int DestinationCoordinate, Piece AttackedPiece) {
            super(board, pieceMove, DestinationCoordinate);
            this.AttackedPiece = AttackedPiece;
        }
        
        @Override
        public int hashCode()
        {
            return this.AttackedPiece.hashCode() + super.hashCode();
        }
        
        @Override
        public boolean equals(Object other)
        {
            if(this == other)
            {
                return true;
            }
            if(!(other instanceof AttackMove))
            {
                return false;
            }
            
            AttackMove attackmove = (AttackMove)other;
            
            return super.equals(attackmove) && getAttackedPiece().equals(attackmove.getAttackedPiece());
        }
        
        @Override
        public boolean isAttack()
        {
            return true;
        }
        
        @Override
        public Piece getAttackedPiece()
        {
            return this.AttackedPiece;
        }
        
        @Override
        public String toString()
        {
            return this.pieceMove.toString() + "x" + BoardUtils.getPosAtCoordinate(DestinationCoordinate);
        }
    }
    
    //Nuoc di cua Tot
    public static class PawnMove extends Move {

        public PawnMove(Board board, Piece pieceMove, int DestinationCoordinate) {
            super(board, pieceMove, DestinationCoordinate);
        }    
        
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }
        
        @Override
        public String toString()
        {
            return BoardUtils.getPosAtCoordinate(DestinationCoordinate);
        }
    }
    
    public static class PawnPromoteMove extends Move{
        
        Move WrappedMove;
        Pawn promotedPawn;
        
        public PawnPromoteMove(Move WrappedMove)
        {
            super(WrappedMove.getBoard(),WrappedMove.getPieceMove(),WrappedMove.getDestinationCoordinate());
            this.WrappedMove = WrappedMove;
            this.promotedPawn = (Pawn)WrappedMove.getPieceMove();
        }
        
        @Override
        public int hashCode()
        {
            return this.WrappedMove.hashCode() + 31*promotedPawn.hashCode();
        }
        
        @Override
        public boolean equals(Object other)
        {
            return this == other || other instanceof PawnPromoteMove && this.WrappedMove.equals(other);
        }
        
        @Override
        public Board runMove()
        {
            Board pawnMoveBoard = this.WrappedMove.runMove();    
            BoardBuilder builder = new BoardBuilder();
            for(Piece piece : pawnMoveBoard.getCurrentPlayer().getActivePieces())
            {
                if(!this.promotedPawn.equals(piece))
                {
                    builder.setPiece(piece);
                }
            }
            for(Piece piece : pawnMoveBoard.getCurrentPlayer().getEnemy().getActivePieces())
            {
                builder.setPiece(piece);
            }
            
            builder.setPiece(this.promotedPawn.getPromotionPiece().pieceMove(this));
            builder.setMoveTurn(pawnMoveBoard.getCurrentPlayer().getAlliance());
            
            return builder.build();
        }
        
        @Override
        public boolean isAttack()
        {
            return this.WrappedMove.isAttack();
        }
        
        @Override
        public Piece getAttackedPiece()
        {
            return this.WrappedMove.getAttackedPiece();
        }
        
        @Override
        public String toString()
        {
            return BoardUtils.getPosAtCoordinate(DestinationCoordinate)+"=Q";  
        }
    }
    
    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(Board board, Piece pieceMove, int DestinationCoordinate, Piece AttackedPiece) {
            super(board, pieceMove, DestinationCoordinate,AttackedPiece);
        }    
        
        @Override
        public boolean equals(Object other)
        {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }
        
        @Override
        public String toString()
        {
            return BoardUtils.getPosAtCoordinate(this.pieceMove.getPiecePos()).substring(0,1)+"x"+BoardUtils.getPosAtCoordinate(DestinationCoordinate);
        }
    }
    
    public static class PawnEnpassantAttackMove extends AttackMove {

        public PawnEnpassantAttackMove(Board board, Piece pieceMove, int DestinationCoordinate, Piece AttackedPiece) {
            super(board, pieceMove, DestinationCoordinate,AttackedPiece);
        }    
        
        @Override
        public boolean equals(Object other)
        {
            return this == other || other instanceof PawnEnpassantAttackMove && super.equals(other);
        }
        
        @Override
        public Board runMove()
        {
            BoardBuilder builder = new BoardBuilder();
            for(Piece piece : this.board.getCurrentPlayer().getActivePieces())
            {
                if(!this.pieceMove.equals(piece))
                {
                    builder.setPiece(piece);
                }
            }
            
            for(Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces())
            {
                if(!piece.equals(this.getAttackedPiece()))
                {
                    builder.setPiece(piece);
                }
            }
            
            builder.setPiece(this.pieceMove.pieceMove(this));
            builder.setMoveTurn(this.board.getCurrentPlayer().getEnemy().getAlliance());
            
            return builder.build();
        }
    }
    
    public static class PawnJump extends Move {

        public PawnJump(Board board, Piece pieceMove, int DestinationCoordinate) {
            super(board, pieceMove, DestinationCoordinate);
        }
        
        @Override
        public Board runMove()
        {
            Board.BoardBuilder builder = new BoardBuilder();
            
            for(Piece piece : this.board.getCurrentPlayer().getActivePieces())
            {
                if(!(this.pieceMove.equals(piece)))
                {
                    builder.setPiece(piece);
                }
            }
            
            for(Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces())
            {
                builder.setPiece(piece);
            }
            
            Pawn pawnMove = (Pawn)this.pieceMove.pieceMove(this);
            builder.setPiece(pawnMove);
            builder.setEnpassantPawn(pawnMove);
            
            builder.setMoveTurn(this.board.getCurrentPlayer().getEnemy().getAlliance());
            return builder.build();
        }
        
        @Override
        public String toString()
        {
            return BoardUtils.getPosAtCoordinate(DestinationCoordinate);
        }
    } 
    
    public static abstract class CastleMove extends Move {

    Rook castleRook;
    int castleRookStart;
    int castleRookDestination;
        
        
    public CastleMove(Board board, Piece pieceMove, int DestinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, pieceMove, DestinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        } 
    public Rook getCastleRook()
    {
        return this.castleRook;
    }
    
    @Override
    public boolean isCastleMove()
    {
        return true;
    }
    
    @Override
    public Board runMove()
    {
        Board.BoardBuilder builder = new BoardBuilder();
        for(Piece piece : this.board.getCurrentPlayer().getActivePieces())
        {
            if(!(this.pieceMove.equals(piece)) && !this.castleRook.equals(piece))
            {
                builder.setPiece(piece);
            }
        }
            
        for(Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces())
        {
            builder.setPiece(piece);
        }
   
        builder.setPiece(this.pieceMove.pieceMove(this));
        builder.setPiece(new Rook(this.castleRookDestination,this.castleRook.getPieceAlliance()));
        board.getCurrentPlayer().setIsCastle(true);
        builder.setMoveTurn(this.board.getCurrentPlayer().getEnemy().getAlliance());
        
        return builder.build();
    }
    
    @Override
    public int hashCode()
    {
        int code = super.hashCode();
        code = 31 * code + this.castleRook.hashCode();
        code = 31 * code + this.castleRookDestination;
        return code;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(this == other)
        {
            return true;
        }
        if(!(other instanceof CastleMove))
        {
            return false;
        }
        CastleMove OtherCastlemove = (CastleMove)other;
        return super.equals(OtherCastlemove) && this.castleRook.equals(OtherCastlemove.getCastleRook());
    }
    
    
    }
    
    public static class KingSideCastleMove extends CastleMove {

    public KingSideCastleMove(Board board, Piece pieceMove, int DestinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, pieceMove, DestinationCoordinate, castleRook,castleRookStart,castleRookDestination);
        } 
    
    @Override
    public boolean equals(Object other)
    {
        return this == other || other instanceof KingSideCastleMove && super.equals(other);
    }
    
    
    @Override
    public String toString()
    {
        return "O-O";
    }
    
    }
    
    public static class QueenSideCastleMove extends CastleMove {

    public QueenSideCastleMove(Board board, Piece pieceMove, int DestinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, pieceMove, DestinationCoordinate, castleRook,castleRookStart,castleRookDestination);
        } 
    
    @Override
    public boolean equals(Object other)
    {
        return this == other || other instanceof KingSideCastleMove && super.equals(other);
    }
    
    @Override
    public String toString()
    {
        return "O-O-O";
    }
    
    }
    
    public static class NullMove extends Move {

        public NullMove() {
                super(null, 65);
            } 
        @Override
        public Board runMove(){
            throw new RuntimeException("Null Move !!");
        }

        @Override
        public int getCurrentCoordinate(){
            return -1;
        }

        @Override
        public int getDestinationCoordinate() {
            return -1;
        }
    }    
    
    public static class MoveCreator{
        
        public static Move NULL_MOVE = new NullMove();
        
        public MoveCreator()
        {
            throw new RuntimeException("Class ko the khoi tao");
        }
        
        public static Move createMove(Board board, int currentCoordinate, int DestinationCoordinate)
        {
            for(Move move : board.getAllLegalMoves())
            {
                if(move.getCurrentCoordinate() == currentCoordinate && 
                   move.getDestinationCoordinate() == DestinationCoordinate)
                {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

    
    public int getDestinationCoordinate() {
        return DestinationCoordinate;
    }

    public Piece getPieceMove() {
        return pieceMove;
    }
   
    public int getCurrentCoordinate() {
        return this.pieceMove.getPiecePos();
    }

    public Board getBoard() {
        return board;
    }

}
