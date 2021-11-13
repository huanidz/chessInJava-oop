/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject.ui;

import chessproject.board.*;
import static chessproject.board.Board.createNormalBoard;
import chessproject.piece.*;
import chessproject.player.MoveChecker;
import chessproject.player.MoveTransition;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author hoain
 */
public class Table {

    JFrame gameFrame;
    BoardPanel boardpanel;
    Board chessBoard;
    
    MoveLog movelog;
    
    GameLogPanel gamelogpanel;
    PieceLogPanel piecelogpanel;
    ActionPanel actionPanel;
    
    public Dimension ChessFrameSize = new Dimension(950, 900);
    public Dimension BoardSize = new Dimension(900,900);
    public Dimension SpotSize = new Dimension(10,10);

    Spot StartSpot;
    Spot EndSpot;
    Piece movePiece;
    int LastEndSpotID;
    int LastClickedSpotID;
    
    String PieceIconPath = "pieceIcon/";
    
    String Wname,Bname;
    
    public Table(String player1Name, String player2Name)
    {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setSize(ChessFrameSize);  
        
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = createNormalBoard();
        this.chessBoard.getWhitePlayer().setPlayerName(player1Name);
        this.chessBoard.getBlackPlayer().setPlayerName(player2Name);
        
        this.boardpanel = new BoardPanel();
        this.movelog = new MoveLog();
        this.gamelogpanel = new GameLogPanel();
        this.piecelogpanel = new PieceLogPanel();
        this.actionPanel = new ActionPanel();
        this.actionPanel.add(new JLabel("White Player: "+chessBoard.getWhitePlayer().getPlayerName()+"        vs.     ", SwingConstants.LEFT));
        this.actionPanel.add(new JLabel("Black Player: "+chessBoard.getBlackPlayer().getPlayerName(), SwingConstants.RIGHT));
        
        Wname = player1Name;
        Bname = player2Name;

        this.gameFrame.add(this.piecelogpanel,BorderLayout.WEST);
        this.gameFrame.add(this.boardpanel, BorderLayout.CENTER); 
        this.gameFrame.add(this.gamelogpanel,BorderLayout.EAST);
        this.gameFrame.add(this.actionPanel,BorderLayout.SOUTH);
        
        
        this.actionPanel.ResignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //All moves to one String   
                String tempStringToAddSQL = "";
                for(Move move : movelog.moves)
                {          
                    tempStringToAddSQL += move.toString();
                    tempStringToAddSQL += " ";
                }

                //SQL Server Connection 
                String dbURL = "jdbc:sqlserver://localhost;databaseName=ChessDatabase;user=sa;password=123456";
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(dbURL);
                    Statement statement = conn.createStatement();
                    statement.executeUpdate("INSERT INTO ChessGameData " + "VALUES('" + Wname + "','" + Bname + "','" + tempStringToAddSQL + "')");
                } catch (SQLException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (conn != null) {
                 System.out.println("Connected to SQLServer");
                }
                //================================
                
                JOptionPane.showMessageDialog(null, 
                chessBoard.getCurrentPlayer().getAlliance().toString() + " Đầu hàng, " + chessBoard.getCurrentPlayer().getEnemy().getAlliance().toString() +  " thắng", "Ván cờ kết thúc", 
                JOptionPane.INFORMATION_MESSAGE);
                chessBoard.getCurrentPlayer().setLegalMoves(Collections.emptyList());
                int i = 0;
                File myObj = new File("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                while(myObj.exists() && !myObj.isDirectory())
                {
                    i++;
                    myObj = new File("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                }
                try {
                FileWriter myWriter = new FileWriter("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                myWriter.write((int) System.currentTimeMillis() + "\n");
                
                myWriter.write("White Player: "+Wname + "\n");
                myWriter.write("Black Player: "+Bname + "\n");
                
                myWriter.write(chessBoard.getCurrentPlayer().getAlliance().toString() + " RESIGN" + "\n");
                myWriter.write("---------------------------------" + "\n");
                int j = 0;
                for(Move move : movelog.moves)
                {          
                    myWriter.write(move.toString() + " ");
                    j++;
                    if(j == 6)
                    {
                        myWriter.write("\n");
                        j = 0;
                    }
                }

                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                } 
                catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
                }
            }
        });
        
        this.actionPanel.NewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    gameFrame.dispose();
                    new Table(Wname,Bname);
            }
        });

        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public class BoardPanel extends JPanel{
        List<SpotPanel> BoardSpots;
        
        public BoardPanel()
        {
            super(new GridLayout(8, 8));
            this.BoardSpots = new ArrayList<>();
            
            for (int i = 0; i < 64; i++) {
                SpotPanel spotPanel = new SpotPanel(this, i);
                this.BoardSpots.add(spotPanel);
                add(spotPanel);
            }
            
            setPreferredSize(BoardSize);
            validate();    
        }
 
        public void drawBoard(Board chessBoard) {
            removeAll();
            for (SpotPanel spotpanel : BoardSpots) {
                spotpanel.drawSpot(chessBoard);
                add(spotpanel);
            }
            validate();
            repaint();
        }
    }
    
    public static class MoveLog{
        public List<Move> moves;
        
        public MoveLog()
        {   
            this.moves = new ArrayList<>();
        }
        
        public List<Move> getMoves()
        {
            return this.moves;
        }
        
        public void addMove(Move move)
        {
            this.moves.add(move);
        }
        
        public int size()
        {
            return this.moves.size();
        }
        
        public void clear()
        {
            this.moves.clear();
        }
        
        public Move removeMove(int i)
        {
            return this.moves.remove(i);
        }
        
        public boolean removeMove(Move move)
        {
            return this.moves.remove(move);
        }
    }
    
    public class SpotPanel extends JPanel{

        int SpotID;
        Color SPOT_WHITE_COLOR = new Color(255,245,245);
        Color SPOT_BLACK_COLOR = new Color(180,222,162);
        Color SPOT_YELLOW_COLOR = new Color(231,243,37);
        
        boolean isStartSpot;
        boolean isEndSpot;

        BufferedImage imagePieceIcon;
        JLabel pieceIconLabel;
        
        @Override
        public void setLayout(LayoutManager mgr) {
            super.setLayout(mgr); //To change body of generated methods, choose Tools | Templates.
        }
        
        public SpotPanel(BoardPanel boardpanel, int spotid)
        {
            super(new GridBagLayout());
            this.SpotID = spotid;
            this.isStartSpot = false;
            this.isEndSpot = false;
            setPreferredSize(SpotSize);
            //this.setLayout(null);
            initSpotColor();
            setPieceIcon(chessBoard);
             
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    
                    //Nhan chuot phai, clear mọi thứ đã chọn trước đó
                    if(SwingUtilities.isRightMouseButton(e))
                    {
                        StartSpot = null;
                        EndSpot = null;
                        movePiece = null; 
                        isStartSpot = false;
                    }
                    //Nhan chuot trai 
                    else if(SwingUtilities.isLeftMouseButton(e))
                    {
                        if(StartSpot == null)
                        {
                            
                             StartSpot = chessBoard.getSpot(SpotID);
                             LastClickedSpotID = StartSpot.getSpotCoordinate();
                             //setSpotColor(StartSpot);
                             movePiece = StartSpot.getPiece();
                             
                             if(movePiece == null)
                             {
                                 StartSpot = null;
                                 isStartSpot = false;
                                 initSpotColor();
                             }
                              //Đổi màu StartSpot
                              if(movePiece != null)
                              {
                                  isStartSpot = true;
                              }  
                        }
                        else
                             {
                                 //setSpotColor(StartSpot);
                                 EndSpot = chessBoard.getSpot(SpotID);
                                 if(EndSpot.getSpotCoordinate() != LastClickedSpotID)
                                    {
                                    //LastEndSpotID = chessBoard.getSpot(SpotID).getSpotCoordinate();
                                    isEndSpot = true;
                                    //isStartSpot = false;
                                    //tạo nước đi từ hàm createMove
                                    Move move = Move.MoveCreator.createMove(chessBoard, StartSpot.getSpotCoordinate(), EndSpot.getSpotCoordinate());

                                    //Triển khai nước đi (trên một board mới)
                                    MoveTransition movetrans = chessBoard.getCurrentPlayer().makeMove(move);

                                    //Sau khi nước đi được hoàn thành, gán board mới cho board cũ
                                    if(movetrans.getMoveStatus().isDone())
                                    {
                                        //chessBoard = movetrans.getTheBoardAfter();
                                        chessBoard = movetrans.getToBoard();
                                        movelog.addMove(move);
                                    }
                                    //Clear moi thu sau khi thuc hien 1 nuoc di
                                    StartSpot = null;
                                    EndSpot = null;
                                    movePiece = null;
                                    isStartSpot = false;
                                    }
                                 else
                                    {
                                        isStartSpot = true;
                                        Move move = Move.MoveCreator.createMove(chessBoard, StartSpot.getSpotCoordinate(), EndSpot.getSpotCoordinate());

                                    //Triển khai nước đi (trên một board mới)
                                    MoveTransition movetrans = chessBoard.getCurrentPlayer().makeMove(move);

                                    //Sau khi nước đi được hoàn thành, gán board mới cho board cũ
                                    if(movetrans.getMoveStatus() == MoveChecker.DONE)
                                    {
                                        chessBoard = movetrans.getTheBoardAfter();
                                        movelog.addMove(move);
                                    }
                                    //Clear moi thu sau khi thuc hien 1 nuoc di
                                    StartSpot = null;
                                    EndSpot = null;
                                    movePiece = null;
                                    isStartSpot = false;
                                    }
                             }
                             
                             SwingUtilities.invokeLater(new Runnable() {
                                 @Override
                                 public void run() {
                                    
                                    gamelogpanel.redo(chessBoard, movelog);
                                    piecelogpanel.redo(movelog);
                                    boardpanel.drawBoard(chessBoard);
                                    if(chessBoard.getCurrentPlayer().isInCheckmate())
                                    {
                                        //All moves to one String   
                                        String tempStringToAddSQL = "";
                                        for(Move move : movelog.moves)
                                        {          
                                            tempStringToAddSQL += move.toString();
                                            tempStringToAddSQL += " ";
                                        }
                                        
                                        //SQL Server Connection 
                                        String dbURL = "jdbc:sqlserver://localhost;databaseName=ChessDatabase;user=sa;password=123456";
                                        Connection conn = null;
                                        try {
                                            conn = DriverManager.getConnection(dbURL);
                                            Statement statement = conn.createStatement();
                                            statement.executeUpdate("INSERT INTO ChessGameData " + "VALUES('" + Wname + "','" + Bname + "','" + tempStringToAddSQL + "')");
                                        } catch (SQLException ex) {
                                            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (conn != null) {
                                         System.out.println("Connected to SQLServer");
                                        }
                                        //================================
  
                                        int i = 0;
                                        File myObj = new File("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                                        while(myObj.exists() && !myObj.isDirectory())
                                        {
                                            i++;
                                            myObj = new File("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                                        }
                                        JOptionPane.showMessageDialog(null, 
                                        "Chiếu hết, " + chessBoard.getCurrentPlayer().getEnemy().getAlliance().toString() +  " thắng", "Ván cờ kết thúc", 
                                        JOptionPane.INFORMATION_MESSAGE); 
                                        try {
                                        FileWriter myWriter = new FileWriter("C:\\Users\\hoain\\OneDrive\\Documents\\NetBeansProjects\\ChessProject\\gamesplayed\\"+"game" + i + ".txt");
                                        myWriter.write((int) System.currentTimeMillis() + "\n");
                                        
                                        myWriter.write("White Player: "+Wname + "\n");
                                        myWriter.write("Black Player: "+Bname + "\n");
                                        
                                        myWriter.write(chessBoard.getCurrentPlayer().getEnemy().getAlliance().toString() +" WIN BY CHECKMATE" + "\n");
                                        
                                        myWriter.write("---------------------------------" + "\n");
                                        int j = 0;
                                        for(Move move : movelog.moves)
                                        {          
                                            myWriter.write(move.toString() + " ");
                                            j++;
                                            if(j == 6)
                                            {
                                                myWriter.write("\n");
                                                j = 0;
                                            }
                                        }
                                        
                                        myWriter.close();
                                        System.out.println("Successfully wrote to the file.");
                                        } 
                                        catch (IOException e) {
                                        System.out.println("An error occurred.");
                                        e.printStackTrace();
                                        }
                                    }
                                 }
                             });
                    }
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
            validate();
        }

        public void drawSpot(Board chessBoard) {
            initSpotColor();
            setPieceIcon(chessBoard);
            highlightMove(chessBoard);
            validate();
            repaint();
        }
        
        public void setPieceIcon(Board board)
        {
            this.removeAll();
            if(board.getSpot(this.SpotID).isContainAnything())
            { 
                try {
                    imagePieceIcon = ImageIO.read(new File(PieceIconPath +
                            board.getSpot(this.SpotID).getPiece().getPieceAlliance().toString().substring(0, 1) + 
                            board.getSpot(this.SpotID).getPiece().toString() + ".png"));
                    
                    pieceIconLabel = new JLabel(new ImageIcon(imagePieceIcon));
                    //add(new JLabel(new ImageIcon(imagePieceIcon)));
                    add(pieceIconLabel);
                    } 
                catch (IOException ex) 
                {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        public void highlightMove(Board board)
        {
            for(Move move: pieceLegalMoves(board))
            {
                if(move.getDestinationCoordinate() == this.SpotID)
                {
                    //Hiển thị legal move bằng blackdot, nếu đường đi có quân, ko thêm blackdot vào đó
                    if(board.getSpot(move.getDestinationCoordinate()).getPiece() == null)
                        try {
                            BufferedImage imageDot = ImageIO.read(new File(PieceIconPath+"blackdot.png"));
                            add(new JLabel(new ImageIcon(imageDot)));
                           // add(new JLabel(new ImageIcon(ImageIO.read(new File("pieceIcon/blackdot.png")))));
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                    else if(board.getSpot(move.getDestinationCoordinate()).getPiece().getPieceAlliance() != move.getPieceMove().getPieceAlliance())
                        try {
                            BufferedImage imageCircle = ImageIO.read(new File(PieceIconPath+"graycircle96.png"));
                            //add(new JLabel(new ImageIcon(imageCircle)));
                            BufferedImage combinedImage = new BufferedImage( 
                            imagePieceIcon.getWidth(), 
                            imagePieceIcon.getHeight(), 
                            BufferedImage.TYPE_INT_ARGB );
                            
                            Graphics2D g = combinedImage.createGraphics();
                            g.drawImage(imagePieceIcon,0,0,null);
                            g.drawImage(imageCircle,0,0,null);
                            g.dispose();
                            
                            //add(new JLabel(new ImageIcon(imagePieceIcon)));
                            //add(new JLabel(new ImageIcon(imageCircle)));
                            remove(pieceIconLabel);
                            add(new JLabel(new ImageIcon(combinedImage)));
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                }
            }
            
        }
        
        public List<Move> pieceLegalMoves(Board board)
        {
            if(movePiece != null && movePiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance())
            {
                if(movePiece.getPieceType() == Piece.PieceType.KING)
                {
                    List<Move> piecelegalMoves = movePiece.PossibleMove(board);
                    piecelegalMoves.addAll(board.getCurrentPlayer().calculateKingCastle(board.getCurrentPlayer().getLegalMoves(), board.getCurrentPlayer().getEnemy().getLegalMoves()));
                    return piecelegalMoves;
                }
                return movePiece.PossibleMove(board);
                //return board.getCurrentPlayer().getLegalMoves();
            }
            return Collections.emptyList();
        }
        
        private void initSpotColor() {
            boolean isWhite;
            isWhite = (this.SpotID + this.SpotID/8) % 2 == 0;
            
            setBackground(isWhite ? SPOT_WHITE_COLOR : SPOT_BLACK_COLOR);
            if(isStartSpot || isEndSpot)
            {
                setBackground(SPOT_YELLOW_COLOR);
            }
            isStartSpot = false;
            if(EndSpot == null ||  EndSpot.getSpotCoordinate() != LastEndSpotID)
            {
                isEndSpot = false;
            }
            
        } 
        
        public void setSpotColor(Spot spot)
        {
            boolean isStartSpot;
            boolean isWhite;
            isWhite = (spot.getSpotCoordinate() + spot.getSpotCoordinate()/8) % 2 == 0;
            if(this.SpotID == StartSpot.getSpotCoordinate())
            {
                isStartSpot = true;
            }
            else
            {
                isStartSpot = false;
            }
            setBackground(SPOT_YELLOW_COLOR);
        }
    }

}
