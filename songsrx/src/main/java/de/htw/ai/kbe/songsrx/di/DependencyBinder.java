package de.htw.ai.kbe.songsrx.di;

import de.htw.ai.kbe.songsrx.authorization.AuthorizationService;
import de.htw.ai.kbe.songsrx.authorization.IAuthorizationService;
import de.htw.ai.kbe.songsrx.persistence.ISongList;
import de.htw.ai.kbe.songsrx.persistence.IUserList;
import de.htw.ai.kbe.songsrx.persistence.InMemorySongList;
import de.htw.ai.kbe.songsrx.persistence.InMemoryUserList;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(InMemorySongList.class).to(ISongList.class).in(Singleton.class);
        bind(InMemoryUserList.class).to(IUserList.class).in(Singleton.class);
        bind(AuthorizationService.class).to(IAuthorizationService.class).in(Singleton.class);
    }
}
