package com.example.badge_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.badge_app.model.SignaturePhotoResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class RemotePhotoService {

    @Autowired
    private RestTemplate restTemplate;

    private String urlAPI = "http://localhost:8080/api/photos";

    public SignaturePhotoResponse getSignaturePhotoFromAPI(String reference) {
        String url = urlAPI + "/" + reference;
  
        ResponseEntity<SignaturePhotoResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SignaturePhotoResponse>() {}
        );
        return response.getBody();
   
    }

    public void saveBase64ToImage(String base64Data, String directoryPath, String fileName) throws IOException {
        // Décoder l'image à partir du Base64
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

//    public void fetchAndSaveImages(String reference) {
//    	SignaturePhotoResponse signaturePhotoResponse = getPhotosFromAPI(reference);
//        
//        if (photosBase64 != null && !photosBase64.isEmpty()) {
//            try {
//                // Sauvegarder la première image (utilisateur)
//                saveBase64ToImage(photosBase64.get(0), "uploads/"+reference, reference + ".png");
//
//                // Sauvegarder la deuxième image (signature)
//                saveBase64ToImage(photosBase64.get(1), "uploads/"+reference, "signature.png");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Aucune image récupérée pour la référence : " + reference);
//        }
//    }
}
