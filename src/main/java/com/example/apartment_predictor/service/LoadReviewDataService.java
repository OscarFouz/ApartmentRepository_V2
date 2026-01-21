package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class LoadReviewDataService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    private final Random random = new Random();

    public void importReviewsFromCSV(String csvPath) throws IOException {

        // Si ya hay reviews, no cargar nada
        if (reviewRepository.count() > 0) {
            System.out.println("BD ya tiene reviews. No se carga CSV.");
            return;
        }

        Path path = Path.of(csvPath);

        List<Apartment> apartments = (List<Apartment>) apartmentRepository.findAll();

        if (apartments.isEmpty()) {
            System.out.println("No hay apartamentos en la BD. No se pueden asignar reviews.");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {

            String line = br.readLine(); // saltar cabecera

            while ((line = br.readLine()) != null) {

                String[] v = line.split(",");

                String title = v[0];
                String content = v[1];
                int rating = Integer.parseInt(v[2]);
                LocalDate date = LocalDate.parse(v[3]);

                Review review = new Review(title, content, rating, date);

                // Asignar review a un apartamento aleatorio
                Apartment randomApartment = apartments.get(random.nextInt(apartments.size()));
                review.setApartment(randomApartment);

                reviewRepository.save(review);
            }
        }

        System.out.println("Reviews importadas correctamente.");
    }
}