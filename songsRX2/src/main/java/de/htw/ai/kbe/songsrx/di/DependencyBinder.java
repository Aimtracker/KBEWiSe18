package de.htw.ai.kbe.songsrx.di;

import de.htw.ai.kbe.songsrx.authorization.AuthorizationService;
import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;
import de.htw.ai.kbe.songsrx.persistence.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(Persistence.createEntityManagerFactory("songDB")).to(EntityManagerFactory.class);
        bind(DBSongDAO.class).to(ISong.class).in(Singleton.class);
        bind(DBUserDAO.class).to(IUser.class).in(Singleton.class);
        bind(AuthorizationService.class).to(IAuthorizationService.class).in(Singleton.class);
    }
}
