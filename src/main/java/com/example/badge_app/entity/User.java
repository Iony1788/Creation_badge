package com.example.badge_app.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {



		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id; // Changez Long en Integer


	    @Column(name = "nom") // Champ obligatoire
	    private String nom;

	    @Column(name = "prenom") // Champ obligatoire
	    private String prenom;

	    @Column(name = "path_photo_CIN") // Chemin de la photo de CIN
	    private String pathPhotoCIN;

	    @Column(name = "worker_name") // Nouvelle colonne pour la référence
	    private String worker_name;
	    
	    @Column(name = "date_signature") // Nouvelle colonne pour la référence
	    private String date_signature;
	    
	    @Column(name = "reference") // Nouvelle colonne pour la référence
	    private String reference;

	    @Column(name = "numero_matricule", unique = true) // Numéro matricule, unique pour chaque utilisateur
	    private String numeroMatricule;
	    
	    


	    // Getters et Setters

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }
	    
	    
	 // Getter pour worker_name
	    public String getWorker_name() {
	        return worker_name;
	    }

	    // Setter pour worker_name
	    public void setWorker_name(String worker_name) {
	        this.worker_name = worker_name;
	    }

	    // Getter pour date_signature
	    public String getDate_signature() {
	        return date_signature;
	    }

	    // Setter pour date_signature
	    public void setDate_signature(String date_signature) {
	        this.date_signature = date_signature;
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

	    public String getPathPhotoCIN() {
	        return pathPhotoCIN;
	    }

	    public void setPathPhotoCIN(String pathPhotoCIN) {
	        this.pathPhotoCIN = pathPhotoCIN;
	    }

	    public String getReference() {
	        return reference;
	    }

	    public void setReference(String reference) {
	        this.reference = reference;
	    }

	    public String getNumeroMatricule() {
	        return numeroMatricule;
	    }

	    public void setNumeroMatricule(String numeroMatricule) {
	        this.numeroMatricule = numeroMatricule;
	    }
}
