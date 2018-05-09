package rest;


import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import org.json.JSONObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.json.JSONException;

@Path("email")
public class email {

//send email to according to student id
    @POST
    @Produces("application/json")
    public String email(String ID) throws java.rmi.RemoteException {
        JSONObject studentID = new JSONObject(ID);

        Brugeradmin ba = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e){
        }

        JSONObject valid = new JSONObject();

        try {
            ba.sendGlemtAdgangskodeEmail(studentID.getString("student"), "Dette er en test, husk at skifte kode");
            valid.put("Valid", true);
        } catch (IllegalStateException e) {
            //e.printStackTrace();
            valid.put("Valid", false);
        }
        
        return valid.toString();
    }
}
