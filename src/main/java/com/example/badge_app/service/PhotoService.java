package com.example.badge_app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PhotoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String urlAPI = "http://localhost:8080/api/photos";
	
	  public List<String> getPhotosFromAPI(String reference) {
	        String url = UriComponentsBuilder.fromPath(this.urlAPI+"/{reference}")
	                .buildAndExpand(reference)
	                .toUriString();

	        ResponseEntity<List<String>> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            null,
	            new ParameterizedTypeReference<List<String>>() {}
	        );

	        return response.getBody();
	    }
	  
	  
	  public  void saveBase64ToImage(String base64Data, String directoryPath, String fileName) throws IOException {
	        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
	        // Créer le répertoire s'il n'existe pas déjà
	        File directory = new File(directoryPath);
	        if (!directory.exists()) {
	            directory.mkdirs();  
	        }

	        
	        File imageFile = new File(directoryPath + File.separator + fileName);
	        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
	            fos.write(imageBytes);  
	            fos.flush();
	        }

	        System.out.println("Image sauvegardée avec succès dans : " + imageFile.getAbsolutePath());
	    }
	  


}
