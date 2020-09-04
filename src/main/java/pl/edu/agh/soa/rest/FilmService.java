/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package pl.edu.agh.soa.rest;

import pl.edu.agh.soa.data.dao;
import pl.edu.agh.soa.model.Film;

import javax.ws.rs.*;
import java.util.LinkedList;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;

@Path("/films")
@Produces({ APPLICATION_JSON })
@Consumes({ APPLICATION_JSON })
public class FilmService {
    private pl.edu.agh.soa.data.dao dao = new dao();

    @GET
    public List<Film> getAllFilms() {
        return dao.getAllFilms();
    }

    @GET
    @Path("/{id}")
    public Film getFilmById(@PathParam("id") Integer id) {
        return dao.getFilm(id);
    }

    @GET
    @Path("/title/{title}")
    public Film getFilmByTitle(@PathParam("title") String title) {
        return dao.getFilm(title);
    }

    @GET
    @Path("/urilist")
//    @Produces({TEXT_URI-LIST})
    public List<String> getAllFilmUri(){
        List<String> uriList = new LinkedList<>();
        List<Film> allFilms = dao.getAllFilms();
        for(Film film: allFilms){
            uriList.add(film.getUri());
        }
        return uriList;
    }

    @POST
    public Film addFilm(Film film){
        if(dao.addFilm(film)) {
            return film;
        }
        else{
            return null;
        }
    }

    @DELETE
    public Film deleteFilm(Film film){
        if(dao.deleteFilm(film)){return film;}
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Film deleteFilm(@PathParam("id") Integer id){
        Film deletedFilm = dao.getFilm(id);
        if(dao.deleteUser(id)){return deletedFilm;}
        return null;
    }

    @PUT
    public List<Film> updateAllFilms(List<Film> newFilms){
        if(dao.updateAllFilms(newFilms)){;
            return newFilms;
        }
        return null;
    }

    @PATCH
    @Path("/{id}")
    public Film updateFilm(@PathParam("id") Integer id, Film newFilm){
        Film oldFilm = dao.getFilm(id);

        if(newFilm.getTitle().isEmpty()){newFilm.setTitle(oldFilm.getTitle());}
        if(newFilm.getUri().isEmpty()){newFilm.setUri(oldFilm.getUri());}
        newFilm.setId(oldFilm.getId());

        if(dao.deleteFilm(oldFilm)) {
            if(dao.addFilm(newFilm)) {
                return newFilm;
            }
        }
        return null;
    }


}
