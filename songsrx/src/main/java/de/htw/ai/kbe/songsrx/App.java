package de.htw.ai.kbe.songsrx;

import de.htw.ai.kbe.songsrx.di.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {
    public App(){
        register(new DependencyBinder());
        packages("de.htw.ai.kbe.songsrx.service");
    }
}
