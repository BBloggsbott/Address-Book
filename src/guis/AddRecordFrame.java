/*
 * Package for the guis of the Address Book
 */
package guis;

import datamanager.DataHandler;
import dbmanager.DBHandler;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * The is the Class for the Window to add a record to the Database
 * @author 
 */
public class AddRecordFrame extends Frame implements ActionListener,WindowListener{
   Label title,idLabel,nameLabel,addressLabel,dobLabel,phnoLabel,cellnoLabel;
   Button addButton,backButton;
   TextField idText,nameText,dobText,phnoText,cellnoText;
   TextArea addressText;
   DataHandler datahandler;
   DBHandler db;
   String tableName;
   Connection conn;
   BufferedReader br;
   String line;
   
   /**
    * This constructor initializes the elements in the window and makes it visible
    * @param conn The connection to the Database
    * @param tableName The name of the Table to add records
    */
   public AddRecordFrame(Connection conn,String tableName){
       super("Address Book - Add");
       setLayout(null);
       this.conn = conn;
       this.tableName = tableName;
       title = new Label("Address Book - Add");
       idLabel = new Label("ID");
       nameLabel = new Label("Name");
       addressLabel = new Label("Address");
       dobLabel = new Label("DOB");
       phnoLabel = new Label("Phone No.");
       cellnoLabel = new Label("Cell No.");
       line = "";
       try{
        br = new BufferedReader(new FileReader("id.txt"));
        line = br.readLine();
        br.close();
       }
       catch(Exception e){
           e.printStackTrace();
       }
       idText = new TextField(line);
       idText.setEditable(false);
       nameText = new TextField();
       dobText = new TextField();
       phnoText = new TextField();;
       cellnoText = new TextField();
       addressText = new TextArea();
       addButton = new Button("Add");
       backButton = new Button("Back");
       
       title.setBounds(100,50,100,20);
       idLabel.setBounds(50,75,40,20);
       idText.setBounds(130,75,40,20);
       nameLabel.setBounds(50,100,40,20);
       nameText.setBounds(130,100,40,20);
       addressLabel.setBounds(50,125,60,20);
       addressText.setBounds(130,125,150,60);
       dobLabel.setBounds(50,190,40,20);
       dobText.setBounds(130,190,70,20);
       phnoLabel.setBounds(50,215,70,20);
       phnoText.setBounds(130,215,70,20);
       cellnoLabel.setBounds(50,240,60,20);
       cellnoText.setBounds(130,240,70,20);
       addButton.setBounds(50,270,60,20);
       backButton.setBounds(130,270,60,20);
       
       add(title);
       add(idLabel);
       add(idText);
       add(nameLabel);
       add(nameText);
       add(addressLabel);
       add(addressText);
       add(dobLabel);
       add(dobText);
       add(phnoLabel);
       add(phnoText);
       add(cellnoLabel);
       add(cellnoText);
       add(addButton);
       add(backButton);
       
       addButton.addActionListener(this);
       backButton.addActionListener(this);
       addWindowListener(this);
       
       setVisible(true);
       setSize(300,300);
   }
   
    /**
     * This function handles the button clicks in the Window
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            dispose();
        }
        else if(e.getSource() == addButton){
            datahandler = new DataHandler();
            datahandler.newData(Integer.parseInt(idText.getText()),nameText.getText(),addressText.getText(),dobText.getText(),phnoText.getText(),cellnoText.getText());
            db = new DBHandler();
            if(db.insertElement(conn,datahandler.getHashMap(),tableName)){
                datahandler.clearData();
            }
            PrintWriter toFile;
            try{
                toFile = new PrintWriter("id.txt");
                toFile.print((Integer.parseInt(line)+1)+"");
                toFile.close();
            }
            catch(FileNotFoundException fe){
                fe.printStackTrace();
            }
            dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) { }
    
    /**
     * Function to handle the closing of the Window
     * @param e The Window Event
     */
    @Override
    public void windowClosing(WindowEvent e) { dispose();}

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
