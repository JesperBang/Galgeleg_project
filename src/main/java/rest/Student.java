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
import database.UserDAOSOAPI;
import database.UserDTO;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

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
        URL url = new URL("http://ec2-35-177-117-75.eu-west-2.compute.amazonaws.com:9915/SQL_Soap?wsdl");
        QName qname = new QName("http://database/", "SOAPImplService");
        Service service = Service.create(url, qname);
        soapI = service.getPort(UserDAOSOAPI.class);
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ShowStudents(@PathParam("id") String id) {
        List<UserDTO> allStudents = null;

        try {
            allStudents = soapI.getStudent(id);
            return Response.ok(allStudents, MediaType.APPLICATION_JSON).build();
        
        } catch (DALException e) {    
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Entity not found:" +e.toString()).build();
        }

    }
}
