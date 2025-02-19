package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneRicevimentoService {
	
	
	public static boolean aggiungiPrenotazioneRicevimento(PrenotazioneRicevimento pr) {
	    try (Connection con = DriverManagerConnectionPool.getConnessione()) {
	        String query = "INSERT INTO insegnamento(giorno, ora, note, stato, codiceProfessore,matricolaStudente) VALUES (?, ?,?,?,?,?);";
	        PreparedStatement ps = con.prepareStatement(query);

	        ps.setString(1, pr.getGiorno());
	        ps.setString(2, pr.getOra());
	        ps.setString(3, pr.getNota());
	        ps.setString(4,pr.getStato());
	        ps.setString(5, pr.getCodiceProfessore());
	        ps.setString(6, pr.getMatricolaStudente());

	        if(ps.execute())
	        	return true;
	        else
	        	return false;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Errore nell'aggiunta della prenotazione" + e.getMessage());
	        return false;
	    }
	}
	public static boolean rimuoviPrenotazione(PrenotazioneRicevimento pr) {
		try(Connection con= DriverManagerConnectionPool.getConnessione()){
			PreparedStatement ps = con.prepareStatement("DELETE FROM prenotazioneRicevimento WHERE matricolaStudente= ? AND codiceProfessore = ?;");
			ps.setString(1, pr.getMatricolaStudente());
			ps.setString(2, pr.getCodiceProfessore());
			
			if(ps.execute())
				return true;
			else
				return false;
			
	}catch(SQLException e){
		e.printStackTrace();
		System.out.println("errore nella cancellazione della prenotazione del ricevimento"+e.getMessage());
		return false;
	}
	}
	public boolean rimuoviPrenotazionePerCodice(int codice) {
	    try (Connection con = DriverManagerConnectionPool.getConnessione()) {
	        String query = "DELETE FROM prenotazioneRicevimento WHERE codice = ?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, codice);

	        // Esegui la query e ottieni il numero di righe influenzate
	        int rowsAffected = ps.executeUpdate();
	        con.commit();
	        return rowsAffected > 0;  // Se sono state cancellate delle righe, ritorna true
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Errore nella cancellazione della prenotazione del ricevimento: " + e.getMessage());
	        return false;
	    }
	}

	
	
	public PrenotazioneRicevimento ricercaPrenotazione(int codicePrenotazione) {
	    PrenotazioneRicevimento prenotazione = null;

	    String query = "SELECT * FROM prenotazioneRicevimento WHERE codice = ?;";
	    try (Connection con = DriverManagerConnectionPool.getConnessione();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setInt(1, codicePrenotazione); 

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) { 
	                
	                prenotazione = new PrenotazioneRicevimento(
	                		rs.getInt("codice"),
	                        rs.getString("stato"),
	                        rs.getString("giorno"), 
	                        rs.getString("ora"),
	                        rs.getString("note"),
	                        rs.getString("codiceProfessore"),
	                        rs.getString("matricolaStudente"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Errore nella ricerca per codice della prenotazione: " + e.getMessage());
	    }

	    return prenotazione; 
	}
	
	public List<PrenotazioneRicevimento> stampaPrenotazioni(String matricolaStudente) {
	    List<PrenotazioneRicevimento> prenotazioni = new ArrayList<>();

	    String query = "SELECT * FROM prenotazioneRicevimento WHERE matricolaStudente = ?;";
	    try (Connection con = DriverManagerConnectionPool.getConnessione();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, matricolaStudente); 

	        try (ResultSet rs = ps.executeQuery()) {
	        
	            while (rs.next()) {
	                PrenotazioneRicevimento prenotazione = new PrenotazioneRicevimento(
	                		rs.getInt("codice"),
	                        rs.getString("stato"),
	                        rs.getString("giorno"), 
	                        rs.getString("ora"),
	                        rs.getString("note"),
	                        rs.getString("codiceProfessore"),
	                        rs.getString("matricolaStudente")
	                );
	                
	                prenotazioni.add(prenotazione); 
	             
	            }
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Errore nella ricerca delle prenotazioni: " + e.getMessage());
	    }
	 return prenotazioni; 
	}


	public PrenotazioneRicevimento ricercaPrenotazionePerProfessore(Professore p) {
		PrenotazioneRicevimento prenotazione = null;
		try(Connection con= DriverManagerConnectionPool.getConnessione()){
			PreparedStatement ps = con.prepareStatement("SELECT * FROM prenotazioneRicevimento pr WHERE pr.codiceProfessore = ?;");
			ps.setString(1, p.getCodiceProfessore());
			  try (ResultSet rs = ps.executeQuery()) {
		            if (rs.next()) { 
		                
		                prenotazione = new PrenotazioneRicevimento(
		                		rs.getInt("codice"),
		                        rs.getString("stato"),
		                        rs.getString("giorno"), 
		                        rs.getString("ora"),
		                        rs.getString("note"),
		                        rs.getString("codiceProfessore"),
		                        rs.getString("matricolaStudente"));
		            }
		        }
			
	}catch(SQLException e){
		e.printStackTrace();
		System.out.println("errore nella ricerca per codice della prenotazione"+e.getMessage());
		
	}
		return prenotazione;
	}
	 public String getCodiceProfessoreDiPrenotazione(int codicePrenotazione)throws SQLException {
  	   String codiceProfessore = null;
  	   String query = "SELECT codiceProfessore FROM prenotazioneRicevimento  WHERE codice= ? ";
  	   try (Connection con = DriverManagerConnectionPool.getConnessione();
                 PreparedStatement ps = con.prepareStatement(query)) {
                
                ps.setInt(1, codicePrenotazione);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        codiceProfessore = rs.getString("codiceProfessore");
                    }
                }
            }

            return codiceProfessore;
  	   
     }
	public boolean confermaPrenotazionePerProfessore(String codiceProfessore,int codice)throws SQLException {
		String query = "UPDATE prenotazioneRicevimento SET stato = 'accettato' WHERE codiceProfessore=? AND codice=?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1,codiceProfessore);
			ps.setInt(2, codice);

			int rowsAffected = ps.executeUpdate();
			con.commit();
			return rowsAffected > 0;

		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella conferma della prenotazione del ricevimento: " + e.getMessage());
			return false;
		}
	}
	public boolean rifiutoPrenotazionePerProfessore(String codiceProfessore,int codice)throws SQLException {
		String query = "UPDATE prenotazioneRicevimento SET stato = 'rifiutato' WHERE codiceProfessore=? AND codice=?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1,codiceProfessore);
			ps.setInt(2,codice);

			int rowsAffected = ps.executeUpdate();
			con.commit();
			return rowsAffected > 0;

		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel rifiuto della prenotazione del ricevimento: " + e.getMessage());
			return false;
		}
	}

	public List<PrenotazioneRicevimento> stampaPrenotazioneProfessore(String codiceProfessore) throws SQLException {
		List<PrenotazioneRicevimento> prenotazioni = new ArrayList<>();
		String query = "SELECT * FROM prenotazioneRicevimento WHERE codiceProfessore = ?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, codiceProfessore);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					PrenotazioneRicevimento prenotazione = new PrenotazioneRicevimento(
							rs.getInt("codice"),
							rs.getString("stato"),
							rs.getString("giorno"),
							rs.getString("ora"),
							rs.getString("note"),
							rs.getString("codiceProfessore"),
							rs.getString("matricolaStudente")
					);
					prenotazioni.add(prenotazione);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella ricerca delle prenotazioni del ricevimento del professore: " + e.getMessage());
			return null; // Restituisce null in caso di errore
		}
		return prenotazioni; // Restituisce la lista completa delle prenotazioni
	}

	public String getmatricolaStudenteDaPrenotazione(int codicePrenotazione)throws SQLException {
		String matricolaStudente = null;
		String query = "SELECT matricolaStudente FROM prenotazioneRicevimento  WHERE codice= ? ";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, codicePrenotazione);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					matricolaStudente = rs.getString("matricolaStudente");
				}
			}
		}

		return matricolaStudente;

	}

	public List<PrenotazioneRicevimento> stampaRicevimentiAccettati(String codiceProfessore){
		List<PrenotazioneRicevimento> ricevimentiAccettati = new ArrayList<>();
		String query = "SELECT * FROM prenotazioneRicevimento WHERE codiceProfessore = ? AND stato='accettato'";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, codiceProfessore);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					PrenotazioneRicevimento prenotazione = new PrenotazioneRicevimento(
							rs.getInt("codice"),
							rs.getString("stato"),
							rs.getString("giorno"),
							rs.getString("ora"),
							rs.getString("note"),
							rs.getString("codiceProfessore"),
							rs.getString("matricolaStudente")
					);
					ricevimentiAccettati.add(prenotazione);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("errore nella stampa dei ricevimenti accettati " + e.getMessage());
			return null; // Restituisce null in caso di errore
		}
		return ricevimentiAccettati; // Restituisce la lista completa delle prenotazioni

	}

	 //-------------------------------CIRO-----------------------------

	public static void inserisciPrenotazione(PrenotazioneRicevimento prenotazione) throws SQLException {
		String sql = "INSERT INTO prenotazioneRicevimento (giorno, ora, note, codiceProfessore, matricolaStudente, stato) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, prenotazione.getGiorno());
			stmt.setString(2, prenotazione.getOra());
			String nota = prenotazione.getNota();
			if (nota == null || nota.isEmpty()) {
				nota = "Non ci sono note";
			}
			stmt.setString(3, nota);
			stmt.setString(4, prenotazione.getCodiceProfessore());
			stmt.setString(5, prenotazione.getMatricolaStudente());
			stmt.setString(6, prenotazione.getStato());

			stmt.executeUpdate();
			conn.commit();
		}
	}
}
