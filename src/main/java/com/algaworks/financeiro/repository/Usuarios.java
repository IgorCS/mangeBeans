package com.algaworks.financeiro.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.algaworks.financeiro.controller.UsuarioController;
import com.algaworks.financeiro.model.Usuario;
import com.algaworks.financeiro.security.UserSistema;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Usuarios(EntityManager manager) {
		this.manager = manager;
	}
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public void adicionar(Usuario usuario){
		this.manager.persist(usuario);
	}

	public Usuario guardar(Usuario usuario){
		return this.manager.merge(usuario);
	}
	
	public Usuario porUsername(String username) {
		return manager.find(Usuario.class, username);
	}

	
	/*public List<Usuario> todosUsuarios() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}*/
	
	public List<Usuario> todosUsuarios() {
		UsuarioController user = new UsuarioController();
		// TODO filtrar apenas vendedores (por um grupo específico)		
		TypedQuery<Usuario> query = manager.createQuery("from Usuario u WHERE " +
				"u.nome="+"'"+user.getUsuario().getUsername()+"'"
				,Usuario.class);
		//query.setParameter("usuario", usuario);
		/*
		 "select l FROM Lancamento l INNER JOIN l.usuario u WHERE " +
				 "u.nome="+"'"+user.getUsuario().getUsername()+"'"
		 */
		return query.getResultList();
	}	
	
	
	public Usuario porNome(String nome) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where nome = :nome", Usuario.class)
				.setParameter("nome", nome.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		
		return usuario;
	}
	
	public Usuario porId(Usuario usuario) {
		//Usuario usuario = null;
		TypedQuery<Usuario> query = manager.createQuery("from Usuario u where u.usuario=:usuario ",
				Usuario.class);
		query.setParameter("usuario", usuario);
		return query.getSingleResult();
		//return usuario;
	}
	
/*	public UsuarioNovo porNome(String nome) {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("usuariosNovo");
	EntityManager em = factory.createEntityManager();

	try {
		UsuarioNovo usuario = (UsuarioNovo) em
				.createQuery("SELECT u from Usuario u where u.nome = :nome ")
				.setParameter("nome", nome).getSingleResult();

		return usuario;
	} catch (NoResultException e) {
		return null;
	}*/	
	
}