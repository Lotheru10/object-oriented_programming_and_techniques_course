package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {


    @Provides
    PersistenceManager providePersistanceManager(SerializablePersistenceManager manager){
        return manager;
    }

    @Override
    protected void configure(){
        bind(String.class).annotatedWith(Names.named("teachersStorage")).toInstance("teachers-guice.dat");
        bind(String.class).annotatedWith(Names.named("classesStorage")).toInstance("classes-guice.dat");
        bind(Logger.class).in(Singleton.class);

    }
}
