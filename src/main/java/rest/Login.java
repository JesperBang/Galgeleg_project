package rest;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import database.UserDTO;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
        
        String secret = "René Über";
        String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

        byte[] cipherData = Base64.getDecoder().decode(cipherText);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

        byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedData = aesCBC.doFinal(encrypted);
        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

        System.out.println(decryptedText);
        
        String uname = userinfo.getString("username"), upass = userinfo.getString("password");

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e){}

        try {
            System.out.println("LOGGING IN");
            System.out.println(uname+" "+upass);
            b = ba.hentBruger(uname, upass);
            user.setStudentID(uname);
            user.setScore(1);
            user.setNumber_of_tries(0);
            user.setTime_used(100);
            System.out.println(user);
            System.out.println(JWTHandler.generateJwtToken(user));
            return JWTHandler.generateJwtToken(user);
        } catch (Exception e) {
            System.out.println("Error logging in with: ");
            e.printStackTrace();
            return null;
        } 
    }
}