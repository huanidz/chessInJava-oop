    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;
import chessproject.piece.Alliance;
import chessproject.piece.*;
import chessproject.player.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hoain
 */
public class Board {
    
    List<Spot> gameBoard;
    List<Piece> whitePieces;
    List<Piece> blackPieces;
    
    WhitePlayer whitePlayer;
    BlackPlayer blackPlayer;

    Player currentPlayer;
    
    Pawn enPassantPawn;
    
    public static List<Piece> CalculateActivePieces(List<Spot> gameBoard, Alliance alliance)
    {
        List<Piece> tempActivePiece = new ArrayList<>();
        for (Spot i: gameBoard) {
            if(i.isContainAnything())
            {
                Piece tempPiece = i.getPiece();
                if(tempPiece.getPieceAlliance() == alliance)
                {
                    tempActivePiece.add(tempPiece);
                }
            }
        }
        return tempActivePiece;
    }

    
    public List<Move> CalculateLegalMoves(List<Piece> pieces) 
    {
        List<Move> legalMoves = new ArrayList<>();
        
        for(Piece i : pieces)
        {
            legalMoves.addAll(i.PossibleMove(this));
        }
        
       return legalMoves;
    }
    
        
    public Board(BoardBuilder builder) {
        this.gameBoard = CreateBoard(builder);
        this.whitePieces = CalculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = CalculateActivePieces(this.gameBoard, Alliance.BLACK);
        
        this.enPassantPawn = builder.enPassantPawn;
     
        List<Move> whiteLegalMoves = CalculateLegalMoves(this.whitePieces);
        List<Move> blackLegalMoves = CalculateLegalMoves(this.blackPieces);
        
        this.whitePlayer = new WhitePlayer(this,whiteLegalMoves,blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this,whiteLegalMoves,blackLegalMoves);
        
        this.currentPlayer = builder.MoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);
    }
    
    
    @Override
    public String toString(){
        StringBuilder stringbuilder = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            String SpotToText = this.gameBoard.get(i).toString();
            stringbuilder.append(SpotToText);
            
            if((i+1) % 8 == 0)
            {
                stringbuilder.append("\n");
            }
        }  
        return stringbuilder.toString();
    }
    
    
    public Spot getSpot(int Coordinate) {
        return this.gameBoard.get(Coordinate);
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }
    
    public static List<Spot> CreateBoard(BoardBuilder builder)
    {
        List<Spot> gameBoard = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            gameBoard.add(Spot.createSpot(i, builder.BoardBuild.get(i)));
        }
        return gameBoard;
    }
    
    
    public static Board createNormalBoard()
    {
        BoardBuilder builder = new BoardBuilder();
        
        //BLACK
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        for (int i = 0; i < 8; i++) {
            builder.setPiece(new Pawn(8+i,Alliance.BLACK));   
        }
              
        //WHITE
        for (int i = 0; i < 8; i++) {
            builder.setPiece(new Pawn(48+i,Alliance.WHITE));   
        }
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));
        
        //Setup cho bên trắng đi trước
        builder.setMoveTurn(Alliance.WHITE);

        return builder.build();
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }
    
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public List<Move> getAllLegalMoves() {
         List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        
        return allLegalMoves;
    }
    
    public static class BoardBuilder{
        Map<Integer,Piece> BoardBuild;
        Alliance MoveMaker;
        Pawn enPassantPawn;
        public BoardBuilder()
        {
            this.BoardBuild = new HashMap<>();
        }
        
        public BoardBuilder setPiece(Piece piece)
        {
            //Thêm Piece vào Map BoardBuilder
            this.BoardBuild.put(piece.getPiecePos(),piece);
            return this;
        }
        
        public BoardBuilder setMoveTurn(Alliance nextMoveMaker)
        {
            //Set lượt đi kế tiếp cho người chơi sau
            this.MoveMaker = nextMoveMaker;
            return this;
        }
        
        public Board build(){
            return new Board(this);
        }

        void setEnpassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
