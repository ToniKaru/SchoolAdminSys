package se.iths.vimton.dao;

import se.iths.vimton.entities.Language;

import java.util.List;

public interface LanguageDao {

    void create(Language language);
    void update(Language language);
    void delete(Language language);
    Language getById(int id);
    List<Language> getByName(String name);
    List<Language> getAll();

}
