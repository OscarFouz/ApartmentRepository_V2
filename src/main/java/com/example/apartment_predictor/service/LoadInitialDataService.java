package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@Service
public class LoadInitialDataService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    private final Random random = new Random();

    public void importApartmentsFromCSV(String csvPath) throws IOException {

        // Si ya hay datos, no cargar nada
        if (apartmentRepository.count() > 0) {
            System.out.println("BD ya tiene datos. No se carga CSV.");
            return;
        }

        Path path = Path.of(csvPath);

        try (BufferedReader br = Files.newBufferedReader(path)) {

            String line = br.readLine(); // saltar cabecera

            while ((line = br.readLine()) != null) {
                String[] v = line.split(",");

                // Elegir tipo de propiedad aleatoriamente
                Apartment apt = createRandomPropertyType();

                // Campos comunes
                apt.setPrice(Long.parseLong(v[0]));
                apt.setArea(Integer.parseInt(v[1]));
                apt.setBedrooms(Integer.parseInt(v[2]));
                apt.setBathrooms(Integer.parseInt(v[3]));
                apt.setStories(Integer.parseInt(v[4]));
                apt.setMainroad(v[5]);
                apt.setGuestroom(v[6]);
                apt.setBasement(v[7]);
                apt.setHotwaterheating(v[8]);
                apt.setAirconditioning(v[9]);
                apt.setParking(Integer.parseInt(v[10]));
                apt.setPrefarea(v[11]);
                apt.setFurnishingstatus(v[12]);

                // Campo heredado de Property
                apt.setLocationRating(random.nextInt(5) + 1); // 1–5

                // Rellenar campos específicos según tipo
                fillSubtypeRandomFields(apt);

                apartmentRepository.save(apt);
            }
        }

        System.out.println("CSV importado correctamente.");
    }

    private Apartment createRandomPropertyType() {
        int type = random.nextInt(4);

        return switch (type) {
            case 0 -> new Apartment();
            case 1 -> new House();
            case 2 -> new Duplex();
            case 3 -> new TownHouse();
            default -> new Apartment();
        };
    }

    private void fillSubtypeRandomFields(Apartment apt) {

        if (apt instanceof House house) {
            house.setGarageQty(random.nextInt(3)); // 0–2
            house.setRoofType(randomRoof());
            house.setGarden(randomYesNo());
        }

        if (apt instanceof Duplex duplex) {
            duplex.setBalcony(randomYesNo());
            duplex.setElevator(random.nextBoolean());
            duplex.setHasSeparateUtilities(random.nextBoolean());
        }

        if (apt instanceof TownHouse th) {
            th.setHasHomeownersAssociation(random.nextBoolean());
            th.setHoaMonthlyFee(random.nextInt(200) + 50); // 50–250€
        }
    }

    private String randomRoof() {
        String[] roofs = {"tile", "flat", "wood", "metal"};
        return roofs[random.nextInt(roofs.length)];
    }

    private String randomYesNo() {
        return random.nextBoolean() ? "yes" : "no";
    }
}