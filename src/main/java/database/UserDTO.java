/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;
public class UserDTO {
    private static final long serialVersionUID = -7272979590540794430L;
 
	public String student_Id;                     
        
	int number_of_tries; 
	
	double time_used;     
	
	double score; 
        
        Map<String, String> map = new HashMap<String, String>(){
            {
                put("Rel", "user");
                put("href", "http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/score");
                put("Method", "GET");
            }
        };
         
	public UserDTO(){
	}

        public void setStudentID(String id){this.student_Id = id;};
        public String getStudentID(){return student_Id;}
        
        public void setNumber_of_tries(int tries){this.number_of_tries = tries;};
        public int getNumber_of_tries(){return number_of_tries;}
        
        public void setTime_used(double time){this.time_used = time;};
        public double getTime_used(){return time_used;}
        
        public void setScore(double score){this.score = score;};
        public double getScore(){return score;}
        
        
        public Map<String, String> getLinks() {
            return map;
	}
        
        public void setLinks(Map<String, String> map) {
            this.map = map;
	}
    
	public String toString() { return "\n" + student_Id + "\t" + number_of_tries + "\t" + time_used + "\t" + score + "\t" + map; }
}
