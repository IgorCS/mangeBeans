package com.algaworks.financeiro.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;

import com.algaworks.financeiro.controller.UsuarioController;
import com.algaworks.financeiro.model.Lancamento;
import com.algaworks.financeiro.model.Pessoa;
import com.algaworks.financeiro.model.TipoLancamento;
import com.algaworks.financeiro.model.Usuario;
import com.algaworks.financeiro.security.AppUserDetailsService;
import com.algaworks.financeiro.security.UserSistema;

public class Lancamentos implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	@Inject
	public Lancamentos(EntityManager manager) {
		this.manager = manager;
	}

	public Lancamento porId(Long id) {
		return manager.find(Lancamento.class, id);
	}

	public List<String> descricoesQueContem(String descricao) {
		TypedQuery<String> query = manager.createQuery(
				"select distinct descricao from Lancamento " + "where upper(descricao) like upper(:descricao)",
				String.class);
		query.setParameter("descricao", "%" + descricao + "%");
		return query.getResultList();
	}

	public List<Lancamento> lancamentoUsuario(Usuario usuario) {
		TypedQuery<Lancamento> query = manager.createQuery("from Lancamento l where l.usuario=:usuario ",
				Lancamento.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	/*
	 * public List<Lancamento> lancamentos(Usuario usuario) {
	 * TypedQuery<Lancamento> query = manager.createQuery(
	 * "from Lancamento l where l.usuario=:usuario ", Lancamento.class);
	 * //UserSistema usuario_ = new UserSistema(usuario, null);
	 * //query.setParameter("usuario", usuario_); // UserSistema("usuario",
	 * useroo); HttpSession session = (HttpSession) new UserSistema(usuario,
	 * null); query.setParameter("usuario", usuario); return
	 * query.getResultList(); }
	 */
	
	public List<Lancamento> lancamentos() {
	UsuarioController user = new UsuarioController();
		TypedQuery<Lancamento> query = manager.createQuery
				("select l FROM Lancamento l INNER JOIN l.usuario u WHERE " +
		 "u.nome="+"'"+user.getUsuario().getUsername()+"'",Lancamento.class);
		//SELECT l FROM Lancamento l INNER JOIN l.usuario u WHERE
		// u.username=:username
		//query.setParameter("usuario", usuario);
		/*System.out.println("pegar usuário pelo nome de Login: "
		+user.getUsuario().getUsername()+" ou ID: "+user.getUsuario().getId());*/
		return query.getResultList();
	}
	
	/*public List<Lancamento> lancamentos() {
		UsuarioController user = new UsuarioController(
		//AppUserDetailsService appUser = new AppUserDetailsService();
		TypedQuery<Lancamento> query = manager.createQuery("from Lancamento l where l.username="
				+user.getUsuario().getUsername()),Lancamento.class);
		//query.setParameter();
		return query.getResultList();
	}*/
	
	/*
	 	public List<Lancamento> lancamentos(Usuario usuario) {
		
		TypedQuery<Lancamento> query = manager.createQuery("from Lancamento l where l.usuario=:usuario ",
				Lancamento.class);
		usuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(usuario);
		query.setParameter("usuario", FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario));
		return query.getResultList();
	}
	 */

	// SELECT * FROM Lancamento l INNER JOIN l.usuario u WHERE
	// l.usuario=:usuario

	/*
	 * public List<Lancamento> lancePessoa(Usuario usuario) {
	 * TypedQuery<Lancamento> query = manager.createQuery(
	 * "from Lancamento l where l.usuario=:usuario", Lancamento.class);
	 * query.setParameter("usuario", usuario); return query.getResultList(); }
	 */

	public List<Lancamento> lancePessoa() {
		UsuarioController user = new UsuarioController();
		TypedQuery<Lancamento> query = manager.createQuery("select l FROM Lancamento l INNER JOIN l.usuario u WHERE " +
				 "u.nome="+"'"+user.getUsuario().getUsername()+"'",Lancamento.class);
		//query.setParameter("usuario", usuario);
		return query.getResultList();
		/*
		 * select usr.id, usr.name from User as usr left join usr.messages as
		 * msg group by usr.id, usr.name order by count(msg)
		 */
	}

	public List<Lancamento> todosLancamento(Usuario usuario) {
		TypedQuery<Lancamento> query = manager.createQuery("from Lancamento l where l.usuario=:usuario ",
				// SELECT * FROM lancamento AS l LEFT JOIN usuario_autorizacao
				// AS u ON u.id=l.id
				// INNER JOIN usuario s ON(s.id=l.usuario_id) WHERE
				// l.usuario_id=3
				Lancamento.class);
		query.setParameter("usuario", usuario);

		return query.getResultList();
	}

	// Select * from lançamento l join l.pessoa p where p.nome...
	// SELECT * FROM lancamento l JOIN usuario u ON(u.id=l.id) WHERE u.id=3

	public BigDecimal calculaTotalMovimentado(TipoLancamento tipo, Pessoa pessoa) {
		String jpql = "select sum(l.valor) from Lancamento " + "l where l.pessoa = :pessoa and l.tipo= :tipo ";
		javax.persistence.Query query = manager.createQuery(jpql);
		query.setParameter("pessoa", pessoa);
		query.setParameter("tipo", tipo);
		return (BigDecimal) ((javax.persistence.Query) query).getSingleResult();
	}

	public BigDecimal Lucro(int i) {
		UsuarioController user = new UsuarioController();
		String jpql = "select sum(l.valor) from Lancamento l INNER JOIN l.usuario u" 
		+ " where u.nome="+"'"+user.getUsuario().getUsername()+"'" + " and l.tipo='RECEITA' ";
		javax.persistence.Query query = manager.createQuery(jpql);
		//query.setParameter("usuario", usuario);
		//query.setParameter("tipo", tipo);
		return (BigDecimal) ((javax.persistence.Query) query).getSingleResult();
	}
	
	
	

	public BigDecimal saldoNegativo() {
		UsuarioController user = new UsuarioController();
		String jpql = "select sum(-l.valor) from Lancamento l INNER JOIN l.usuario u " 
	    + " where u.nome="+"'" +user.getUsuario().getUsername()+"'" + " and l.tipo='DESPESA' ";
		javax.persistence.Query query = manager.createQuery(jpql);
		//query.setParameter("usuario", usuario);
		// query.setParameter("tipo", tipo);
		return (BigDecimal) ((javax.persistence.Query) query).getSingleResult();
	}

	public BigDecimal lucroTotal() {
		UsuarioController usuarioLogado = new UsuarioController();
		String jpql = "select sum(l.valor) - (select sum(l.valor) "
				+ "FROM Lancamento l where l.tipo='DESPESA' and l.usuario=:usuario) "
				+ "FROM Lancamento l WHERE l.tipo='RECEITA' and l.usuario=:usuario";
		javax.persistence.Query query = manager.createQuery(jpql);
	//	query.setParameter("usuario", usuario);
		return (BigDecimal) ((javax.persistence.Query) query).getSingleResult();
	}
	
	
	/*public BigDecimal lucroTotalUsuario(TipoLancamento tipo) {
		UsuarioController user = new UsuarioController();
		String jpql = "select sum(l.valor) - (select sum(l.valor) "
				+ "FROM Lancamento l INNER JOIN l.usuario u WHERE l.tipo='DESPESA' and u.nome="+"'" +user.getUsuario().getUsername()+"')" 
				+ "FROM Lancamento l INNER JOIN l.usuario u WHERE l.tipo='RECEITA' and u.nome="+"'" +user.getUsuario().getUsername()+"'";
		javax.persistence.Query query = manager.createQuery(jpql);
		//query.setParameter("nome", nome);
		return (BigDecimal) ((javax.persistence.Query) query).getSingleResult();
	}*/

	public void adicionar(Lancamento lancamento) {
		this.manager.persist(lancamento);
	}
	
	

	/*
	 * public void adicionar(Lancamento lancamento) { int count = 1; for
	 * (this.manager.persist(lancamento); count <= 10; count++) {
	 * System.out.println(count); } }
	 */

	public Lancamento guarda(Lancamento lancamento) {
		return this.manager.merge(lancamento);
	}

	public void remover(Lancamento lancamento) {
		this.manager.remove(lancamento);
	}
}