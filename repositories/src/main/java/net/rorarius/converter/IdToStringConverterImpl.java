package net.rorarius.converter;

import org.springframework.stereotype.Component;

@Component
public class IdToStringConverterImpl implements IdToStringConverter {

    private static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int ALPHABET_LENGTH = ALPHABET.length();
    private static final char[] ALPHABET_CHARSET = ALPHABET.toCharArray();

    @Override
    public String encodeId(long id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Negative numbers not supported!");
        }

        long i = id;

        if (i == 0) {
            return Character.toString(ALPHABET_CHARSET[0]);
        }

        StringBuilder stringBuilder = new StringBuilder();

        while (i > 0) {
            int remainder = (int)(i % ALPHABET_LENGTH);
            i /= ALPHABET_LENGTH;
            stringBuilder.append(ALPHABET_CHARSET[remainder]);
        }

        return stringBuilder.reverse().toString();
    }

    @Override
    public long decodeId(String idString) throws IllegalArgumentException {
        if (idString == null) {
            throw new IllegalArgumentException("idString may not be null!");
        }

        long i = 0;

        char[] chars = idString.toCharArray();

        for(char c : chars) {
            i = i * ALPHABET_LENGTH + ALPHABET.indexOf(c);
            if (i < 0) {
                throw new IndexOutOfBoundsException("ID is out of bounds!");
            }
        }

        return i;
    }
}
