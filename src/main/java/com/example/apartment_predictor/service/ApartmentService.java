package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.apartment_predictor.model.Review;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    public Iterable<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public Apartment createApartment(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment (Apartment apartment){

        return apartmentRepository.save(apartment);
    }


    public Apartment updateApartmentById (String id, Apartment apartment){
        if (id == null || apartment == null) {
            return null;
        }

        Optional<Apartment> existingOpt = apartmentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Apartment existing = existingOpt.get();
        existing.setPrice(apartment.getPrice());
        existing.setArea(apartment.getArea());
        existing.setBedrooms(apartment.getBedrooms());
        existing.setBathrooms(apartment.getBathrooms());
        existing.setStories(apartment.getStories());
        existing.setMainroad(apartment.getMainroad());
        existing.setGuestroom(apartment.getGuestroom());
        existing.setBasement(apartment.getBasement());
        existing.setHotwaterheating(apartment.getHotwaterheating());
        existing.setAirconditioning(apartment.getAirconditioning());
        existing.setParking(apartment.getParking());
        existing.setPrefarea(apartment.getPrefarea());
        existing.setFurnishingstatus(apartment.getFurnishingstatus());

        if (apartment.getReviews() != null) {
            existing.setReviews(apartment.getReviews());
            for (Review review : existing.getReviews()) {
                if (review != null) {
                    review.setApartment(existing);
                }
            }
        }

        return apartmentRepository.save(existing);
    }
    public void deleteApartment (String id){
        apartmentRepository.deleteById(id);
    }

    public Apartment findApartmentById (String id){
        Optional<Apartment> found = apartmentRepository.findById(id);
        if ( found.isPresent()) return found.get() ;
        else return null;
    }

    public void findApartmentByPrice (){}
}