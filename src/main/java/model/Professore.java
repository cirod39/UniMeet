package model;

public class Professore {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String codiceProfessore;
	private String ufficio;
	private String domanda;
	private String risposta;
	
	public Professore() {
		
	}

	public Professore(String nome, String cognome, String email, String password, String codiceProfessore, String ufficio, String domanda, String risposta) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.codiceProfessore = codiceProfessore;
		this.ufficio = ufficio;
		this.domanda = domanda;
		this.risposta = risposta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodiceProfessore() {
		return codiceProfessore;
	}

	public void setCodiceProfessore(String codiceProfessore) {
		this.codiceProfessore = codiceProfessore;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getDomanda() {
		return domanda;
	}

	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}
