package com.mardonio.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mardonio.backend.domain.Usuario;
import com.mardonio.backend.repositories.UsuarioRepository;
import com.mardonio.backend.security.UserSS;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Usuario user = repo.findByNome(usuario);
		if(user == null) {
			throw new UsernameNotFoundException(usuario);
		}

		return new UserSS(user.getId(), user.getNome(), user.getSenha(), user.getPerfis());
	}

}