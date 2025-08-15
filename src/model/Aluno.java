import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private String matricula;
    private List<Disciplina> disciplinas;

    public Aluno(String nome, String login, String senha, String matricula) {
        super(nome, login, senha);
        this.matricula = matricula;
        this.disciplinas = new ArrayList<>();
    }

    public String getMatricula() { 
        return matricula; 
    }

    public void setMatricula(String matricula) { 
        this.matricula = matricula; 
    }

    public List<Disciplina> getDisciplinas() { 
        return disciplinas; 
    }

    /* Implementar na Sprint03 */ 
    public void efetuarMatricula() {

    }

    /* Implementar na Sprint03 */ 
    public void cancelarMatricula() {

    }

    /* Implementar na Sprint03 */ 
    public int numeroDisciplinas() {

    }
}