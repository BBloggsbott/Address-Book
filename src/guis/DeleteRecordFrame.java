/*
 * Package for the guis of the Address Book
 */
package guis;

import dbmanager.DBHandler;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *This is the Frame to delete a record from the database
 * @author 
 */
public class DeleteRecordFrame extends Frame implements ActionListener,WindowListener,TextListener {
    Label title, idLabel,nameLabel;
    TextField idText,nameText;
    Button deleteButton,backButton;
    Connection conn;    String tableName;
    DBHandler db;
    ResultSet rs;
    
    /**
     * This constructor creates and initializes the elements of the Frame.
     * @param conn The connection to the Database
     * @param tableName The name of the table to Delete Records
     */
    public DeleteRecordFrame(Connection conn,String tableName){
        super("Address Book - Delete");
        setLayout(null);
        title = new Label("Address Book-Delete");
        idLabel = new Label("ID");
        idText = new TextField();
        nameLabel = new Label("Name");
        nameText = new TextField();
        nameText.setEditable(false);
        deleteButton = new Button("Delete");
        backButton = new Button("Back");
        db = new DBHandler();
        this.conn = conn;
        this.tableName = tableName;
        
        title.setBounds(100,50,100,20);
        idLabel.setBounds(50,75,40,20);
        idText.setBounds(130,75,40,20);
        nameLabel.setBounds(50,100,40,20);
        nameText.setBounds(130,100,150,20);
        deleteButton.setBounds(50,125,70,20);
        backButton.setBounds(130,125,70,20);
        
        add(title);
        add(idLabel);
        add(idText);
        add(nameLabel);
        add(nameText);
        add(deleteButton);
        add(backButton);
        
        idText.addTextListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this);
        addWindowListener(this);
        
        setVisible(true);
        setSize(300,300);
    }
    
    /**
     * The function to handle button clicks in the frame
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            dispose();
        }
        else if(e.getSource() == deleteButton){
            db.deleteElement(conn,tableName,Integer.parseInt(idText.getText()));
            dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
    }

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
    
    /**
     * This function gets the "name" parameter of the record when the ID is entered
     * @param e The Text Value Changed event
     */
    @Override
    public void textValueChanged(TextEvent e) { 
        if(e.getSource() == idText){
            try{
                rs = db.searchElements(conn,Integer.parseInt(idText.getText()),tableName);
                if(rs.next()){
                    do{
                        nameText.setText(rs.getString("name"));
                    }while(rs.next());
                }
                else{
                    nameText.setText("No Record Found");
                }
            }
            catch(Exception ee){
                //Add Logs
                nameText.setText("Error No Record Found");
            }
        }
    }

    
    
}
