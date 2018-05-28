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
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import jwtHandler.JWTHandler;
import org.json.JSONObject;
import jwtHandler.JWTHandler.AuthException;
import org.json.JSONException;

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
        URL url = new URL("http://ec2-35-177-117-75.eu-west-2.compute.amazonaws.com:9915/SQL_Soap?wsdl");
        QName qname = new QName("http://database/", "SOAPImplService");
        Service service = Service.create(url, qname);
        soapI = service.getPort(UserDAOSOAPI.class);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response PostScore(String scoredata) throws AuthException {
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
            

                 return Response.status(Response.Status.CREATED).entity("Score of " + input.getDouble("score") + " was posted for user " + input.getString("username")).build();

        
        } catch (AuthException ae) {
                         if(JWTHandler.validateToken(input.getString("jwt"))==null){
            return Response.status(Response.Status.UNAUTHORIZED).entity( "failed to post score: Empty token.").build();
          }
                         else
            return Response.status(Response.Status.UNAUTHORIZED).entity("failed to post score with error\n\n" + ae.toString()).build();
        }
        catch (JSONException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("failed to post score with error\n\n" + e.toString()).build();
        }
    }
}
