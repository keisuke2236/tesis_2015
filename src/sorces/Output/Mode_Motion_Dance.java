/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import Database.DataBase;
import java.io.IOException;


public class Mode_Motion_Dance extends Abstract_Mode_parts {

    public Mode_Motion_Dance(DataBase database) throws IOException {
        super(database);
        about = "料理";
        
    }

    @Override
    public String Action() throws IOException {
        return "dance001";
    }

    @Override
    public int getTimeActionLevel() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String TimeAction() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
