    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.ui;

import chessproject.board.Board;
import chessproject.board.Move;
import chessproject.ui.Table.MoveLog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoain
 */
public class GameLogPanel extends JPanel {
    
    public DataModel model;
    private final JScrollPane scrollPane;
    public Dimension GameLogDimension = new Dimension(100,400);
    
    public GameLogPanel(){
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());

        scrollPane.setPreferredSize(GameLogDimension);
        this.add(scrollPane,BorderLayout.CENTER);
        this.setVisible(true); 
    }
    
    void redo(Board board, MoveLog movelog)
    {
        int currentRow = 0;
        this.model.clear();
        for(Move move : movelog.getMoves())
        {
            String moveText = move.toString();
            if(move.getPieceMove().getPieceAlliance().isWhite())
            {
                this.model.setValueAt(moveText,currentRow,0);
            }
            else if(move.getPieceMove().getPieceAlliance().isBlack())
            {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        
        if(movelog.getMoves().size() > 0){
            Move lastMove = movelog.getMoves().get(movelog.size()-1);
            String moveText = lastMove.toString();
            
            if(lastMove.getPieceMove().getPieceAlliance().isWhite())
            {
                this.model.setValueAt(moveText+ calculateCheckAndCheckmate(board), currentRow, 0);
            }
            else if(lastMove.getPieceMove().getPieceAlliance().isBlack())
            {
                this.model.setValueAt(moveText+ calculateCheckAndCheckmate(board), currentRow-1, 1);
            }
            
        }
        
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calculateCheckAndCheckmate(Board board) {
        if(board.getCurrentPlayer().isInCheckmate())
        {
            return "#";
        }
        else if(board.getCurrentPlayer().isInCheck())
        {
            return "+";
        }
        else
            return "";
    }
    
    public static class DataModel extends DefaultTableModel
    {
        public List<Row> values;
        public static String[] NAMES = {"White","Black"};
        
        public DataModel()
        {
            this.values = new ArrayList<>();
        }
        
        public void clear()
        {
            this.values.clear();
            setRowCount(0);
        }
        
        @Override
        public int getRowCount()
        {
            if(this.values == null){
                return 0;
            }
            return this.values.size();
        }
        
        @Override
        public int getColumnCount()
        {
            return NAMES.length;
        }
        
        @Override
        public Object getValueAt(int row, int column)
        {
            Row currentRow = this.values.get(row);
            if(column == 0)
            {
                return currentRow.getWhiteMove();
            }
            else if(column == 1)
            {
                return currentRow.getBlackMove();
            }
            return null;
        }
        
        @Override
        public void setValueAt(Object aValue, int row, int column)
        {
            Row currentRow;
            if(this.values.size() <= row)
            {
                currentRow = new Row();
                this.values.add(currentRow);
            }
            else
            {
                currentRow = this.values.get(row);
            }
            if(column == 0)
            {
                currentRow.setWhiteMove((String)aValue);
                fireTableRowsInserted(row, row);
            }
            else if(column == 1)
            {
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, column);
            }
        }
        
        @Override
        public Class<?> getColumnClass(int column)
        {
            return Move.class;
        }
        
        @Override
        public String getColumnName(int column)
        {
            return NAMES[column];
        }
        
        
    }
    
    public static class Row
    {
        public String whiteMove;
        public String blackMove;

        public String getWhiteMove() {
            return whiteMove;
        }

        public String getBlackMove() {
            return blackMove;
        }
        
        public void setWhiteMove(String move)
        {
            this.whiteMove = move;
        }
        
        public void setBlackMove(String move)
        {
            this.blackMove = move;
        }
    }
}
