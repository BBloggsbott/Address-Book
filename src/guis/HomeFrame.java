/*
 * Package for the GUI of the Address Book
 */
package guis;

import dbmanager.DBHandler;
import java.awt.Frame;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
/**
 *This class acts as the Home Window of the AddressBook
 * @author 
 */
public class HomeFrame extends Frame implements ActionListener,WindowListener{
    Button add,delete,searchButton;
    Label title;
    AddRecordFrame addPage;
    DeleteRecordFrame deletePage;
    SearchRecordFrame searchPage;
    Connection conn;
    String tableName;
    DBHandler db;
    BufferedReader br;
    
    /**
     * This is the Constructor which creates the Home Window of the Address book
     */
    public HomeFrame(){
        super("Address Book - Home");
        setLayout(null);
        title = new Label("Address Book");
        add = new Button("Add");
        delete = new Button("Delete");
        searchButton = new Button("Search");
        db = new DBHandler();
        tableName = "contacts";
        conn = db.createConnection("addressBookDB");
        
        
        title.setBounds(100,50,100,20);
        add.setBounds(40,95,40,20);
        delete.setBounds(105,95,70,20);
        searchButton.setBounds(200,95,60,20);
        
        add(title);
        add(add);
        add(delete);
        add(searchButton);
        
        add.addActionListener(this);
        delete.addActionListener(this);
        searchButton.addActionListener(this);
        addWindowListener(this);
        
        setVisible(true);
        setSize(300,200);
    }
    
    /**
     * This function Handles the Button Clicks
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
        if(e.getSource() == add){
            addPage = new AddRecordFrame(conn,tableName);
        }
        else if(e.getSource() == delete){
            deletePage = new DeleteRecordFrame(conn,tableName);
        }
        else if(e.getSource() == searchButton){
            searchPage = new SearchRecordFrame(conn,tableName);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) { }
    
    /**
     * This function HAndles Window Closing Event
     * @param e The Window Event
     */
    @Override
    public void windowClosing(WindowEvent e) { 
        try{
            if(!addPage.isActive())
                addPage = null;
            if(!deletePage.isActive())
                deletePage = null;
            if(!searchPage.isActive())
                searchPage = null;
        }
        catch(Exception ee){
            try{
                if(!deletePage.isActive())
                    addPage = null;
                if(!searchPage.isActive())
                    searchPage = null;
            }
            catch(Exception eee){
                try{
                    if(!searchPage.isActive())
                        searchPage = null;
                }
                catch (Exception we){ }
            }
        }
        if(addPage == null && deletePage == null && searchPage == null)
            dispose();
        
    }

    @Override
    public void windowClosed(WindowEvent e) {  }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
