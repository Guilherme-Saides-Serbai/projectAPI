package redeSocial.Entidade;

public class Pessoa {
    private Integer id;
    private String nome;
    private Double nota;
    private Boolean masculino;

    // Construtor vazio (necessário para JPA/Spring)
    public Pessoa() {
    }

    // Construtor com parâmetros
    public Pessoa(Integer id, String nome, Double nota, Boolean masculino) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
        this.masculino = masculino;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Boolean getMasculino() {
        return masculino;
    }

    public void setMasculino(Boolean masculino) {
        this.masculino = masculino;
    }

    // Método toString para debug
    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nota=" + nota +
                ", masculino=" + masculino +
                '}';
    }
}
