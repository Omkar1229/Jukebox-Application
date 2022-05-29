package org.test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	Connection con=null;
	public User newUser(User u) throws SQLException
	{ 
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("insert into user(name) values (?)");
		ps.setString(1, u.getUserName());
		ps.execute();
		PreparedStatement ps1=con.prepareStatement("select user_id from user where name like ?");
		ps1.setString(1, u.getUserName());
		ResultSet rs=ps1.executeQuery();
		rs.next();
		u.setUserId(rs.getInt(1));
		rs.close();
		ps.close();
		ps1.close();
		con.close();
		return u;
		
	}
	
	public boolean displayUser() throws SQLException
	{
		boolean isPresent=false;
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select * from user");
		ResultSet r=ps.executeQuery();
		try {
			if(!r.next())
			{
				throw new EmptyResultsetException("There are no user accounts");
			}
			else {
				System.out.println("==========================================");
				System.out.format("%15s %15s\n","User Id","User Name");
				System.out.println("==========================================");
				while(r.next())
				{
					System.out.format("%15s %15s\n",r.getInt(1),r.getString(2));
				}
				System.out.println("==========================================");
				r.close();
				ps.close();
				con.close();
				return true;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return isPresent;
	}
	
	public User currentUser(int userId) throws SQLException
	{
		User user = null;
		con=DbConfig.getConnection();
		PreparedStatement ps=con.prepareStatement("select * from user where user_id=?");
		ps.setInt(1, userId);
		ResultSet r=ps.executeQuery();
		try {
			if(!r.next())
			{
				throw new EmptyResultsetException("No currnet user with the given user id");

			}
			else {
				r.next();
				user=new User(r.getString(2));
				user.setUserId(userId);
				r.close();
				ps.close();
				con.close();
				return user;
			}
		}
		catch(EmptyResultsetException e)
		{
			e.printStackTrace();
		}
		return user;
	}
	
}
