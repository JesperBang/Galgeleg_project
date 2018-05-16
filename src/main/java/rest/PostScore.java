/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import database.UserDAOSOAPI;
import database.UserDTO;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import jwtHandler.JWTHandler;
import org.json.JSONObject;
import jwtHandler.JWTHandler.AuthException;

/**
 *
 * @author jespe
 */
@Path("postscore")
public class PostScore {
    static UserDAOSOAPI soapI;
    URL url;
    QName qname;
    Service service;
    Validate val = new Validate();
    UserDTO user = new UserDTO();
        
    public PostScore() throws MalformedURLException {
        URL url = new URL("http://ubuntu4.saluton.dk:9911/SQL_Soap?wsdl");
        QName qname = new QName("http://database/", "SOAPImplService");
        Service service = Service.create(url, qname);
        soapI = service.getPort(UserDAOSOAPI.class);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String PostScore(String scoredata){
        //Fetching input from JSON body
        JSONObject input = new JSONObject(scoredata);  
        
        //Validating and posting
        try {
            JWTHandler.validateToken(input.getString("jwt"));

            //Creating userDTO for soap service
            user.setStudentID(input.getString("username"));
            user.setScore(input.getDouble("score"));
            user.setNumber_of_tries(input.getInt("numtries"));
            user.setTime_used(input.getDouble("time"));
                
            //requesting score creation with userDTO info
            soapI.createScore(user);

            return "Score of "+input.getDouble("score")+" was posted for user "+input.getString("username");
        } catch (Exception e) {
            return "failed to post score with error\n\n"+e.toString();
        }
    }
}
