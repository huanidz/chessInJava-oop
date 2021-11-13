/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;
import chessproject.piece.Piece;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author hoain
 */
public abstract class Spot {
    
    private final int SpotCoordinate; //Tọa độ của ô vuông

    //Tạo 1 map chứa các ô trống, sử dụng Map với key = Integer và Value là EmptySpot
    public static final Map<Integer, EmptySpot> Empty_Spot_Map = CreateAllEmptySpot();
    
    //Hàm tạo toàn bộ các ô trống có thể
    public static Map<Integer, EmptySpot> CreateAllEmptySpot()
    {
        Map<Integer,EmptySpot> EmptySpotMap = new HashMap<>();
        
        for (int i = 0; i < 64; i++) {
            //Put các ô trống với key là i = 1,2,3.... 
            //và value là các EmptySpot được khởi tạo SpotCoordinate là i
            EmptySpotMap.put(i, new EmptySpot(i));      
        }
        return EmptySpotMap; //Trả về Map vừa tạo
    }
    
    public Spot(int SpotCoordinate)
    {
        this.SpotCoordinate = SpotCoordinate;
    }

    public int getSpotCoordinate() {
        return SpotCoordinate;
    }
    
    
    
    //Hàm khởi tạo 1 spot (nếu chứa piece ==> Ô chứa quân (ContainingSpot) , ngược lại: ô trống (EmptySpot))
    public static Spot createSpot(int SpotCoordinate, Piece piece)
    {
        if(piece != null)
            return new ContainingSpot(SpotCoordinate, piece);
        else
            return Empty_Spot_Map.get(SpotCoordinate); //Lấy 1 EmptySpot từ Map chứa các EmptySpot
    }
    
    public abstract boolean isContainAnything();
    
    public abstract Piece getPiece();
    
    //Ô trống
    public static class EmptySpot extends Spot{
        
        public EmptySpot(int SpotCoordinate)
        {
            super(SpotCoordinate);
        }

        @Override
        public String toString()
        {
            return "-";
        }
        
        @Override
        public boolean isContainAnything() {
            return false; //Ô trống -> trả về false (ko contain gì)
        }

        @Override
        public Piece getPiece() {
            return null; //Ko trả về được Piece nào từ Ô trống
        }    
    }
    
    //Ô có chứa quân
    public static class ContainingSpot extends Spot{
        
        private Piece piece;
        
        public ContainingSpot(int SpotCoordinate, Piece piece)
        {
            super(SpotCoordinate);
            this.piece = piece;
        }

        @Override
        public String toString()
        {
            return this.getPiece().getPieceAlliance().isBlack() ? this.getPiece().toString().toLowerCase() 
                    : this.getPiece().toString().toUpperCase();
        }
        
        @Override
        public boolean isContainAnything() {
            return true; //Trả về true do có quân
        }

        @Override
        public Piece getPiece() {
            return this.piece; //Trả về quân đang nằm trong 
        }
    }
    
}
