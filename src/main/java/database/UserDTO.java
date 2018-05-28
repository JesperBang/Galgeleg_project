/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class UserDTO {
    private static final long serialVersionUID = -7272979590540794430L;
 
	public String student_Id, href = "http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/score";                     
	
	int number_of_tries; 
	
	double time_used;     
	
	double score;                 

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
        
        public void getHref(String href){this.href = href;}
        public String getHref(){return href;}
    
	public String toString() { return "\n" + student_Id + "\t" + number_of_tries + "\t" + time_used + "\t" + score + "\t" + href; }
}
