package net.rorarius.converter;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class IdToStringConverterTest {

    @Test
    public void testEncodeZero() {
        assertThat(encode(0), equalTo("a"));
    }

    @Test
    public void testDecodeZero() {
        assertThat(decode("a"), equalTo(0L));
    }

    @Test
    public void testEncodeNegativeNumber() {
        try {
            encode(Integer.MIN_VALUE);
            assertThat("encode", equalTo("negative"));
        } catch (IllegalArgumentException iax) {
            // should throw Exception
            assertThat(iax, notNullValue());
        }
    }

    @Test
    public void testEncodeIntMaxValue() {
        assertThat(encode(Integer.MAX_VALUE), equalTo("cvuMLb"));
    }

    @Test
    public void testEncodeLongMaxValue() {
        assertThat(encode(Long.MAX_VALUE), equalTo("k9viXaIfiWh"));
    }

    @Test
    public void testDecodeOutOfBounds() {
        try {
            decode("k9viXaIfiWhX");
            assertThat("decode", equalTo("outOfBounds"));
        } catch (IndexOutOfBoundsException obx) {
            // Should throw OutOfBoundsException
            assertThat(obx, notNullValue());
        }
    }

    private String encode(long value) throws IllegalArgumentException {
        IdToStringConverter conv = new IdToStringConverterImpl();
        return conv.encodeId(value);
    }

    private long decode(String id) {
        IdToStringConverter conv = new IdToStringConverterImpl();
        return conv.decodeId(id);
    }
}
