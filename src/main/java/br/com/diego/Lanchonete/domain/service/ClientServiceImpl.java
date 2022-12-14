package br.com.diego.Lanchonete.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import br.com.diego.Lanchonete.domain.exceptions.NoDataFoundException;
import br.com.diego.Lanchonete.domain.repository.ClienteRepository;
import br.com.diego.Lanchonete.domain.templates.Cliente;

@Service
public class ClientServiceImpl {
    @Autowired
    private ClienteRepository repository;

    @Transactional
    public Cliente cadastrarCliente(Cliente cliente) {
        if(repository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("> cliente já cadastrada com o CPF='"+cliente.getCpf()+"' !");
        }

        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(Cliente clienteInput, Long id) {
        Optional<Cliente> clienteSearchByCpf = repository.findByCpf(clienteInput.getCpf());

        if(clienteSearchByCpf.isPresent()) {
            if(clienteSearchByCpf.get().getId() == id) {
                clienteInput.setId(clienteSearchByCpf.get().getId());
            } else {
                throw new IllegalArgumentException("> CPF='"+clienteInput.getCpf()+"' não corresponde ao id='"+id+"' do produto a ser atualizado");
            }
        } else {
            throw new IllegalArgumentException("> Nenhum cliente com este CPF='"+clienteInput.getCpf()+"' cadastrado"); 
        }

        return repository.save(clienteInput);
    }

    public Page<Cliente> listarClientes(Pageable paginacao) {
        Page<Cliente> clientes;

        try {
            clientes = repository.findAll(paginacao);
        } catch(NoDataFoundException e) {
            throw new NoDataFoundException("> Nenhum dado de cliente encontrado!");
        }
        
        return clientes;
    }

    public Cliente pesquisarClientePorIdentificador(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> 
                new EntityNotFoundException("> cliente não encontrado pelo id='"+id+"' especificado!")
            );
    }

    @Transactional
    public void removerCliente(Long id) {
        try {
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("> cliente não encontrado pelo id='"+id+"' especificado!");
        }
    }
}
