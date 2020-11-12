package com.finnegans;

public abstract class Usuario {
	private String nombreUsuario;
    private String claveUsuario;
    private int tiempo;
    private int id;
	
    public Usuario(String nombreUsuario, String claveUsuario,int tiempo,int id) {
		this.nombreUsuario = nombreUsuario;
		this.claveUsuario = claveUsuario;
		this.tiempo = tiempo;
		this.id=id;
	}
	
    
	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getClaveUsuario() {
		return claveUsuario;
	}


	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}


	public int getTiempo() {
		return tiempo;
	}


	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public abstract void mostrarMenu();

}
