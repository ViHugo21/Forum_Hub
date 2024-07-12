package com.Alura.Challenge.Forum_Hub.Model;

public enum Curso {
    JAVA("java"),
    HTML("html"),
    CSS("css"),
    JAVASCRIPT("javascript"),
    REACT("react"),
    NODE("node"),
    PYTHON("python");

    private String cursoOmdb;

    Curso(String curso) {
        this.cursoOmdb = curso;
    }

    public static Curso fromString(String text) {
        for(Curso curso: Curso.values()) {
            if (curso.cursoOmdb.equalsIgnoreCase(text)) {
                return curso;
            }
        }
        throw new IllegalArgumentException("Curso inválido");
    }
}
