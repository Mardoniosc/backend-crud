package com.mardonio.backend.DTO;

import java.io.Serializable;

public class CredenciaisDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String user;
	private String senha;

	public CredenciaisDTO() {}

	public String getEmail() {
		return user;
	}

	public void setEmail(String email) {
		this.user = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}