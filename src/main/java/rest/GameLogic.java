/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import galgeleg.GalgeI;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import jwtHandler.JWTHandler;
import org.json.JSONObject;

/**
 *
 * @author jespe
 */
@Path("game")
public class GameLogic {

    @Context
    HttpServletRequest request;
    static GalgeI game;
    URL url;
    QName qname;
    Service service;

    public GameLogic() throws MalformedURLException {
        //this.url = new URL("http://localhost:9913/galgeleg?wsdl");
        this.url = new URL("http://ubuntu4.saluton.dk:9913/galgeleg?wsdl");
        this.qname = new QName("http://galgeleg/", "GalgelogikService");
        this.service = Service.create(url, qname);
        game = service.getPort(GalgeI.class);
    }

    @Path("nulstil")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Nulstil() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            game.nulstil();
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }

    @Path("logstatus")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Logstatus() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);
        try {
            String response = game.logStatus();
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Path("getordet")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String GetOrdet() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            String response = game.getOrdet();

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Path("getbrugtebogstaver")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> GetBrugteBogstaver() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        ArrayList<String> bogstaver = null;

        try {
            bogstaver = game.getBrugteBogstaver();
            return bogstaver;
        } catch (Exception e) {
            return null;
        }
    }

    @Path("getsynligtord")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String GetSynligtOrd() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            String response = game.getSynligtOrd();
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Path("erspilletvundet")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean ErSpilletVundet() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            Boolean response = game.erSpilletVundet();
            return response;
        } catch (Exception e) {
            return false;
        }
    }

    @Path("erspillettabt")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ErSpilletTabt() throws MalformedURLException {
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            Boolean response = game.erSpilletTabt();
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed with error: \n\n" +e.toString()).build();
        }
    }

    @Path("gaetbogstav")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response GaetBogstav(String guess) throws MalformedURLException {
        JSONObject input = new JSONObject(guess);
        String header = request.getHeader("Authorization");
        System.out.println("header: " + header);

        try {
            game.gætBogstav(input.getString("bogstav"));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed with error: \n\n" +e.toString()).build();
        }
    }

}
