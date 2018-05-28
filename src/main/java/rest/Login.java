package rest;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import database.UserDTO;
import java.net.MalformedURLException;
import org.json.JSONObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.persistence.Entity;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jwtHandler.JWTHandler;

@Path("login")
public class Login {

//Validate the user login information
    @POST
    @Produces("application/json")
    public Response login(String loginInfo) throws java.rmi.RemoteException {
        JSONObject userinfo = new JSONObject(loginInfo);
        
        UserDTO user = new UserDTO();
        Brugeradmin ba = null;
        Bruger b;

        String uname = userinfo.getString("username"), upass = userinfo.getString("password");

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
        }

        try {
            b = ba.hentBruger(uname, upass);
            if (b.brugernavn.equals(uname) && b.adgangskode.equals(upass)) {
                System.out.println("LOGGING IN");
                System.out.println(uname + " " + upass);

                user.setStudentID(uname);
                System.out.println(user);

                String json = JWTHandler.generateJwtToken(user);
                String hyper = "{"
                        + "\"jwt\": \""+json+"\","
                        + " \"Links\":["
                        + "{ \"Rel\": \"self\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/validatejwt\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"scores\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/score\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"start spil\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/nulstil\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"hent ordet\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/getsynligtord\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"gæt bogstav\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/gaetbogstav\","
                        + " \"Method\": \"POST\"},"
                        + "{ \"Rel\": \"spil status\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/logstatus\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"spil vundet?\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/erspilletvundet\","
                        + " \"Method\": \"GET\"},"
                        + "{ \"Rel\": \"spil tabt?\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/erspillettabt\","
                        + " \"Method\": \"GET\"}"
                        + "]"
                        + "}";
                return Response.ok(hyper, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Bad credentials for user: " + uname).build();
            }

        } catch (Exception e) {

            System.out.println("Error logging in with: 401");
            return Response.status(Response.Status.UNAUTHORIZED).entity("Bad credentials for user: " + uname).build();
        }
    }
}
