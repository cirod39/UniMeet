package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class RicevimentoService {


	//Ricevimento()

	public static boolean aggiungiRicevimento(Ricevimento r) {
		// Variabile per il risultato dell'operazione
		int risultato = 0;

		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement("INSERT INTO ricevimento (giorno, ora, note, codiceProfessore) VALUES (?, ?, ?, ?)")) {

			// Impostazione dei parametri del PreparedStatement
			ps.setString(1, r.getGiorno());
			ps.setString(2, r.getOra());
			ps.setString(3, r.getNote());
			ps.setString(4, r.getCodiceProfessore());

			// Esecuzione della query
			risultato = ps.executeUpdate();
			con.commit();
			// Controlla se almeno una riga è stata inserita
			if (risultato > 0) {
				System.out.println("Inserimento riuscito, righe inserite: " + risultato);
				return true; // Inserimento riuscito
			} else {
				System.out.println("Nessuna riga inserita.");
				return false; // Nessuna riga inserita
			}

		} catch (SQLException e) {
			// Gestione delle eccezioni in caso di errore SQL
			e.printStackTrace();
			System.out.println("Errore SQL durante l'inserimento: " + e.getMessage());
			return false; // Se c'è stato un errore, ritorna false
		}
	}

	public boolean ricevimentoPresente(String giorno, String codiceProfessore) throws SQLException {
		String query = "SELECT * FROM ricevimento WHERE giorno = ? AND codiceProfessore = ?";
		boolean trovato = false;
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, giorno);
			ps.setString(2, codiceProfessore);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					trovato = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore durante il controllo delle collisioni dell'inserimento: " + e.getMessage());
		}
		return trovato;
	}

	public boolean ricevimentoPresenteOra(String giorno, String codiceProfessore,String ora) throws SQLException {
		String query = "SELECT * FROM ricevimento WHERE giorno = ? AND codiceProfessore = ? AND ora = ?";
		boolean trovato = false;
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, giorno);
			ps.setString(2, codiceProfessore);
			ps.setString(3, ora);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					trovato = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore durante il controllo delle collisioni dell'inserimento: " + e.getMessage());
		}
		return trovato;
	}

	public static boolean modificaRicevimento(Ricevimento r) {
		String query = "UPDATE ricevimento SET giorno = ?, ora = ?, note = ? WHERE codice = ?";

		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {


			ps.setString(1, r.getGiorno());
			ps.setString(2, r.getOra());
			ps.setString(3, r.getNote());

			ps.setInt(4, r.getCodice());




			ps.executeUpdate();
			con.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore durante la modifica del ricevimento: " + e.getMessage());
			return false;
		}
	}

	//aggiunto il metodo per la restituzione di tutto l'oggetto ricevimento importante da testare
	public Ricevimento getRicevimento(String ora, String giorno, String codiceProfessore, int codice) {
		String query = "SELECT * FROM ricevimento  WHERE ora = ? AND giorno = ? AND codiceProfessore = ? AND codice = ?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement st = con.prepareStatement(query)) {


			st.setString(1, ora);
			st.setString(2, giorno);
			st.setString(3, codiceProfessore);
			st.setInt(4, codice);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Ricevimento ricevimento = new Ricevimento(
							rs.getInt("codice"),
							rs.getString("giorno"),
							rs.getString("ora"),
							rs.getString("note"),
							rs.getString("codiceProfessore")
					);
					return ricevimento;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel prelevare il ricevimento: " + e.getMessage());
			return null;
		}
	}

	public List<Ricevimento> getListaRicevimenti(String codiceProfessore) {
		String query = "SELECT * FROM ricevimento WHERE codiceProfessore = ?";
		List<Ricevimento> listaRicevimenti = new ArrayList<>();

		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement st = con.prepareStatement(query)) {

			st.setString(1, codiceProfessore);

			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Ricevimento ricevimento = new Ricevimento(
							rs.getInt("codice"),
							rs.getString("giorno"),
							rs.getString("ora"),
							rs.getString("note"),
							rs.getString("codiceProfessore")
					);
					listaRicevimenti.add(ricevimento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel prelevare i ricevimenti per il codice professore: "
					+ codiceProfessore + ". Messaggio: " + e.getMessage());
		}

		// Restituisci una lista vuota in caso di errore o se non ci sono risultati
		return listaRicevimenti;
	}




	public Ricevimento getRicevimentoPerCodice(int codice) {
		String query = "SELECT * FROM ricevimento  WHERE codice = ?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement st = con.prepareStatement(query)) {

			st.setInt(1,codice);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Ricevimento ricevimento = new Ricevimento(
							rs.getInt("codice"),
							rs.getString("giorno"),
							rs.getString("ora"),
							rs.getString("note"),
							rs.getString("codiceProfessore")
					);
					return ricevimento;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel prelevare il ricevimento: " + e.getMessage());
			return null;
		}
	}





	public boolean eliminaRicevimento(Ricevimento r) {
		String query = "DELETE FROM ricevimento WHERE codiceProfessore = ? AND codice = ?";
		try (Connection con = DriverManagerConnectionPool.getConnessione();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setString(1, r.getCodiceProfessore());
			ps.setInt(2, r.getCodice());


			int rowsUpdated = ps.executeUpdate();
			con.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore durante la modifica del ricevimento: " + e.getMessage());
			return false;
		}

	}
