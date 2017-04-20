package net.rorarius.converter;

public interface IdToStringConverter {

    String encodeId(long id);

    long decodeId(String idString);
}
