/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import database.DALException;
import database.SQLUserDAO;
import database.UserDAO;
import database.UserDTO;

/**
 *
 * @author jespe
 */

@Path("score")
public class Score {
    UserDAO users = new SQLUserDAO();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> ShowScores() {
	List<UserDTO> allScores = null;

	try {
            allScores = users.getStudentList();
            System.out.println(allScores);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
            return allScores;
	}
}
