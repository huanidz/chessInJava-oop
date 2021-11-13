/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.piece;
import chessproject.board.*;
import java.util.List;
/**
 *
 * @author hoain
 */
public abstract class Piece {
    
    int piecePos;
    Alliance pieceAlliance;
    boolean isFirstMove;
    PieceType pieceType;
    int pieceHashCode;
    
    public Piece(PieceType pieceType, int piecePos, Alliance pieceAlliance, boolean isFirstMove)
    {
        this.pieceType = pieceType;
        this.piecePos = piecePos;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.pieceHashCode = calculateHashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        //2 điều kiện kiểm tra ban đầu
        
        //Nếu đã trùng, return true
        if(this == other)
        {
            return true;
        }
        
        //Nếu other không phải là một biến của kiểu Piece, trả về false
        if(!(other instanceof Piece))
        {
            return false;
        }
        
        Piece otherPiece = (Piece)other;
        
        //Kiểm tra mọi thuộc tính của lớp Piece giữa 2 piece, nếu ok thì trả về true
        return (this.pieceAlliance == otherPiece.getPieceAlliance() && this.piecePos == otherPiece.getPiecePos() &&
                this.isFirstMove == otherPiece.isFirstMove()        && this.pieceType == otherPiece.getPieceType());
    }
    
    
    public int calculateHashCode() {
        //tính toán hashcode cho 1 piece, dựa vào toàn bộ các thuộc tính của piece đó
        int code = pieceType.hashCode();
        
        code = 31 * code + pieceAlliance.hashCode();
        code = 31 * code + piecePos;
        code = 31 * code + (isFirstMove ? 1 : 0);
        
        return code;
    }
    
    @Override
    public int hashCode()
    {
        return this.pieceHashCode;
    }
    
    public int getPiecePos() {
        return piecePos;
    }


    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
    
    public boolean isFirstMove()
    {
        return this.isFirstMove;
    }

    public int getPieceValue()
    {
        return this.pieceType.getPieceValue();
    }
    
    public enum PieceType{
        
        PAWN(1,"P"),
        KNIGHT(3,"N"),
        BISHOP(3,"B"),
        ROOK(5,"R"),
        QUEEN(9,"Q"),
        KING(10,"K");
        private String pieceName;
        public int pieceValue;
        PieceType(int pieceValue, String pieceName)
        {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
        
        @Override
        public String toString()
        {
            return this.pieceName; 
        }

        public int getPieceValue() {
            return this.pieceValue;
        }
    }
    
    public abstract List<Move> PossibleMove(Board board);

    public abstract Piece pieceMove(Move move);
}
