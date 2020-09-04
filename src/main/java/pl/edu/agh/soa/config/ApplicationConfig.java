package pl.edu.agh.soa.config;

import pl.edu.agh.soa.rest.FilmService;
import pl.edu.agh.soa.rest.RedirectService;
import pl.edu.agh.soa.rest.UserService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(UserService.class, FilmService.class, RedirectService.class));
    }
}