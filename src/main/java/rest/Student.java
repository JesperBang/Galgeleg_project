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
import javax.ws.rs.PathParam;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import static rest.GameLogic.game;
import static rest.Score.soapI;
/**
 *
 * @author s143775
 */

@Path("student")
public class Student {
      static UserDAOSOAPI soapI;
    URL url;
    QName qname;
    Service service;
    
        
       public Student() throws MalformedURLException {
        URL url = new URL("http://localhost:9915/SQL_Soap?wsdl");
        QName qname = new QName("http://database/", "SOAPImplService");
        Service service = Service.create(url, qname);
        soapI = service.getPort(UserDAOSOAPI.class);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> ShowStudents(@PathParam("id") String id) {
	List<UserDTO> allStudents = null;

	try {
            allStudents = soapI.getStudent(id);
            System.out.println(allStudents);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
            return allStudents;
	}
}
