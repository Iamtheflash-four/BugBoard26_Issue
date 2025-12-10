package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection 
{
	public static Connection connect() throws SQLException
	{
		String url = System.getenv("DATABASE");

        try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = DriverManager.getConnection("jdbc:postgresql://ep-cool-band-ab59fecz-pooler.eu-west-2.aws.neon.tech/neondb?user=neondb_owner&password=npg_v4gKmBXVcwo0&sslmode=require&channelBinding=require");
		
		return connection;
	}
}
