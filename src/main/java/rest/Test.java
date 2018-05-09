package rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("test")
public class Test {

    @GET
    public String GetTest(){
        return "test";
    }


    @POST
    public String PostTest(String s){
        return s+"test";
    }
}
