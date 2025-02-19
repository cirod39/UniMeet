package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudenteService {
	public static int aggiungiStudente(Studente s) {
        int result = 0;

        try (Connection con = DriverManagerConnectionPool.getConnessione();
        	PreparedStatement ps = con.prepareStatement("INSERT INTO studente (matricola, nome, cognome, email, passwordHash, domandaSicurezza, risposta) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, s.getMatricola());
            ps.setString(2, s.getNome());
            ps.setString(3, s.getCognome());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPassword());
            ps.setString(6, s.getDomanda());
            ps.setString(7, s.getRisposta());

            result = ps.executeUpdate();

            System.out.println("Inserimento riuscito, righe inserite: " + result);

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore SQL durante l'inserimento: " + e.getMessage());
        }
        return result;
    }
	
	public static Studente loginStudente(String email, String password) throws Exception {
		Studente studente = null;
		String sql = "SELECT * FROM studente WHERE email = ? AND passwordHash = ?";
		
		try (Connection conn = DriverManagerConnectionPool.getConnessione();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				studente = new Studente();
				studente.setEmail(rs.getString("email"));
				studente.setPassword(rs.getString("passwordHash"));
				studente.setNome(rs.getString("nome"));
				studente.setCognome(rs.getString("cognome"));
				studente.setMatricola(rs.getString("matricola"));
			}
		}
		return studente;
	}
    //ricerca professore tramite mail
	  public static Studente cercaStudenteEmail(String email) throws Exception {
		  
	        Studente studente = null;
	        String sql = "SELECT * FROM studente WHERE email = ?";
	        
	        try (Connection conn = DriverManagerConnectionPool.getConnessione();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	        	
	            stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();
	            
	            if (rs.next()) {
	                studente = new Studente();
	                studente.setEmail(rs.getString("email"));
	                studente.setPassword(rs.getString("passwordHash"));
	                studente.setNome(rs.getString("nome"));
	                studente.setCognome(rs.getString("cognome"));
	                studente.setMatricola(rs.getString("matricola"));
	                studente.setDomanda(rs.getString("domandaSicurezza"));
	                studente.setRisposta(rs.getString("risposta"));
	            }
	        }
	        return studente;
	    }
	  
	  public static Boolean modificaStudente (Studente s) throws SQLException {
      	
      	String sql = "UPDATE studente SET nome = ?, cognome = ?, email = ?, passwordHash = ?, domandaSicurezza= ?, risposta = ? WHERE matricola = ?";
      	 try (Connection conn = DriverManagerConnectionPool.getConnessione();
                   PreparedStatement stmt = conn.prepareStatement(sql)){
      		 
      		 	conn.setAutoCommit(false);
      		 
      	        stmt.setString(1, s.getNome());
      	        stmt.setString(2, s.getCognome());
      	        stmt.setString(3, s.getEmail());
      	        stmt.setString(4, s.getPassword());
      	        stmt.setString(5, s.getDomanda());
      	        stmt.setString(6, s.getRisposta());
      	        stmt.setString(7, s.getMatricola());
      	        int rows = stmt.executeUpdate();
      	        
      	        conn.commit();
      	        
      	        return rows > 0;
      	    } catch (SQLException e) {
      	        System.err.println("Errore nell'aggiornamento dello studente: " + e.getMessage());
      	        return false;
      	    }
	  }
//non viene utilizzato perchè il professore non cerca lo studente per matricola
	  public Studente trovaPerMatricola(String matricola) throws SQLException {
		    String str = "SELECT * FROM studente s WHERE s.matricola = ?";
		    try (Connection con = DriverManagerConnectionPool.getConnessione();
		         PreparedStatement st = con.prepareStatement(str)) {
		        
		        st.setString(1, matricola);  
		        
		        try (ResultSet rs = st.executeQuery()) {
		            if (rs.next()) { 
		                
		                Studente studente = new Studente();
		                studente.setMatricola(rs.getString("matricola"));
		                studente.setNome(rs.getString("nome"));
		                studente.setCognome(rs.getString("cognome"));
		                studente.setDomanda(rs.getString("domdandaSicurezza"));
		                studente.setRisposta(rs.getString("risposta"));
		                studente.setEmail(rs.getString("email"));
		                studente.setPassword(rs.getString("passwordHash"));
		                return studente;
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("Errore nella ricerca dello studente per matricola: " + e.getMessage());
		    }
		    return null;  
		}

	public String getNomeStudenteByMatricola(String codice) throws SQLException {
		String query = "SELECT nome FROM studente WHERE matricola = ?";
		String nome = null;

		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, codice);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nome = rs.getString("nome");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella ricerca del nome dello studente: " + e.getMessage());
			return null; // Restituisce null in caso di errore
		}

		return nome; // Restituisce il nome dello studente trovato, o null se non trovato
	}

		public String getCognomeStudenteByMatricola(String codice)throws SQLException {
		String query = "SELECT cognome FROM studente WHERE matricola = ?";
			String cognome = null;
			try (Connection con = DriverManagerConnectionPool.getConnessione();
				 PreparedStatement ps = con.prepareStatement(query)) {
				ps.setString(1, codice);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					cognome = rs.getString("cognome");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore nella ricerca delle prenotazioni del ricevimento del cognome dello studente: " + e.getMessage());
				return null; // Restituisce null in caso di errore
			}
	return cognome;
		}





	}
	
	 
      
