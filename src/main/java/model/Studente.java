package model;

public class Studente {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String matricola;
	private String domanda;
	private String risposta;
	
	public Studente() {
		
	}

	public Studente(String nome, String cognome, String email, String password, String matricola, String domanda, String risposta) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.matricola = matricola;
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

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
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
