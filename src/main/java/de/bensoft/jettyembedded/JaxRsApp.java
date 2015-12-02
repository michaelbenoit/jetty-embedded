package de.bensoft.jettyembedded;

import com.google.common.collect.ImmutableSet;
import de.bensoft.jettyembedded.employee.EmployeesResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Created by michaelbenoit on 02.12.15.
 */
@ApplicationPath("/")
public class JaxRsApp extends Application {

    private static final ImmutableSet services = ImmutableSet.of(
            EmployeesResource.class
    );


    @Override
    public Set<Class<?>> getClasses() {
        return services;
    }
}
