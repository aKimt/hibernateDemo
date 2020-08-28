package artifact.repository;

import artifact.repository.model.Personne;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PersonneDAO{

    private EntityManager em;

    public PersonneDAO(EntityManagerFactory factory) {
        em = factory.createEntityManager();
    }

    public void update(Personne p)
    {
        em.getTransaction().begin();

        p = em.find(Personne.class, p.getId());
        p.setNom("lulu");

        em.getTransaction().commit();
    }
}