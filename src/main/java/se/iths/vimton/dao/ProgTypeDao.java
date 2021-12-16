package se.iths.vimton.dao;

import se.iths.vimton.entities.ProgramType;
import java.util.List;

public interface ProgTypeDao {

    void create(ProgramType programType);
    void update(ProgramType programType);
    void delete(ProgramType programType);
    ProgramType getById(int id);
    List<ProgramType> getByAccreditation(boolean accredited);
    List<ProgramType> getAll();

}
