/**
 * dbmanager is a package that has a collection of classes than handle databases
 */
package dbmanager;

//Importing required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
* DBHandler is used to create connection to databases 
* and perform other operations on the database. 
* @author bbloggsbott
 */
public class DBHandler {
    
    /**
     * This function creates a connection to the database
     * and returns the connection object
     * @param dbName The name of the Database
     * @return Connection This returns the connection object to the database
     */
    public Connection createConnection(String dbName){
        
        
        Connection conn = null;
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/"+dbName;
            conn = DriverManager.getConnection(url);
        }
        catch(Exception se){
            se.printStackTrace();
        }
        return conn;
    }
    
    /**
     * This function creates the table to store data for the address book
     * @param conn The connection variable for the database of address book
     * @param tableName the table name of the address book
     * @return boolean Return true for successful creation and false for failure
     */
    public boolean createTable(Connection conn,String tableName){
        Statement s;
        try{
            s = conn.createStatement();
            s.executeUpdate("create table "+tableName+"(ID integer primary key,Name varcha(20),Address Varchar(100),DOB varchar(10),Phno integer,CellNo integer)");
            return true;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }   
    
    /**
     * This function inserts data into the table in the address book database;
     * @param conn The connection variable for the database of address book
     * @param data The HashMap containing the data
     * @param tableName The String containing the name of the table for data insertion
     * @return boolean Return true for successful insertion and false for failure
     */
    public boolean insertElement(Connection conn,HashMap data,String tableName){
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("insert into "+tableName+" values(?,?,?,?,?,?)");
            ps.setInt(1,(int)data.get("id"));
            ps.setString(2,(String)data.get("name"));
            ps.setString(3,(String)data.get("address"));
            ps.setString(4,(String)data.get("dob"));
            ps.setString(5,(String)data.get("phno"));
            ps.setString(6,(String)data.get("cellno"));
            ps.executeUpdate();
            return true;
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }
    
    /**
     * This function deletes the record based on the ID
     * @param conn The connection variable for the database of address book
     * @param tableName The name of the table with records of the address book
     * @param id The ID of the record to b deleted
     * @return boolean Returns true for successful deletion and false for failure
     */
    public boolean deleteElement(Connection conn,String tableName,int id){
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("delete from "+tableName+" where ID=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }
    
    /**
     * A function to search for elements in the database based on ID and return them
     * @param conn The connection to the database
     * @param ID The ID of the record to be Fetched
     * @param tableName The table name from which data is to be fetched
     * @return ResultSet The result set of the select query
     */
    public ResultSet searchID(Connection conn,int ID,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where ID=?");
            ps.setInt(1,ID);
            rs = ps.executeQuery();
        }
        catch(SQLException se){

            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     * A function to search for elements in the database based on name and return them
     * @param conn The Connection to the Database
     * @param name The Name to be searched
     * @param tableName The Table to be searched in
     * @return ResultSet The Search Results
     */
    public ResultSet searchName(Connection conn,String name,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where name like ?");
            ps.setString(1,"%"+name+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){

            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     * A function to search for elements in the database based on PhNo and return them
     * @param conn The connection to the Database
     * @param phno The phno to be searched for
     * @param tableName The table to be searched in
     * @return ResultSet The search Results
     */
    public ResultSet searchByPhno(Connection conn,String phno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where phno like ?");
            ps.setString(1,"%"+phno+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     * A function to search for elements in the database based on ID and return them
     * @param conn The connection to the Database
     * @param cellno The cellno to be searched for
     * @param tableName The Table to be searched in
     * @return ResultSet The Result of the Search
     */
    public ResultSet searchByCellno(Connection conn,String cellno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where cellno like ?");
            ps.setString(1,"%"+cellno+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     *  A function to search for elements based on their name and phno
     * @param conn The connection to the Database
     * @param name The name to be searched for
     * @param phno The phno to be searched for
     * @param tableName The Table to be searched in
     * @return ResultSet The Result of the Search
     */
    public ResultSet searchNamePhno(Connection conn,String name,String phno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where phno like ? and name like ?");
            ps.setString(1,"%"+phno+"%");
            ps.setString(2,"%"+name+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     *  A function to search for elements based on their name and cellno
     * @param conn The connection to the Database
     * @param name The name to be searched for
     * @param cellno The cellno to be searched for
     * @param tableName The Table to be searched in
     * @return ResultSet The Result of the Search
     */
    public ResultSet searchNameCellno(Connection conn,String name,String cellno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where cellno like ? and name like ?");
            ps.setString(1,"%"+cellno+"%");
            ps.setString(2,"%"+name+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     *  A function to search for elements based on their phno and cell no
     * @param conn The connection to the Database
     * @param phno The phno to be searched for
     * @param cellno The cellno to be searched for
     * @param tableName The Table to be searched in
     * @return ResultSet The Result of the Search
     */
    public ResultSet searchPhnoCellno(Connection conn,String phno,String cellno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where phno like ? and cellno like ?");
            ps.setString(1,"%"+phno+"%");
            ps.setString(2,"%"+cellno+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
    
    /**
     *  A function to search for elements based on their name, phno and cell no
     * @param conn The connection to the Database
     * @param name The name to be searched for
     * @param phno The phno to be searched for
     * @param cellno The cellno to be searched for
     * @param tableName The Table to be searched in
     * @return ResultSet The Result of the Search
     */
    public ResultSet searchNamePhnoCellno(Connection conn,String name,String phno,String cellno,String tableName){
        ResultSet rs = null;
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement("select * from "+tableName+" where phno like ? and name like ? and cellno like ?");
            ps.setString(1,"%"+phno+"%");
            ps.setString(2,"%"+name+"%");
            ps.setString(3,"%"+cellno+"%");
            rs = ps.executeQuery();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return rs;
    }
}
