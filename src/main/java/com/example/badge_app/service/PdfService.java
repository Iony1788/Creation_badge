package com.example.badge_app.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
@Service
public class PdfService {

	
	public void generatePdfAndSaveToDirectory(String htmlContent, String directoryPath, String fileName) {
	    try {
	        File directory = new File(directoryPath);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        String filePath = directoryPath + File.separator + fileName;

	        // Créer un nouveau PdfDocument
	        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new File(filePath)));

	        // Définir la taille de page en mode paysage
	        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());

	        // Convertir HTML à PDF
	        HtmlConverter.convertToPdf(htmlContent, pdfDocument, new ConverterProperties());

	        pdfDocument.close();

	        System.out.println("PDF généré et enregistré dans : " + filePath);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
