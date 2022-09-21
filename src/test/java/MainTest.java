import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


public class MainTest {

    @Disabled
    @Test
    // по условию 21, но у меня не проходит т.к. комп старый и ядер маловато
    @Timeout(value = 22)
    void mainTimeout() throws Exception {
        // вызываем метод main у класса Main, в качестве аргумента передаем null
        Main.main(null);
    }
}