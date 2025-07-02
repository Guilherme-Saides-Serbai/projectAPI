package redeSocial.GUI;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import redeSocial.Entidade.Pessoa;

@RestController
@RequestMapping
public class PessoaGUI {
    // Lista temporária para simular um banco de dados
    private List<Pessoa> pessoas = new ArrayList<>();

    public static void retornoPessoa(String[] args) {
        System.out.println("pessoa funciona");
    }

    // Endpoint para criar uma nova pessoa
    @PostMapping({ "/pessoas", "/pessoas/" })
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        pessoa.setId(pessoas.size() + 1); // Simula ID auto-increment
        pessoas.add(pessoa);
        return pessoa;
    }

    // Endpoint para listar todas as pessoas
    @GetMapping({ "/pessoas", "/pessoas/" })
    public List<Pessoa> listarPessoas() {
        return pessoas;
    }

    // Endpoint para buscar pessoa por ID
    @GetMapping({ "/pessoas/{id}", "/pessoas/{id}/" })
    public Pessoa buscarPessoa(@PathVariable Integer id) {
        return pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Método DELETE novo
    @DeleteMapping({ "/pessoas/{id}", "/pessoas/{id}/" })
    public ResponseEntity<String> deletarPessoa(@PathVariable Integer id) {
        boolean pessoaExiste = pessoas.stream()
                .anyMatch(p -> p.getId().equals(id));

        if (pessoaExiste) {
            pessoas.removeIf(p -> p.getId().equals(id));
            return ResponseEntity.ok("Pessoa com ID " + id + " foi removida com sucesso!");
        } else {
            return ResponseEntity.status(404)
                    .body("Pessoa com ID " + id + " não encontrada!");
        }
    }

    // requisicao alterar
    @PutMapping({ "/pessoas/{id}", "/pessoas/{id}/" })
    public ResponseEntity<Object> atualizarPessoa(
            @PathVariable Integer id,
            @RequestBody Pessoa pessoaAtualizada) {

        // Busca a pessoa existente
        Pessoa pessoaExistente = pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (pessoaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pessoa com ID " + id + " não encontrada!");
        }

        // Atualiza os campos (exceto o ID)
        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setNota(pessoaAtualizada.getNota());
        pessoaExistente.setMasculino(pessoaAtualizada.getMasculino());

        return ResponseEntity.ok(pessoaExistente);
    }

    // Endpoint raiz com exemplo de uso
    @GetMapping({ "/pessoas/index", "/pessoas/index/" })
    public String index() {
        // Criando uma pessoa manualmente
        Pessoa exemplo = new Pessoa(1, "João Silva", 9.5, true);

        return """
                <h1>API de Pessoas</h1>
                <p>Exemplo de pessoa:</p>
                <pre>%s</pre>
                <p>Acesse <a href='/pessoas'>/pessoas</a> para ver a lista</p>
                """.formatted(exemplo.toString());
    }

}
