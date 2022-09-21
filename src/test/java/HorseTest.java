import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Test
    void constructor_WhenNameNull() {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 11));
        assertEquals("Name cannot be null.", expected.getMessage());
    }

    // или можно сделать вот так:
    @Test
    void nullNameMessage() {
        try {
            new Horse(null, 11, 18);
            fail(); // если ошибка не вылетает - тест упадет здесь
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       ", "\n\n", "\t\t\t\t"})
    void constructor_WhenNameEmpty(String empty) {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Horse(empty, 11));
        assertEquals("Name cannot be blank.", expected.getMessage());
    }

    @Test
    void constructor_WhenSpeedNegative() {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", -11));
        assertEquals("Speed cannot be negative.", expected.getMessage());
    }

    @Test
    void constructor_WhenDistanceNegative() {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Name", 11, -18));
        assertEquals("Distance cannot be negative.", expected.getMessage());
    }


    @Test
    void getNameWithGetter() {
        String expected = "Speed Monster";
        String actual = new Horse("Speed Monster", 11).getName();
        assertEquals(expected, actual);
    }

    // правильнее делать через Reflection т.к. метод getName может работать неправильно
    @Test
    void getNameWithReflection() throws NoSuchFieldException, IllegalAccessException {
        String expected = "Flame";
        Horse horse = new Horse(expected, 11, 18);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String actual = (String) name.get(horse);
        assertEquals(expected, actual);
    }

    @Test
    void getSpeed() {
        double expected = 11;
        double actual = new Horse("name", 11).getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    void getDistance_WithParameter() {
        double expected = 18;
        double actual = new Horse("Speed Monster", 11, 18).getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void getDistance_WithDefault() {
        double expected = 0;
        double actual = new Horse("Speed Monster", 18).getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void callGetRandom_WhenMove() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Flame", 3, 10).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 1.0, 2.0, 15.0, 999.0, 0.0})
    void correctDistanceCalculation(double random) {
        // Pattern AAA
        // Arrange
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 2, 10);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            double expected = 10 + 2 * random;
            // Act
            horse.move();
            // Assertion
            assertEquals(expected, horse.getDistance());
        }
    }
}