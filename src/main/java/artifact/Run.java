package artifact;

import artifact.repository.PersonneDAO;
import artifact.repository.model.Personne;
import org.hibernate.FlushMode;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class Run {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("artifact");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        em.setFlushMode(FlushModeType.AUTO);

        em.getTransaction().begin();

        // CREATE --------------------------------------------------
        // INSERT

        Personne p = new Personne();
        p.setNom("luc");
        em.persist(p);

        p = new Personne();
        p.setNom("luc");
        em.persist(p);
        // ----------------------------------------------------------


        // READ -----------------------------------------------------
        // READ ONE
        System.out.println( em.find(Personne.class, 176).getNom() );


        // READ SOME - precise getting
        CriteriaQuery<String> cQuery = cb.createQuery(String.class);
        Root<Personne> root = cQuery.from(Personne.class);
        cQuery.select(root.get("nom"));

        System.out.println("-----------------");
        List<String> list = em.createQuery(cQuery).getResultList();
        list.forEach(System.out::println);
        System.out.println("-----------------");


        // READ ALL
        CriteriaQuery<Personne> personneCriteriaQuery = cb.createQuery(Personne.class);
        personneCriteriaQuery.from(Personne.class);

        System.out.println("-----------------");
        List<Personne> list3 = em.createQuery(personneCriteriaQuery).getResultList();
        list3.forEach( (pers)-> System.out.println(pers.getId() +"-"+pers.getNom() ) );
        System.out.println("-----------------");
        // ------------------------------------------------------------




        // UPDATE -----------------------------------------------------

        // UPDATE ONE

        Personne toUp = em.find(Personne.class, 176);
        toUp.setNom("luclelicle");
        em.flush();

        // UPDATE MULTIPLE
        CriteriaUpdate<Personne> persUpdater = cb.createCriteriaUpdate(Personne.class);
        Root<Personne> rootPers = persUpdater.from(Personne.class);
        persUpdater.set("nom", "luc")
                .where(cb.equal(rootPers.get("nom"), "lucky luke"));

        em.createQuery(persUpdater).executeUpdate();
        // -----------------------------------------



        // DELETE ----------------------------------
        // DELETE ONE
        //em.remove(p);
        //em.remove( em.find(Personne.class, 3) );

        // DELETE SOME
        CriteriaDelete<Personne> criteriaDelete = cb.createCriteriaDelete(Personne.class);
        Root<Personne> root3 = criteriaDelete.from(Personne.class);
        criteriaDelete.where(cb.equal(root3.get("nom"), "luc"));

        em.createQuery(criteriaDelete).executeUpdate();

        // DELETE ALL
//        criteriaDelete = cb.createCriteriaDelete(Personne.class);
//        root3 = criteriaDelete.from(Personne.class);
//
//        em.createQuery(criteriaDelete).executeUpdate();
        // -----------------------------------------

        // COMMIT
        em.getTransaction().commit();

        em.close();
        emf.close();

    }
}
