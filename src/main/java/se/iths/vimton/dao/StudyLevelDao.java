package se.iths.vimton.dao;

import se.iths.vimton.entities.StudyLevel;

import java.util.List;
import java.util.Map;

public interface StudyLevelDao {

    void create(StudyLevel studyLevel);
    void update(StudyLevel studyLevel);
    void delete(StudyLevel studyLevel);
    StudyLevel getById(int id);
    List<StudyLevel> getByName(String name);
    List<StudyLevel> getAll();

    Map<String, Integer> getStudentsPerStudyLevel();

}
