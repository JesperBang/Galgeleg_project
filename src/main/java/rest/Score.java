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
import database.UserDAO;
import database.UserDAOSOAPI;
import database.UserDTO;
import galgeleg.GalgeI;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import static rest.GameLogic.game;

/**
 *
 * @author jespe
 */

@Path("score")
public class Score {
    static UserDAOSOAPI soapI;
    URL url;
    QName qname;
    Service service;
        
    public Score() throws MalformedURLException {
        URL url = new URL("http://ubuntu4.saluton.dk:9911/SQL_Soap?wsdl");
        QName qname = new QName("http://database/", "SOAPImplService");
        Service service = Service.create(url, qname);
        soapI = service.getPort(UserDAOSOAPI.class);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> ShowScores() {
	List<UserDTO> allScores = null;

	try {
            allScores = soapI.getStudentList();
            System.out.println(allScores);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
            return allScores;
	}
}
