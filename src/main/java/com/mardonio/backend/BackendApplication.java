package com.mardonio.backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mardonio.backend.domain.Cliente;
import com.mardonio.backend.domain.Endereco;
import com.mardonio.backend.domain.Usuario;
import com.mardonio.backend.domain.enums.Perfil;
import com.mardonio.backend.repositories.ClienteRepository;
import com.mardonio.backend.repositories.EnderecoRepository;
import com.mardonio.backend.repositories.UsuarioRepository;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository endRepository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		
		
		Cliente c1 = new Cliente(null, "Oliver Marcelo Tomás Martins", "10019521707");
		Cliente c2 = new Cliente(null, "Luana Alana Alessandra da Costa", "05840917109");
		
		Endereco e1 = new Endereco(null, "Vila São Pedro", "285", "Apto 303", "Alecrim", "59030310", "Natal", "RN", c1);
		Endereco e2 = new Endereco(null, "Rua São Cristóvão", "989", "sala 800", "Guarituba", "83311497", "Guarituba", "PR", c2);
		
		c1.getTelefones().addAll(Arrays.asList("8428667489","84981207063"));
		c2.getTelefones().addAll(Arrays.asList("4138381257","41999205110"));

		c1.getEmails().addAll(Arrays.asList("email1@gmail.com","email2@hotmail.com","email3@empresa.com"));
		c2.getEmails().addAll(Arrays.asList("email1@gmail.com","email2@hotmail.com"));
		
		c1.getEnderecos().addAll(Arrays.asList(e1));
		c2.getEnderecos().addAll(Arrays.asList(e2));

		clienteRepository.saveAll(Arrays.asList(c1, c2));
		endRepository.saveAll(Arrays.asList(e1, e2));
		
		Usuario admin = new Usuario(null, "admin", pe.encode("123456"));
		Usuario comum = new Usuario(null, "comum", pe.encode("123456"));
		
		admin.addPerfil(Perfil.ADMIN);
		comum.addPerfil(Perfil.COMUM);

		userRepository.saveAll(Arrays.asList(admin, comum));

	}

}
