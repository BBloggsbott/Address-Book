/**
 * A package to Handle the data for the address book
 */
package datamanager;

import java.util.HashMap;
/**
 *This Class is to Handle the data structures in the address book
 * @author bbloggsbott
 */
public class DataHandler {
    HashMap data;
    
    /**
     * This constructor initializes the HashMap to handle data
     */
    public DataHandler(){
        data = new HashMap();
    }
    
    /**
     * This function creates a HashMap to hold the data
     * @param id This represents the ID of the data
     * @param name This represents the name of the data
     * @param address This represents the address of the data
     * @param dob Thir represents the dob of the data
     * @param phno This represents the PhNo. of the data
     * @param cellno This represents the CellNo of the data
     */
    public void newData(int id,String name,String address,String dob,String phno,String cellno){
        data.put("id",id);
        data.put("name",name);
        data.put("address",address);
        data.put("dob",dob);
        data.put("phno",phno);
        data.put("cellno",cellno);
    }
    
    /**
     * This function returns the HashMAp object
     * @return HashMap Returns the HashMAp object containing the data
     */
    public HashMap getHashMap(){
        return data;
    }
    
    /**
     * This function clears all the mapping in the data
     */
    public void clearData(){
        data.clear();
    }
}
