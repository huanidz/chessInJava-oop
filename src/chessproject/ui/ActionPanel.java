/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author hoain
 */
public class ActionPanel extends JPanel{
    
    JButton ResignButton = new JButton("Resign");
    JButton NewGameButton = new JButton("New Game");

        public ActionPanel()
        {
            super();
            super.setSize(950,400);
            this.ResignButton = new JButton("Resign");
            this.NewGameButton = new JButton("New Game");
            
            this.add(NewGameButton);
            this.add(ResignButton);

        }
}
