package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.profile.Address;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.CrewType;
import ua.hudyma.domain.profile.Profile;
import ua.hudyma.enums.Country;
import ua.hudyma.repository.CrewRepository;

import java.util.Locale;
import java.util.stream.Stream;

import static ua.hudyma.util.IdGenerator.getRandomEnum;

@Service
@RequiredArgsConstructor
@Log4j2
public class CrewService {
    private final CrewRepository crewRepository;

    public void generateCrew(Integer number) {
        var list = Stream
                .generate(CrewService::create)
                .limit(number)
                .toList();
        crewRepository.saveAll(list);
    }

    private static Crew create (){
        var fairy = Fairy.create(Locale.forLanguageTag("uk"));
        var genPerson = fairy.person();
        return populateCrewFields (genPerson);
    }

    private static Crew populateCrewFields(Person genPerson) {
        var crew = new Crew();
        crew.setCrewType(getRandomEnum(CrewType.class));
        var profile = new Profile();
        profile.setBirthday(genPerson.getDateOfBirth());
        profile.setName(genPerson.getFirstName());
        profile.setSurname(genPerson.getLastName());
        profile.setEmail(genPerson.getEmail());
        profile.setPhoneNumber(genPerson.getTelephoneNumber());
        crew.setProfile(profile);

        var address = new Address();
        var genAddress = genPerson.getAddress();
        address.setCountry(getRandomEnum(Country.class));
        address.setPostalIndex(genAddress.getPostalCode());
        address.setCity(genAddress.getCity());
        address.setBuilding(genAddress.getStreetNumber());
        address.setApartment(genAddress.getApartmentNumber());
        address.setStreet(genAddress.getStreet());
        crew.setAddress(address);
        return crew;
    }
}
