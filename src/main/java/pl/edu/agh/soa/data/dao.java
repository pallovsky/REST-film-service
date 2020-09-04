package pl.edu.agh.soa.data;

import com.google.gson.Gson;
import pl.edu.agh.soa.model.Film;
import pl.edu.agh.soa.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class dao {
    private EntityManager entityManager;

    public dao() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("RestDS");
        this.entityManager = factory.createEntityManager();
        System.out.println("Stworzono EM \n");
    }

    // CRUD OPERATIONS
    // GET all films or users:
    public List<Film> getAllFilms() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
        Root<Film> bl = query.from(Film.class);
        query.select(bl);
        return this.entityManager.createQuery(query).getResultList();
    }

    public List<User> getAllUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> bl = query.from(User.class);
        query.select(bl);
        return this.entityManager.createQuery(query).getResultList();
    }

    // GET distinctive film or user:
    public User getUser(int id){
        List<User> userList = getAllUsers();
        for(User user: userList){
            if (user.getId() == id){return user;}
        }
        return null;
    }

    public Film getFilm(int id){
        List<Film> userList = getAllFilms();
        for(Film film: userList){
            if (film.getId() == id){return film;}
        }
        return null;
    }

    public Film getFilm(String title){
        for(Film film: getAllFilms()){
            if(film.getTitle().replace(" ", "").equals(title)){
                return film;
            }
        }
        return null;
    }

    // POST add users or films
    public boolean addUser(User user){
        try {
            this.executeInsideTransaction(entityManager -> entityManager.persist(user));
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFilm(Film film){
        try {
            this.executeInsideTransaction(entityManager -> entityManager.persist(film));
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // DELETE users or films
    public boolean deleteUser(User user){
        try{
            executeInsideTransaction(entityManager -> entityManager.remove(user));
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFilm(Film film){
        try{
            executeInsideTransaction(entityManager -> entityManager.remove(film));
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // DELETE user or film with specified id
    public boolean deleteUser(int id){
        List<User> userList = getAllUsers();
        User userToDelete = new User();
        for(User user: userList){
            if(user.getId() == id){userToDelete = user;}
        }
        return deleteUser(userToDelete);
    }

    public boolean deleteFilm(int id){
        List<Film> filmList = getAllFilms();
        Film filmToDelete = new Film();
        for(Film film: filmList){
            if(film.getId() == id){filmToDelete = film;}
        }
        return deleteFilm(filmToDelete);
    }

    // PUT update users or films
    public boolean updateAllUsers(List<User> newUsers){
        try{
            List<User> allUsers = getAllUsers();
            for(User user: allUsers){
                deleteUser(user);
            }
            for(User user: newUsers){
                addUser(user);
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAllFilms(List<Film> newFilms){
        try{
            List<Film> allFilms = getAllFilms();
            for(Film film: allFilms){
                deleteFilm(film);
            }
            for(Film film: newFilms){
                addFilm(film);
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // PUT update user or film


    // Helper function
    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    // Getters and setters
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
