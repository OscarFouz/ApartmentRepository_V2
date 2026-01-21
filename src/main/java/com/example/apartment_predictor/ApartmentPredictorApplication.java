package com.example.apartment_predictor;

import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import com.example.apartment_predictor.service.LoadInitialDataService;
import com.example.apartment_predictor.service.LoadReviewDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApartmentPredictorApplication implements CommandLineRunner {

    @Value("${app.csv.path}")
    private String csvPath;

    @Value("${app.reviews.csv.path}")
    private String reviewsCsvPath;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LoadInitialDataService loadInitialDataService;

    @Autowired
    private LoadReviewDataService loadReviewDataService;

    public static void main(String[] args) {
        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (apartmentRepository.count() == 0) {
            System.out.println("Base de datos vacía. Insertando apartamentos iniciales...");
            loadInitialDataService.importApartmentsFromCSV(csvPath);
            System.out.println("Importación de apartamentos completada.");
        } else {
            System.out.println("La base de datos ya tiene apartamentos. No se insertan.");
        }

        if (reviewRepository.count() == 0) {
            System.out.println("Base de datos vacía. Insertando reviews iniciales...");
            loadReviewDataService.importReviewsFromCSV(reviewsCsvPath);
            System.out.println("Importación de reviews completada.");
        } else {
            System.out.println("La base de datos ya tiene reviews. No se insertan.");
        }

        System.out.println("Aplicación iniciada. Servidor activo.");
    }
}
