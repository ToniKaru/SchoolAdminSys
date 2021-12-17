package se.iths.vimton.dao;

import se.iths.vimton.entities.StudyLevel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudyLevelDao {

    void create(StudyLevel studyLevel);
    void update(StudyLevel studyLevel);
    void delete(StudyLevel studyLevel);
    Optional<StudyLevel> getById(int id);
    List<StudyLevel> getByName(String name);
    List<StudyLevel> getAll();
    Map<String, Long> getStudentsPerStudyLevel();
    Map<String, Long> getStudentsPerProgram();

}
