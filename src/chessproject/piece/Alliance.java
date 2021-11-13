/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.piece;

import chessproject.board.BoardUtils;
import chessproject.player.BlackPlayer;
import chessproject.player.Player;
import chessproject.player.WhitePlayer;

/**
 *
 * @author hoain
 */
public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }

        @Override
        public boolean isPawnPromotionSpot(int position) {
            return BoardUtils.EIGHTH_RANK[position];
        }
    },
    
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }

        @Override
        public boolean isPawnPromotionSpot(int position) {
            return BoardUtils.FIRST_RANK[position];
        }
    };
    
    //Hàm lấy hướng đi, phục vụ cho Pawn do Pawn chỉ đi 1 hướng
    public abstract int getDirection();
    
    public abstract int getOppositeDirection();

    public abstract boolean isBlack();
    
    public abstract boolean isWhite();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
    
    public abstract boolean isPawnPromotionSpot(int position);

}
