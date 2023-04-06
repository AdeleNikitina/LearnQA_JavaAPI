import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TryParameterizedTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "Короткий  тест", "Это длинный текст", "А это очень длинный текст"})
    public  void ex10Test(String inputValue) {
        assertTrue(inputValue.length() >= 15, "Значение inputValue больше или равно 15: " + inputValue);
    }
}
