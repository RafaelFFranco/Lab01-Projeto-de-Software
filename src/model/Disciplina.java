import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private int numCreditos;
    private List<Aluno> alunos;
    private Professor professor;

    public Disciplina(String nome, int numCreditos, int vagasMax, Professor professor) {
        this.nome = nome;
        this.numCreditos = numCreditos;
        this.vagasMax = vagasMax;
        this.professor = professor;
        this.alunos = new ArrayList<>();
    }

    public String getNome() {
        return nome; 
    }

    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public List<Aluno> getAlunos() { 
        return alunos; 
    }

    public Professor getProfessor() { 
        return professor; 
    }


    /* Implementar na Sprint03 */
    public List<Aluno> alunosMatriculados() { 
        
    }

    /* Implementar na Sprint03 */
    private int checkTotalAlunos() { 
         
    }

    /* Implementar na Sprint03 */
    public boolean checkAtiva() { 
        
    }
}
