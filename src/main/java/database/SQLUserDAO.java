/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLUserDAO implements UserDAO {
    private Connector connector = new Connector();
	@Override
	public UserDTO getStudent(String student_Id) throws DALException {
		ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("select * from brugere where student_Id = ?;");
			stmt.setString(1, student_Id);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			throw new DALException(e.getMessage()); 
		}
	    try {
	    	if (!rs.first()) throw new DALException("Student " + student_Id + " findes ikke");
	    	UserDTO user = new UserDTO ();
	    	user.setStudentID(rs.getString("student_Id"));
			user.setNumber_of_tries(rs.getInt("number_of_tries"));
			user.setTime_used(rs.getDouble("time_used"));
			user.setScore(rs.getDouble("score"));
	    	return user;
	    }
	    catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	}

	@Override
	public List<UserDTO> getStudentList() throws DALException {
		List<UserDTO> list = new ArrayList<UserDTO>();
		ResultSet rs;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(1)
			rs = connector.doQuery("select * from brugere order by score DESC;");
		} catch (Exception e) {
			throw new DALException(e.getMessage());
		}
		try
		{
			UserDTO user = new UserDTO();
			int last = 0;
			while (rs.next()){
				
				if (last != 0){
					list.add(user);
					user = new UserDTO();
				}
				
				user.setStudentID(rs.getString("student_Id"));
				user.setNumber_of_tries(rs.getInt("number_of_tries"));
				user.setTime_used(rs.getDouble("time_used"));
				user.setScore(rs.getDouble("score"));
                                
				last++;
			}
			list.add(user);
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

    @Override
    public void createScore(String student_id, int number_of_tries, double time_used, double score) {
      
      	ResultSet rs = null;
		try { //Files.readAllLines(Paths.get("/UserCommands.txt")).get(0)
			PreparedStatement stmt = connector.getConnection().prepareStatement("INSERT INTO brugere (student_id, number_of_tries, time_used, score) values (?,?,?,?)");
			stmt.setString(1, student_id);
                        stmt.setInt(2, number_of_tries);
                        stmt.setDouble(3, time_used);
                        stmt.setDouble(4, score);
			rs = stmt.executeQuery();
		} catch (Exception e) {
                try { 
                throw new DALException(e.getMessage());
                    } catch (DALException ex) {
                        Logger.getLogger(SQLUserDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
    }
}
