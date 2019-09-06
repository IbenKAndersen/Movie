package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long count = (long) em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return count;
        } finally {
            em.close();
        }
    }

    public long getNumberOfMoviesInDB() {
        EntityManager em = emf.createEntityManager();
        try {
            long countMovies = (long) em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return countMovies;
        } finally {
            em.close();
        }

    }

    public Movie makeMovie(Movie movie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public List<MovieDTO> getAllMoviesDTO() {
        EntityManager em = getEntityManager();
        try {
            List<Movie> movies = em.createNamedQuery("Movie.findAll").getResultList();
            List<MovieDTO> result = new ArrayList<>();
            for (Movie movie : movies) {
                result.add(new MovieDTO(movie));
            }
            return result;
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieDTOById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            return new MovieDTO(movie);
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieDTOByName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new dto.MovieDTO(m) FROM Movie m WHERE m.name = :name", MovieDTO.class)
                    .setParameter("name", name).getSingleResult();
        } finally {
            em.close();
        }
    }

    public void populateMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie(2008, "Iron Man"));
            em.persist(new Movie(2010, "Iron Man 2"));
            em.persist(new Movie(2013, "Iron Man 3"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