public int getCodiceRicevimento(String ora, String giorno, String codiceProfessore) {
		int codice = 0;
		String query ="select codice from ricevimento where giorno = ? AND ora = ?";
	try (Connection con = DriverManagerConnectionPool.getConnessione();
		 PreparedStatement ps = con.prepareStatement(query)){

		ps.setString(1, ora);
		ps.setString(2, giorno);
		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				codice = rs.getInt("codice");
				return codice;
			}
		}

	}catch (SQLException e) {
		e.printStackTrace();
		System.out.println("Errore durante la modifica del ricevimento: " + e.getMessage());
		return codice;
	}
	return codice;
}

	//importante da testare
	public Date getGiornoRicevimento(Ricevimento r) {
		Date giorno = null;

		try(Connection con = DriverManagerConnectionPool.getConnessione()){
			String query = "SELECT giorno FROM ricevimento r WHERE r.giorno = ?;";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, r.getGiorno());
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				java.sql.Date sqlDate = rs.getDate("giorno");
				giorno = new Date(sqlDate.getTime());
			}

		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("connessione al database non effettuata per la restituzione del giorno di ricevimento " + e.getMessage());

		}
		return giorno;

	}
	public String getOraRicevimento(Ricevimento r) {
		String ora = null;
		try(Connection con = DriverManagerConnectionPool.getConnessione()){
			PreparedStatement ps = con.prepareStatement("SELECT ora FROM ricevimento r WHERE r.codiceProfessore=?;");


			ps.setString(1, r.getCodiceProfessore());

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			ora = rs.getString("ora");


		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("connessione al database non effettuata per la restituzione dell'ora di ricevimento " + e.getMessage());

		}
		return ora;
	}

public boolean setOraRicevimento(Ricevimento r) {
	try(Connection con = DriverManagerConnectionPool.getConnessione()){
		PreparedStatement ps = con.prepareStatement("UPDATE Ricevimento SET ora= ? WHERE r.codiceProfessore=?;");

		ps.setString(1, r.getOra());
		ps.setString(2, r.getCodiceProfessore());

		if(ps.execute())
			return true;
		else
			return false;



	}catch(SQLException e) {
		e.printStackTrace();
		System.out.println("connessione al database non effettuata per la modifica dell'ora di ricevimento " + e.getMessage());
		return false;

	}
}
public boolean setGiornoRicevimento(Ricevimento r) {
	try(Connection con = DriverManagerConnectionPool.getConnessione()){
		PreparedStatement ps = con.prepareStatement("UPDATE Ricevimento SET giorno= ? WHERE r.codiceProfessore=?;");

		ps.setString(1, r.getGiorno());
		ps.setString(2, r.getCodiceProfessore());

		if(ps.execute())
			return true;
		else
			return false;



	}catch(SQLException e) {
		e.printStackTrace();
		System.out.println("connessione al database non effettuata per la modifica del giorno di ricevimento " + e.getMessage());
		return false;


		}
	}


//-----------------------------CIRO----------------------------


	public List<String[]> cercaGiornoOraProf(String codiceProfessore) {
		List<String[]> giorniOreNote = new ArrayList<>();
		Map<String, DayOfWeek> mappaGiorni = Map.of(
				"lunedì", DayOfWeek.MONDAY,
				"martedì", DayOfWeek.TUESDAY,
				"mercoledì", DayOfWeek.WEDNESDAY,
				"giovedì", DayOfWeek.THURSDAY,
				"venerdì", DayOfWeek.FRIDAY
		);

		try (Connection con = DriverManagerConnectionPool.getConnessione()) {
			String query = "SELECT giorno, ora, note FROM ricevimento WHERE codiceProfessore = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, codiceProfessore);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String giornoStr = rs.getString("giorno").toLowerCase();
				String oraStr = rs.getString("ora");
				String note = rs.getString("note");
				DayOfWeek giornoSettimana = mappaGiorni.get(giornoStr);
				if (giornoSettimana == null) continue;

				String nomeGiorno = giornoSettimana.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
				// Normalizza il formato dell'ora
				LocalTime ora = LocalTime.parse(oraStr, DateTimeFormatter.ofPattern("H:mm"));
				String oraFormattata = ora.format(DateTimeFormatter.ofPattern("HH:mm"));

				giorniOreNote.add(new String[]{nomeGiorno, oraFormattata, note != null ? note : ""});
			}

		} catch (SQLException | DateTimeParseException e) {
			e.printStackTrace();
			System.out.println("Errore durante il recupero o la conversione di giorno, ora o note: " + e.getMessage());
		}

		return giorniOreNote;
	}
}

