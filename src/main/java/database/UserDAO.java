/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.List;


public interface UserDAO {
        UserDTO getStudent(String student_id) throws DALException;
	List<UserDTO> getStudentList() throws DALException;
        void createScore(String student_id, int number_of_tries, double time_used, double score);
}
