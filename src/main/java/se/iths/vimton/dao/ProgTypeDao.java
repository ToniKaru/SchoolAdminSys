package se.iths.vimton.dao;

import se.iths.vimton.entities.ProgramType;

import java.util.List;
import java.util.Optional;

public interface ProgTypeDao {

    void create(ProgramType programType);
    void update(ProgramType programType);
    void delete(ProgramType programType);
    Optional<ProgramType> getById(int id);
    List<ProgramType> getByName(String name);
    List<ProgramType> getByAccreditation(boolean accredited);
    List<ProgramType> getAll();

}
