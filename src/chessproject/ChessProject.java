/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import chessproject.ui.ChessMenu;

/**
 *
 * @author hoain
 */
public class ChessProject {

    /**
     * @param args
     * @NguyenTrongHuan args the command line arguments
     */
    public static void main(String[] args) {
        //Board mainboard = Board.createNormalBoard();
        //System.out.println(mainboard);
        //Table table = new Table();
        ChessMenu cm = new ChessMenu();
        cm.setVisible(true);
    }
    
}
