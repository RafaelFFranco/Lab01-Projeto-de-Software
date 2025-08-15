import java.util.List;

public class Curso {
    private String nome;
    private int numeroCreditos;
    private List<Disciplina> disciplinas;

    public Curso(String nome, int creditos) {
        this.nome = nome;
        this.creditos = creditos;
    }

    public String getNome() { 
        return nome; 
    }

    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public int getCreditos() { 
        return creditos; 
    }

    public void setCreditos(int creditos) { 
        this.creditos = creditos; 
    }

    public List<Disciplina> getDisciplinas() { 
        return disciplinas; 
    }

    public void setDisciplinas(List<Disciplina> disciplinas) { 
        this.disciplinas = disciplinas; 
    }

    /* Implementar na Sprint03 */ 
    public void gerarCurriculo() {

    }
}