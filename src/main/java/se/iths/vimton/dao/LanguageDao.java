package se.iths.vimton.dao;

import se.iths.vimton.entities.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageDao {

    void create(Language language);
    void update(Language language);
    void delete(Language language);
    Optional<Language> getById(int id);
    List<Language> getByName(String name);
    List<Language> getAll();

}
