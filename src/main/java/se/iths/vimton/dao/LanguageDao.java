package se.iths.vimton.dao;

import se.iths.vimton.entities.Language;

import java.util.List;

public interface LanguageDao {

    void create(Language language);
    void update(Language language);
    void delete(Language language);
    List<Language> getByName(String name);
    Language getById(int id);
    List<Language> getAll();

}
