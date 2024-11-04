package com.example.badge_app.model;

public class Badge {
	private String nom;
	private String prenom;
	private String numeroMatricule;
	private String pathSignature ;
	private String pathPhoto;
	
	public Badge() {}
	
	public Badge(String nom,String prenom,String numeroM,String pathS,String pathP) {
		setNom(nom);
		setPrenom(prenom);
		setNumeroMatricule(numeroM);
		setPathPhoto(pathP);
		setPathSignature(pathS);
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNumeroMatricule() {
		return numeroMatricule;
	}
	public void setNumeroMatricule(String numeroMatricule) {
		this.numeroMatricule = numeroMatricule;
	}
	public String getPathSignature() {
		return pathSignature;
	}
	public void setPathSignature(String pathSignature) {
		this.pathSignature = pathSignature;
	}
	public String getPathPhoto() {
		return pathPhoto;
	}
	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}
	
}
