package se.iths.vimton.impl;

import se.iths.vimton.dao.LanguageDao;
import se.iths.vimton.entities.Language;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class LanguageDaoImpl implements LanguageDao {

    EntityManager em;

    public LanguageDaoImpl (EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public void create(Language language) {
        if(exists(language)) {
            System.out.println("Language: " + language.getName() + " already exists.");
            return;
        }
        em.getTransaction().begin();
        em.persist(language);
        em.getTransaction().commit();
    }

    private boolean exists(Language language) {
        return getByName(language.getName()).contains(language);
    }

    @Override
    public void update(Language language) {
        if(!exists(language)) {
            System.out.println("Could not update language. Language: " + language.getName() + " does not exist.");
            return;
        }
        em.getTransaction().begin();
        em.merge(language);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Language language) {
        if(!exists(language))
            return;
        em.getTransaction().begin();
        em.remove(language);
        em.getTransaction().commit();
    }

    @Override
    public Optional<Language> getById(int id) {
        return em.createQuery("SELECT l FROM Language l WHERE l.id = :id", Language.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Language> getByName(String name) {
        return em.createQuery("SELECT l FROM Language l " +
                        "WHERE l.name LIKE :name", Language.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    @Override
    public List<Language> getAll() {
        return em.createQuery("SELECT l FROM Language l", Language.class)
                .getResultList();
    }
}
