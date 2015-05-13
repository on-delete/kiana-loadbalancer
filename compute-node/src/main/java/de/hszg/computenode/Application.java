package de.hszg.computenode;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 13.05.15.
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        return classes;
    }
}
