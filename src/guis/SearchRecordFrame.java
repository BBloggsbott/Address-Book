/*
 * Package for the GUIs of Address Book
 */
package guis;

import dbmanager.DBHandler;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
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
 * Frame to Search for Records from the Database
 * @author bbloggsbott
 */
public class SearchRecordFrame extends Frame implements ActionListener,TextListener,WindowListener {
    Label title,idLabel,nameLabel,addressLabel,phnoLabel,cellnoLabel;
    TextField idSearch,nameSearch,phnoSearch,cellnoSearch;
    TextArea idResult,nameResult,addressResult,phnoResult,cellnoResult;
    Button backButton;
    Connection conn;    String tableName;
    DBHandler db;   ResultSet rs;
    
    /**
     * This constructor Initializes the elements in the Frame
     * @param conn The connection to the Database
     * @param tableName The name of the table to search from
     */
    public SearchRecordFrame(Connection conn,String tableName){
        super("Address Book - Search");
        setLayout(null);
        this.conn = conn;
        this.tableName = tableName;
        title = new Label("AddressBook - Search");
        idLabel = new Label("ID");
        nameLabel = new Label("Name");
        addressLabel = new Label("Address");
        phnoLabel = new Label("Ph No.");
        cellnoLabel = new Label("CellNo");
        idSearch = new TextField();
        nameSearch = new TextField();
        phnoSearch = new TextField();
        cellnoSearch = new TextField();
        idResult = new TextArea();
        idResult.setEditable(false);
        nameResult = new TextArea();
        nameResult.setEditable(false);
        addressResult = new TextArea();
        addressResult.setEditable(false);
        phnoResult = new TextArea();
        phnoResult.setEditable(false);
        cellnoResult = new TextArea();
        cellnoResult.setEditable(false);
        backButton = new Button("Back");
        db = new DBHandler();
        
        title.setBounds(325,40,200,20);
        idLabel.setBounds(40,70,40,20);
        idSearch.setBounds(40,95,40,20);
        idResult.setBounds(40,120,60,130);
        nameLabel.setBounds(140,70,100,20);
        nameSearch.setBounds(140,95,100,20);
        nameResult.setBounds(140,120,120,130);
        addressLabel.setBounds(300,70,100,20);
        addressResult.setBounds(300,120,120,130);
        phnoLabel.setBounds(460,70,100,20);
        phnoSearch.setBounds(460,95,100,20);
        phnoResult.setBounds(460,120,120,130);
        cellnoLabel.setBounds(620,70,100,20);
        cellnoSearch.setBounds(620,95,100,20);
        cellnoResult.setBounds(620,120,120,130);
        backButton.setBounds(375,275,50,20);
        
        add(title);
        add(idLabel);
        add(idSearch);
        add(idResult);
        add(nameLabel);
        add(nameSearch);
        add(nameResult);
        add(addressLabel);
        add(addressResult);
        add(phnoLabel);
        add(phnoSearch);
        add(phnoResult);
        add(cellnoLabel);
        add(cellnoSearch);
        add(cellnoResult);
        add(backButton);
        
        addWindowListener(this);
        idSearch.addTextListener(this);
        nameSearch.addTextListener(this);
        phnoSearch.addTextListener(this);
        cellnoSearch.addTextListener(this);
        backButton.addActionListener(this);
        
        setVisible(true);
        setSize(800,300);
        
    }
    
    /**
     * This Function Handles the Button Clicks in the Frame 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
        if(e.getSource() == backButton){
            dispose();
        }
    }
    
    /**
     * This function searches for and displays the data when a parameter is entered
     * @param e The Text Value Changed Event
     */
    @Override
    public void textValueChanged(TextEvent e) {
        
        if(e.getSource() == idSearch){
            try{
                rs = db.searchID(conn,Integer.parseInt(idSearch.getText()),tableName);
            }
            catch(NumberFormatException ne){
                //Nothing to do
            }
            try{
                if(rs.next()){
                    do{
                        idResult.setText(rs.getInt("id")+"\n");
                        nameResult.setText(rs.getString("name")+"\n");
                        addressResult.setText(rs.getString("address")+"\n");
                        phnoResult.setText(rs.getString("phno")+"\n");
                        cellnoResult.setText(rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(!nameSearch.getText().isEmpty() && !phnoSearch.getText().isEmpty() && !cellnoSearch.getText().isEmpty()){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchNamePhnoCellno(conn,nameSearch.getText(),phnoSearch.getText(),cellnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(!nameSearch.getText().isEmpty() && !phnoSearch.getText().isEmpty()){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchNamePhno(conn,nameSearch.getText(),phnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(!nameSearch.getText().isEmpty() && !cellnoSearch.getText().isEmpty()){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchNameCellno(conn,nameSearch.getText(),cellnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(!phnoSearch.getText().isEmpty() && !cellnoSearch.getText().isEmpty()){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchPhnoCellno(conn,phnoSearch.getText(),cellnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(e.getSource() == nameSearch){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchName(conn,nameSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(e.getSource() == phnoSearch){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchByPhno(conn,phnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
        else if(e.getSource() == cellnoSearch){
            idResult.setText("");
            nameResult.setText("");
            addressResult.setText("");
            phnoResult.setText("");
            cellnoResult.setText("");
            rs = db.searchByCellno(conn,cellnoSearch.getText(),tableName);
            try{
                if(rs.next()){
                    do{
                        idResult.append(rs.getInt("id")+"\n");
                        nameResult.append(rs.getInt("id")+" - "+rs.getString("name")+"\n");
                        addressResult.append(rs.getInt("id")+" - "+rs.getString("address")+"\n");
                        phnoResult.append(rs.getInt("id")+" - "+rs.getString("phno")+"\n");
                        cellnoResult.append(rs.getInt("id")+" - "+rs.getString("cellno")+"\n");
                    }while(rs.next());
                }
                else{
                    idResult.setText("Not\nFound");
                    nameResult.setText("Not Found");
                    addressResult.setText("Not Found");
                    phnoResult.setText("Not Found");
                    cellnoResult.setText("Not Found");
                }
            }
            catch(Exception se){
                idResult.setText("Error - Not Found");
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) { }
    
    /**
     * This function Handles the closing of the Frame
     * @param e The Window Event
     */
    @Override
    public void windowClosing(WindowEvent e) { dispose(); }

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
