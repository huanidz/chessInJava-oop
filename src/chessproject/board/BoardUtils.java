/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 *
 * @author hoain
 */
public class BoardUtils {

    public static boolean[] FIRST_COLUMN = initColumn(0);
    public static boolean[] SECOND_COLUMN = initColumn(1);
    
    public static boolean[] SEVENTH_COLUMN = initColumn(6);
    public static boolean[] EIGHTH_COLUMN = initColumn(7);
    
    public static boolean[] EIGHTH_RANK = initRow(0);
    public static boolean[] SEVENTH_RANK = initRow(8);
    public static boolean[] SIXTH_RANK = initRow(16);
    public static boolean[] FIFTH_RANK = initRow(24);
    public static boolean[] FOURTH_RANK = initRow(32);
    public static boolean[] THIRD_RANK = initRow(40);
    public static boolean[] SECOND_RANK = initRow(48);
    public static boolean[] FIRST_RANK = initRow(56);
    
    
    public static List<String> COORDINATE_TO_POS = initCoorToPos();

    /**
     *
     */
    public static Map<String,Integer> POS_TO_COORDINATE = initPosToCoor();
    
    //Hàm kiểm tra xem ô đó có hợp lệ không (nằm trong 0->63)
    public static boolean isValidSpot(int RealCoordinateMove) {
        return (RealCoordinateMove >= 0 && RealCoordinateMove < 64);
    }

    private static boolean[] initColumn(int ColumnNumber) {
        final boolean[] column = new boolean[64];
        
        do
        {
            column[ColumnNumber] = true;
            ColumnNumber += 8;
        }while(ColumnNumber < 64);
        
        return column;          
    }
    
    private static boolean[] initRow(int RowStart)
    {
        final boolean[] row = new boolean[64];
        do{
            row[RowStart] = true;
            RowStart++;
        }while(RowStart % 8 != 0);
        
        return row;
    }

    private static Map<String, Integer> initPosToCoor() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            positionToCoordinate.put(COORDINATE_TO_POS.get(i), i);
        }
        return positionToCoordinate;
    }
    
    private static List<String> initCoorToPos() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    public static String getPosAtCoordinate(int Coordinate) {
        return COORDINATE_TO_POS.get(Coordinate);
    }
    
    public static int getCoordinateAtPos(String position){
        return POS_TO_COORDINATE.get(position);
    }
}
