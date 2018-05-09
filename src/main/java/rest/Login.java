package rest;


import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import org.json.JSONObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@Path("login")
public class Login {

//Validate the user login information
    @POST
    @Produces("application/json")
    public String login(String loginInfo) throws java.rmi.RemoteException {
        JSONObject userinfo = new JSONObject(loginInfo);

        Brugeradmin ba = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e){
        }

        Bruger b;
        JSONObject valid = new JSONObject();
        
        try {
            b = ba.hentBruger(userinfo.getString("username"), userinfo.getString("password"));
            valid.put("Valid", true);

        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            valid.put("Valid", false);

        }
        
        return valid.toString();

    }
}
