package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.certify.AircraftType;
import ua.hudyma.domain.certify.Certificate;
import ua.hudyma.domain.profile.Address;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.profile.PilotType;
import ua.hudyma.domain.profile.Profile;
import ua.hudyma.enums.Country;
import ua.hudyma.repository.CertificateRepository;
import ua.hudyma.repository.PilotRepository;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static ua.hudyma.domain.profile.PilotType.*;
import static ua.hudyma.util.IdGenerator.getRandomEnum;

@Service
@RequiredArgsConstructor
@Log4j2
public class PilotService {
    private final PilotRepository pilotRepository;
    private final CertDataService certDataService;

    public List<Pilot> findCaptain(AircraftType aircraftType) {
        return Stream.of(CPT, TRI, TRE, LTC, DE)
                .map(pilotRepository::findByPilotType)
                .map(list -> list.stream()
                        .filter(hasJetCompliance(aircraftType))
                        .toList())
                .filter(filteredList -> !filteredList.isEmpty())
                .findFirst()
                .orElse(Collections.emptyList());
    }

    public Predicate<Pilot> hasJetCompliance(AircraftType aircraftType) {
        return pilot ->
                pilot.getCertificateData()
                        .getCertificateList()
                        .stream()
                        .anyMatch(cert -> cert.getAircraftType()
                                == aircraftType);
    }

    public void generatePilots(int number) {
        var list = Stream
                .generate(PilotService::create)
                .limit(number)
                .toList();
        pilotRepository.saveAll(list);
    }

    private static Pilot create() {
        var fairy = Fairy.create(Locale.forLanguageTag("uk"));
        var generatedPerson = fairy.person();
        return populatePilotFields(generatedPerson);
    }

    private static Pilot populatePilotFields(Person genPerson) {
        var pilot = new Pilot();

        pilot.setPilotType(getRandomEnum(PilotType.class));

        var profile = new Profile();
        profile.setBirthday(genPerson.getDateOfBirth());
        profile.setName(genPerson.getFirstName());
        profile.setSurname(genPerson.getLastName());
        profile.setEmail(genPerson.getEmail());
        profile.setPhoneNumber(genPerson.getTelephoneNumber());
        pilot.setProfile(profile);

        var address = new Address();
        var genAddress = genPerson.getAddress();
        address.setCountry(getRandomEnum(Country.class));
        address.setPostalIndex(genAddress.getPostalCode());
        address.setCity(genAddress.getCity());
        address.setBuilding(genAddress.getStreetNumber());
        address.setApartment(genAddress.getApartmentNumber());
        address.setStreet(genAddress.getStreet());
        pilot.setAddress(address);
        return pilot;
    }
}
