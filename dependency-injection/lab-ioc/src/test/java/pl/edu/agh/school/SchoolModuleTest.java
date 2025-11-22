package pl.edu.agh.school;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.guice.SchoolModule;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SchoolModuleTest {

    @Test
    public void loggerShouldBeSingleton() {
        Injector injector = Guice.createInjector(new SchoolModule());

        Logger l1 = injector.getInstance(Logger.class);
        Logger l2 = injector.getInstance(Logger.class);

        assertSame(l1, l2);
    }
}

