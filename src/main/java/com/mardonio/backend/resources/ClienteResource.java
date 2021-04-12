package com.mardonio.backend.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mardonio.backend.domain.Cliente;
import com.mardonio.backend.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente clienteObj = clienteService.find(id);
		return ResponseEntity.ok().body(clienteObj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping()
	public ResponseEntity<Cliente> insert(@Valid @RequestBody Cliente obj) {
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@Valid @RequestBody Cliente obj, @PathVariable Integer id){
		obj.setId(id);
		obj = clienteService.update(obj);
		return ResponseEntity.ok().body(obj);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> list = clienteService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<Cliente>> findPage(
				@RequestParam(value = "page", defaultValue = "0" ) Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue = "nome" ) String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC" ) String direction
			) {
		Page<Cliente> list = clienteService.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}

}
