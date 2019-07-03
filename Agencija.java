package agencija;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Agencija {
	String naziv;
	
	public Agencija(String naziv) throws SQLException {
		this.naziv = naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public AdminPanel login(String username, String password) throws SQLException {
		PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * FROM Administrator WHERE KorIme=? AND Lozinka=?");
		ps.setString(1,username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			return new AdminPanel();
		return null;
	}
	
	public List<Ponuda> pretrazi(String drzava) throws SQLException{
		List<Ponuda> list = new LinkedList<Ponuda>();
		String sql = "SELECT IdTer, Naziv, Drzava, Od, Do, PreostaloMesta, Cena FROM Skijaliste JOIN Termin ON Skijaliste.IdSki=Termin.IdSki Where Drzava=? ORDER BY Cena ASC";
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)){
			ps.setString(1, drzava);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Ponuda(
					rs.getInt("IdTer"),
					rs.getString("Naziv"),
					rs.getString("Drzava"),
					rs.getInt("Od"),
					rs.getInt("Do"),
					rs.getInt("PreostaloMesta"),
					rs.getInt("Cena")
				));
			}
		}
		
		return list;
	}
	public List<Ponuda> pretrazi(int maxCena) throws SQLException{
		List<Ponuda> list = new LinkedList<Ponuda>();
		String sql = "SELECT IdTer, Naziv, Drzava, Od, Do, PreostaloMesta, Cena FROM Skijaliste JOIN Termin ON Skijaliste.IdSki=Termin.IdSki Where Cena<=? ORDER BY Cena ASC";
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)){
			ps.setInt(1, maxCena);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Ponuda(
					rs.getInt("IdTer"),
					rs.getString("Naziv"),
					rs.getString("Drzava"),
					rs.getInt("Od"),
					rs.getInt("Do"),
					rs.getInt("PreostaloMesta"),
					rs.getInt("Cena")
				));
			}
		}
		
		return list;
               }
        }