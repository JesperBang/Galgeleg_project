/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jwtHandler.JWTHandler;
import jwtHandler.JWTHandler.AuthException;

/**
 *
 * @author jespe
 */
@Path("validatejwt")
public class Validate {

    //Fetching header
    @Context
    HttpServletRequest request;

    //Validate user login token
    @GET
    @Produces("application/json")
    public Response validJWT() {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            if (header != null) {
                JWTHandler.validateToken(header.split(" ")[1]);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (AuthException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity("Failed to validate jwt due to: \n\n"+e.toString()).build();
        }

    }

}
