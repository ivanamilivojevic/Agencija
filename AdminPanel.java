package agencija;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminPanel {

	public boolean modifikujCene(double procenat) throws SQLException {
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement("UPDATE termin SET Cena = Cena + Cena * ?")){
			ps.setDouble(1, procenat);
			return ps.execute();
		}
	}
	public boolean ukloni(int idTerm) throws SQLException{
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement("DELETE FROM termin WHERE IdTer = ?")){
			ps.setInt(1, idTerm);
			return ps.execute();
		}
	}
	
}
