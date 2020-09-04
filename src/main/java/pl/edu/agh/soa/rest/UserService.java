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
import pl.edu.agh.soa.model.User;

import java.util.List;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.*;

@Path("/users")
@Produces({ APPLICATION_JSON })
@Consumes({ APPLICATION_JSON })
public class UserService {
    private pl.edu.agh.soa.data.dao dao = new dao();

    @GET
    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Integer id){
        return dao.getUser(id);
    }

    @POST
    public User addUser(User user){
        if(dao.addUser(user)) {
            return user;
        }
        else{
            return null;
        }
    }

    @DELETE
    public User deleteUser(User user){
        if(dao.deleteUser(user)){return user;}
        return null;
    }

    @DELETE
    @Path("/{id}")
    public User deleteUser(@PathParam("id") Integer id){
        User deletedUser = dao.getUser(id);
        if(dao.deleteUser(id)){return deletedUser;}
        return null;
    }

    @PUT
    public List<User> updateAllUsers(List<User> newUsers){
        if(dao.updateAllUsers(newUsers)){;
            return newUsers;
        }
        return null;
    }

    @PATCH
    @Path("/{id}")
    public User updateUser(@PathParam("id") Integer id, User newUser){
        User oldUser = dao.getUser(id);

        newUser.setId(id);
        if(newUser.getAge() == 0){newUser.setAge(oldUser.getAge());}
        if(newUser.getName().isEmpty()){newUser.setName(oldUser.getName());}
        if(newUser.getFilmlist() == null && oldUser.getFilmlist() != null){newUser.setFilmlist(oldUser.getFilmlist());}

        if(dao.deleteUser(oldUser)) {
            if(dao.addUser(newUser)) {
                return newUser;
            }
        }
        return null;
    }
}
