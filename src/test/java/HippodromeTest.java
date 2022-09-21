import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HippodromeTest {

    @Test
    void constructor_WhenNull() {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", expected.getMessage());


    }

    @Test
    void constructor_WhenEmptyList() {
        Throwable expected = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", expected.getMessage());
    }

    @Test
    void getHorses() {

//        Horse firstHorse = new Horse("First", 5, 10);
//        Horse secondHorse = new Horse("Second", 3, 7);
//        Horse thirdHorse = new Horse("Third", 8, 16);
//        List<Horse> horses = List.of(firstHorse, secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse,
//                secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse,
//                firstHorse, firstHorse, secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse,
//                secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse, firstHorse, secondHorse, thirdHorse,
//                firstHorse);

        List<Horse> horses = new ArrayList<>();
        // список из 30 разных лошадей
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse number:" + i, i + 1, i + 2));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        // упадет т.к. метод getHorse модифицирует коллекцию и возвращает другой объект
        // assertSame(horses, hippodrome.getHorses());
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
//      Horse mockHorse = Mockito.mock(Horse.class); - создаем 1 мок лошади и 50 раз добавляем в список (неправильно)

        // Patter AAA - Arrange - Act - Assert
        // arrange
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        // act
        hippodrome.move();

        // assert
        for (Horse mockHorse : horses) {
            Mockito.verify(mockHorse).move();
        }
        // неверный вариант т.к. метод move может быть вызван 50 раз у 1 лошади, а не по 1 разу у каждой из 50
//        Mockito.verify(mockHorse, Mockito.times(50)).move();
    }

    @Test
    void getWinner() {
        Horse firstHorse = new Horse("First", 5, 10);
        Horse secondHorse = new Horse("Second", 3, 7);
        Horse thirdHorse = new Horse("Third", 8, 16);
        Horse fourthHorse = new Horse("Fourth", 8, 9);
        List<Horse> horses = List.of(firstHorse, secondHorse, thirdHorse, secondHorse, fourthHorse);
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(16, hippodrome.getWinner().getDistance());

        // проверяем, что метод getWinner вернет коня с самой большой дистанцией
        assertSame(thirdHorse, hippodrome.getWinner());
    }
}
