package pl.edu.agh.soa.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/osoby")
public class RedirectService {

    // REDIRECTION
    @GET
    public Response redirect() {
        URI uri = null;
        try {
            uri = new URI("http://127.0.0.1:8080/rest-1.0-SNAPSHOT/api/users");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.MOVED_PERMANENTLY).location(uri).build();

    }
}
