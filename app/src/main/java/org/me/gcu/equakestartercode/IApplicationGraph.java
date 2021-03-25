package org.me.gcu.equakestartercode;

import org.me.gcu.equakestartercode.Data.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Component
@Singleton
public interface IApplicationGraph {
    Repository repository();
}
