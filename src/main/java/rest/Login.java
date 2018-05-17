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
import jwtHandler.JWTHandler;

@Path("login")
public class Login {

//Validate the user login information
    @POST
    @Produces("application/json")
    public String login(String loginInfo) throws java.rmi.RemoteException {
        JSONObject userinfo = new JSONObject(loginInfo);
        UserDTO user = new UserDTO();
        Brugeradmin ba = null;
        Bruger b;
        
        String uname = userinfo.getString("username"), upass = userinfo.getString("password");

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e){}

        try {
            b=ba.hentBruger(uname, upass);
            if(b.brugernavn.equals(uname) && b.adgangskode.equals(upass)){
              System.out.println("LOGGING IN");
              System.out.println(uname+" "+upass);

              user.setStudentID(uname);
              System.out.println(user);

              return JWTHandler.generateJwtToken(user);
            }else{ return null; }
            
        } catch (Exception e) {
            System.out.println("Error logging in with: ");
            e.printStackTrace();
            return null;
        } 
    }
}