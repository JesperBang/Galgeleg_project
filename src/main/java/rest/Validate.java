/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;

/**
 *
 * @author jespe
 */
@Path("validatejwt")
public class Validate {
    //Fetching header
    @Context HttpServletRequest request;
    
    //Validate user login token
    @GET
    @Produces("application/json")
    public boolean validJWT() {
        String header = request.getHeader("Authorization");
        System.out.println("header: " +header);
        
        try {
            if(header != null){
                JWTHandler.validateToken(header.split(" ")[1]);
                return true;
            }else{ return false;}
        } catch (AuthException e) { 
            e.printStackTrace(); 
            return false;
        }
        
    }
    
}
