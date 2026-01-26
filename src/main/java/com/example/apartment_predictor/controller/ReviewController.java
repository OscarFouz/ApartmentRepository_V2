package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ReviewRepository;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ApartmentService apartmentService;
    private final ReviewRepository reviewRepository;

    public ReviewController(ApartmentService apartmentService,
                            ReviewRepository reviewRepository) {
        this.apartmentService = apartmentService;
        this.reviewRepository = reviewRepository;
    }

    // GET /api/apartments/{id}/reviews
    @GetMapping("/apartments/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsByApartment(@PathVariable String id) {
        Apartment apt = apartmentService.findApartmentById(id);
        if (apt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(apt.getReviews());
    }

    // POST /api/apartments/{id}/reviews
    @PostMapping("/apartments/{id}/reviews")
    public ResponseEntity<Review> addReviewToApartment(
            @PathVariable String id,
            @RequestBody Review review) {

        Apartment apt = apartmentService.findApartmentById(id);
        if (apt == null) {
            return ResponseEntity.notFound().build();
        }

        // Asociar review al apartamento
        review.setApartment(apt);
        Review saved = reviewRepository.save(review);

        return ResponseEntity
                .created(URI.create("/api/reviews/" + saved.getId()))
                .body(saved);
    }

    // DELETE /api/reviews/{reviewId}
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            return ResponseEntity.notFound().build();
        }
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
