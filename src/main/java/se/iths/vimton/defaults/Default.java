package se.iths.vimton.defaults;

import se.iths.vimton.entities.Language;

import java.util.List;

public class Default {

    static List<Language> languages() {
        return List.of(
                new Language("English"),
                new Language("Swedish")
        );
    }
}
