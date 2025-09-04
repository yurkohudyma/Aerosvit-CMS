package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.profile.Address;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.profile.PilotType;
import ua.hudyma.domain.profile.Profile;
import ua.hudyma.repository.PilotRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class PilotService {
    private final PilotRepository pilotRepository;

    public List<Pilot> generatePilots(int number) {
        var list = Stream
                .generate(PilotService::create)
                .limit(number)
                .toList();
        return pilotRepository.saveAll(list);
    }

    private static Pilot create() {
        Fairy fairy = Fairy.create(Locale.forLanguageTag("uk"));
        var generatedPerson = fairy.person();
        return populatedPilotFields (generatedPerson);
    }

    private static Pilot populatedPilotFields(Person genPerson) {
        var pilot = new Pilot();

        pilot.setPilotType(getRandomPilotType());

        var profile = new Profile();
        profile.setBirthday(genPerson.getDateOfBirth());
        profile.setName(genPerson.getFirstName());
        profile.setSurname(genPerson.getLastName());
        profile.setEmail(genPerson.getEmail());
        profile.setPhoneNumber(genPerson.getTelephoneNumber());
        pilot.setProfile(profile);

        var address = new Address();
        var genAddress = genPerson.getAddress();
        address.setCountry("Ukraine");
        address.setPostalIndex(genAddress.getPostalCode());
        address.setCity(genAddress.getCity());
        address.setBuilding(genAddress.getStreetNumber());
        address.setApartment(genAddress.getApartmentNumber());
        address.setStreet(genAddress.getStreet());
        pilot.setAddress(address);
        return pilot;
    }

    private static PilotType getRandomPilotType() {
        var values = PilotType.values();
        var enumSize = values.length;
        var index = new SecureRandom().nextInt(enumSize);
        return values[index];
    }
}
