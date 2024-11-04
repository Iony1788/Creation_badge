package com.example.badge_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import com.example.badge_app.entity.User;
import com.example.badge_app.model.Badge;
import com.example.badge_app.model.SignaturePhotoResponse;
import com.example.badge_app.service.PdfService;
import com.example.badge_app.service.PhotoService;
import com.example.badge_app.service.RemotePhotoService;
import com.example.badge_app.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

	
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private PdfService pdfService;
    
    
    @Autowired
    private RemotePhotoService remotePhotoService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private static final String UPLOAD_DIRECTORY = "uploads/";
    

    
    @GetMapping("/")
    public ModelAndView home(Model model) {
        return new ModelAndView("/pages/index");
    }
    


//    @GetMapping("/photos/{reference}")
//    public ResponseEntity<String> getPhotos(@PathVariable String reference) {
//        try {
//        	
//        	
//            remotePhotoService.fetchAndSaveImages(reference);
//            
//            
//            return new ResponseEntity<>("Images successfully fetched and saved.", HttpStatus.OK);
//        } catch (Exception e) {
//            // Log the exception if needed
//            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    
//    }
   
	    @PostMapping("/save")
	    public ModelAndView saveUser(@RequestParam("nom") String nom,
	                           @RequestParam("prenom") String prenom,
	                           @RequestParam(value = "photoCIN", required = false) MultipartFile photoCIN,
	                           @RequestParam("inputReference") String reference,
	                           @RequestParam("numeroMatricule") String numeroMatricule) {
	
	        User user = new User();
	        user.setNom(nom);
	        user.setPrenom(prenom);
	        user.setReference(reference);
	        user.setNumeroMatricule(numeroMatricule);
	        
	        
//	        if (reference != null && !reference.isEmpty()) {
//	            try {
//	                // Appel direct à la logique de récupération des images
//	                remotePhotoService.fetchAndSaveImages(reference);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	              
//	            }
//	        }
	        if (reference != null && !reference.isEmpty()) {
	            try {
	                // Appel direct à la logique de récupération des images
	               SignaturePhotoResponse signaturePhotoResponse= remotePhotoService.getSignaturePhotoFromAPI(reference);
	               System.out.println("name :"+signaturePhotoResponse.getName());
	               // Sauvegarder la première image (utilisateur)
	               remotePhotoService.saveBase64ToImage(signaturePhotoResponse.getPhotoIdentite(), "uploads/"+reference, reference + ".png");
	
	                // Sauvegarder la deuxième image (signature)
	               remotePhotoService.saveBase64ToImage(signaturePhotoResponse.getSignatureImage(), "uploads/"+reference, "signature.png");
	               	user.setWorker_name(signaturePhotoResponse.getName());
	               	user.setDate_signature(signaturePhotoResponse.getDateSignature());
	            } catch (Exception e) {
	            	System.out.println("error :"+e.getMessage());
	                e.printStackTrace();
	               
	              
	            }
	        }
	     
	        if (photoCIN != null && !photoCIN.isEmpty()) {
	            try {
	                String fileName = photoCIN.getOriginalFilename();
	                Path filePath = Paths.get(UPLOAD_DIRECTORY + fileName);
	                Files.copy(photoCIN.getInputStream(), filePath);
	                user.setPathPhotoCIN(filePath.toString());
	            } catch (IOException e) {
	                e.printStackTrace();
	             }
	        }
	
	        
	        userService.saveUser(user);
	        
	        
	        
	        String pathSignature = "uploads/"+reference+"/signature.png";
	        String pathPhoto =  "uploads/"+reference+"/"+reference+".png";
	        
	        Badge badge = new Badge(user.getNom(),user.getPrenom(),user.getNumeroMatricule(),pathSignature,pathPhoto);
	        
	        
	        ModelAndView model = new ModelAndView();
	        
	        model.addObject("badge", badge);
	       model.setViewName("/pages/badge.html");
	       
	       return model;
	        
	        
	    }
    
    
    
    //tsy mety
//    @PostMapping("/save")
//    public ModelAndView saveUser(@RequestParam("nom") String nom,
//                                 @RequestParam("prenom") String prenom,
//                                 @RequestParam(value = "photoCIN", required = false) MultipartFile photoCIN,
//                                 @RequestParam("inputReference") String reference,
//                                 @RequestParam("numeroMatricule") String numeroMatricule) {
//
//        // Création d'un nouvel utilisateur
//        User user = new User();
//        user.setNom(nom);
//        user.setPrenom(prenom);
//        user.setReference(reference);
//        user.setNumeroMatricule(numeroMatricule);
//
//        // Sauvegarde de la photo de la carte d'identité (si présente)
//        if (photoCIN != null && !photoCIN.isEmpty()) {
//            try {
//                String fileName = photoCIN.getOriginalFilename();
//                Path filePath = Paths.get(UPLOAD_DIRECTORY + fileName);
//                Files.copy(photoCIN.getInputStream(), filePath);
//                user.setPathPhotoCIN(filePath.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Récupération des informations de l'utilisateur par référence
//        Map<String, Object> infoUserData = new HashMap<>();
//        try {
//            // Appel de l'API pour obtenir les informations et photos de l'utilisateur
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<Map> response = restTemplate.getForEntity("http://localhost:8080/api/photos/" + reference, Map.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                infoUserData = response.getBody();
//
//                // Récupérer le nom et la date de signature depuis l'API et les assigner à l'utilisateur
//                String nomUser = (String) infoUserData.get("nom");
//                String dateSignature = infoUserData.get("date_signature").toString();
//
//                // Insérer le nom dans la colonne worker_name et la date de signature dans date_signature
//                user.setWorker_name(nomUser);
//                user.setDate_signature(dateSignature);
//            } else {
//                throw new Exception("Erreur lors de la récupération des informations utilisateur");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Sauvegarder l'utilisateur avec les nouvelles informations
//        userService.saveUser(user);
//
//        // Utiliser les informations pour créer le badge
//        String pathSignature = (String) infoUserData.get("signature");
//        String pathPhoto = (String) infoUserData.get("photo");
//
//        Badge badge = new Badge(user.getNom(), prenom, user.getNumeroMatricule(), pathSignature, pathPhoto);
//
//        // Passer les données à la vue
//        ModelAndView model = new ModelAndView();
//        model.addObject("badge", badge);
//        model.addObject("dateSignature", user.getDate_signature());  // Ajout de la date de signature à la vue
//        model.setViewName("/pages/badge.html");
//
//        return model;
//    }


	    
	    @GetMapping("/toPdf")
	    public ResponseEntity<String> convertHtmlToPdf(@RequestParam("htmlContent") String htmlContent,
	    											@RequestParam("matricule") String matricule) {
	        try {
	        	
	        	String pathPdf = "uploads/badge";
	        	
	        	pdfService.generatePdfAndSaveToDirectory(htmlContent, pathPdf,matricule+".pdf");
	            
	            return new ResponseEntity<>("PDf successfully created and saved.", HttpStatus.OK);
	        } catch (Exception e) {
	            // Log the exception if needed
	            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    
	    }
	    
	    

}
    
    


