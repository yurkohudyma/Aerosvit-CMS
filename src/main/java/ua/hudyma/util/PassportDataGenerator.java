package ua.hudyma.util;

import ua.hudyma.domain.profile.PilotType;
import ua.hudyma.domain.visa.VisaStatus;
import ua.hudyma.domain.visa.VisaType;
import ua.hudyma.enums.Country;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

public class PassportDataGenerator {

    public static LocalDate generateIssuedOn() {
        var today = LocalDate.now();
        int daysBack = new SecureRandom().nextInt(365 * 10);
        return today.minusDays(daysBack);
    }
    public static String generatePassportId(int letterLength, int numberLength) {
        String letters = generateRandomUppercaseLetters(letterLength);
        String numbers = generateRandomDigits(numberLength);
        return letters + numbers;
    }

    private static String generateRandomUppercaseLetters(int length) {
        return new Random()
                .ints('A', 'Z' + 1)
                .limit(length)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private static String generateRandomDigits(int length) {
        return new Random()
                .ints('0', '9' + 1)
                .limit(length)
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    public static PilotType getRandomPilotType() {
        var values = PilotType.values();
        var enumSize = values.length;
        var index = new SecureRandom().nextInt(enumSize);
        return values[index];
    }

    public static Country getRandomCountry() {
        var values = Country.values();
        var enumSize = values.length;
        var index = new SecureRandom().nextInt(enumSize);
        return values[index];
    }

    public static VisaStatus getRandomVisaStatus(){
        var values = VisaStatus.values();
        var enumSize = values.length;
        var index = new SecureRandom().nextInt(enumSize);
        return values[index];
    }

    public static VisaType getRandomVisaType(){
        var values = VisaType.values();
        var enumSize = values.length;
        var index = new SecureRandom().nextInt(enumSize);
        return values[index];
    }
}