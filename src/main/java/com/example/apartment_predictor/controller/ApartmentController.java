package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.service.ApartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.nd4j.shade.protobuf.compiler.PluginProtos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URI;


@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/export")
    public ResponseEntity<String> exportToJson() {
        Iterable<Apartment> apartments = apartmentService.findAll();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // opcional: fecha legible

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("apartments.json"), apartments);

            return ResponseEntity.ok("Archivo apartments.json generado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error generando archivo: " + e.getMessage());
        }
    }



}