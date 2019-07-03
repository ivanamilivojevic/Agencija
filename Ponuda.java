package agencija;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ponuda {
	int idTerm;
	String naziv;
	String drzava;
	int datumOd;
	int datumDo;
	int preostaloMesta;
	int cena;
	
	public Ponuda(int idTerm, String naziv, String drzava, int datumOd, int datumDo, int preostaloMesta, int cena) {
		this.idTerm = idTerm;
		this.naziv = naziv;
		this.drzava = drzava;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.preostaloMesta = preostaloMesta;
		this.cena = cena;
	}

	public String toString() {
		return String.format("(%d) %s(%s) %d - %d / %d€ : %d", idTerm, naziv, drzava, datumOd, datumDo, cena, preostaloMesta);
	}
	
	private void umanjiPreostalaMesta() throws SQLException {
		String sql = "UPDATE Termin SET PreostaloMesta = PreostaloMesta - 1 WHERE idTer = ? AND PreostaloMesta > 0";
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)){
			ps.setInt(1, idTerm);
			ps.execute();
		}
		preostaloMesta--;	
	}
	
	public boolean zakupi(int idOsobe) throws SQLException {
		if(preostaloMesta<=0)
			return false;
		
		String sql = "INSERT INTO Putuje (IdOsobe, IdTer) VALUES (?, ?)";
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)){
			ps.setInt(1, idOsobe);
			ps.setInt(2, idTerm);
			ps.executeUpdate();
		}		
		
		umanjiPreostalaMesta();
		return true;
	}
	
	public boolean zakupi(String ime, String prezime, String kontakt) throws SQLException {
		if(preostaloMesta<=0)
			return false;
		
		
		String sql = "INSERT INTO Osoba (Ime, Prezime, Kontakt) VALUES (?, ?, ?)";
		int idOsobe;
		try(PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){ 
			ps.setString(1, ime);
			ps.setString(2, prezime);
			ps.setString(3, kontakt);
			ps.executeUpdate();
		
			
			try(ResultSet ids = ps.getGeneratedKeys()){
				if(ids.next())
					idOsobe = ids.getInt(1);
				else throw new SQLException("Greska u upitu!");
			}
		}
		
				
		return zakupi(idOsobe);	
	}
}