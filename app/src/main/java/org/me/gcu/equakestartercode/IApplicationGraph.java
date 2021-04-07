package org.me.gcu.equakestartercode;

import org.me.gcu.equakestartercode.Data.Repository;

import javax.inject.Singleton;

import dagger.Component;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
@Component
@Singleton
public interface IApplicationGraph {
    Repository repository();
}
