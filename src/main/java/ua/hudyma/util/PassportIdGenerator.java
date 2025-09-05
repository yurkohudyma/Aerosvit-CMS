package ua.hudyma.util;

import java.util.Random;

public class PassportIdGenerator {
    public static String generatePassportId(int letterLength, int numberLength) {
        String letters = generateRandomUppercaseLetters(letterLength);
        String numbers = generateRandomDigits(numberLength);
        return letters + numbers;
    }

    private static String generateRandomUppercaseLetters(int length) {
        return new Random()
                .ints('A', 'Z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
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

}
