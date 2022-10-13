package br.com.diego.Lanchonete.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.diego.Lanchonete.service.interfaces.ClienteServico;
import br.com.diego.Lanchonete.templates.Cliente;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteServico clienteServico;

    @GetMapping
    public List<Cliente> listar() {
        return clienteServico.listarClientes();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable(value = "id") Long clienteId) {
        return clienteServico.buscaCliente(clienteId);
    }

    @PostMapping("/cadastro")
    public String adicionar(@RequestBody Cliente cliente) {
        clienteServico.salvarCliente(cliente);
        return "Novo cliente adicionado com sucesso!";
    }

    @PutMapping("/{id}/alterar")
    public String atualizar(@RequestBody Cliente clienteDetalhes, @PathVariable(value="id") Long clienteId) {
        Cliente cliente = clienteServico.buscaCliente(clienteId);

        cliente.setCpf(clienteDetalhes.getCpf());
        cliente.setNome(clienteDetalhes.getNome());
        cliente.setTelefone(clienteDetalhes.getTelefone());
        cliente.setEmail(clienteDetalhes.getEmail());

        clienteServico.salvarCliente(cliente);
        return "Cliente "+cliente.getNome()+" de id "+clienteId+", atualizado com sucesso!";
    }
}