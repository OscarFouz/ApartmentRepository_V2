package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    // GET /api/apartments
    @GetMapping
    public ResponseEntity<Iterable<Apartment>> getAll() {
        return ResponseEntity.ok(apartmentService.findAll());
    }

    // GET /api/apartments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getById(@PathVariable String id) {
        Apartment apt = apartmentService.findApartmentById(id);
        if (apt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(apt);
    }

    // POST /api/apartments
    @PostMapping
    public ResponseEntity<Apartment> create(@RequestBody Apartment apartment) {
        Apartment created = apartmentService.createApartment(apartment);
        return ResponseEntity
                .created(URI.create("/api/apartments/" + created.getId()))
                .body(created);
    }

    // PUT /api/apartments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Apartment> update(
            @PathVariable String id,
            @RequestBody Apartment apartment) {

        Apartment updated = apartmentService.updateApartmentById(id, apartment);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/apartments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Apartment existing = apartmentService.findApartmentById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}