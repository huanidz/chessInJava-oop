/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.ui;

import chessproject.board.Move;
import chessproject.piece.Piece;
import chessproject.ui.Table.MoveLog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import com.google.common.primitives.Ints;
import java.awt.Image;

/**
 *
 * @author hoain
 */
public class PieceLogPanel extends JPanel {
    
    public JPanel northPanel;
    public JPanel southPanel;
    
    public static Color PANEL_COLOR = new Color(218,247,166);
    
    public static Dimension PieceLogPanelDimension = new Dimension(80,100);
    public static Dimension PieceTaken = new Dimension(80,450);
            
    public static EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public PieceLogPanel()
    {
        super(new BorderLayout());
        setBackground(new Color(218,247,166));
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8,2));
        this.southPanel = new JPanel(new GridLayout(8,2));
        
        this.northPanel.setSize(PieceTaken);
        this.southPanel.setSize(PieceTaken);
        
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        
        this.add(this.northPanel,BorderLayout.NORTH);
        this.add(this.southPanel,BorderLayout.SOUTH);
        
        setPreferredSize(PieceLogPanelDimension);
    }
    
    public void redo(MoveLog movelog)
    {
        this.southPanel.removeAll();
        this.northPanel.removeAll();
        
        
        List<Piece> whiteTakenPieces = new ArrayList<>();
        List<Piece> blackTakenPieces = new ArrayList<>();
        
        for(Move move : movelog.getMoves())
        {
            if(move.isAttack())
            {
                Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite())
                {
                    whiteTakenPieces.add(takenPiece);
                }
                else if (takenPiece.getPieceAlliance().isBlack())
                {
                    blackTakenPieces.add(takenPiece);
                }
            }
        }
        
        
        Collections.sort(whiteTakenPieces, new Comparator<Piece>(){
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        
        Collections.sort(blackTakenPieces, new Comparator<Piece>(){
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        
        for(Piece takenpiece : whiteTakenPieces){
            try {
                BufferedImage image = ImageIO.read(new File("pieceIcon/" + takenpiece.getPieceAlliance().toString().substring(0,1) + "" + takenpiece.toString() + ".png"));
                ImageIcon icon = new ImageIcon(image);
                
                JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-70, icon.getIconWidth()-70, Image.SCALE_DEFAULT)));
                
                this.northPanel.add(imageLabel);
                validate();
                        } catch (IOException ex) {
                Logger.getLogger(PieceLogPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Piece takenpiece : blackTakenPieces){
            try {
                BufferedImage image = ImageIO.read(new File("pieceIcon/" + takenpiece.getPieceAlliance().toString().substring(0,1) + "" + takenpiece.toString() + ".png"));
                ImageIcon icon = new ImageIcon(image);
                
                JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-70, icon.getIconWidth()-70, Image.SCALE_DEFAULT)));
                this.southPanel.add(imageLabel);
                validate();
                        } catch (IOException ex) {
                Logger.getLogger(PieceLogPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
