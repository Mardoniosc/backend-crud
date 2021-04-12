package com.mardonio.backend.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mardonio.backend.domain.Cliente;
import com.mardonio.backend.domain.Endereco;
import com.mardonio.backend.repositories.ClienteRepository;
import com.mardonio.backend.repositories.EnderecoRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository endRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		Endereco end = obj.getEnderecos().get(0);
		end.setCliente(obj);
		endRepository.saveAll(Arrays.asList(end));
		return clienteRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		clienteRepository.deleteById(id);			
	}
	
	public Cliente update(Cliente obj) {
		
		Endereco end = obj.getEnderecos().get(0);
		Cliente objEnd = find(obj.getId());
				
		if(objEnd.getEnderecos().isEmpty()) {
			end.setCliente(obj);
		} else {
			Endereco endCliente = obj.getEnderecos().get(0);
			endCliente.setCliente(obj);
		}
		
		return clienteRepository.save(obj);
	}

	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
}
