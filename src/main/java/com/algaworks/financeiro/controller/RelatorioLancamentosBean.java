package com.algaworks.financeiro.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.algaworks.financeiro.util.jsf.FacesUtil;
import com.algaworks.financeiro.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioLancamentosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//private Date dataInicio;
	//private Date dataFim;
	
	//private Long id;
	private String nome;
	//private Long id;

	

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		//parametros.put("data_inicio", this.dataInicio);
		//parametros.put("data_fim", this.dataFim);
		//parametros.put("usuario", this.id);
		parametros.put("nome", this.nome);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorioLancamentos.jasper",
				this.response, parametros, "Lancamentos.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}
	
	/*@NotNull
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/
	
	@NotNull
	UsuarioController user = new UsuarioController();
	public String getNome() {
		return user.getUsuario().getUsername();
	}

	public void setNome(String nome) {
		this.nome = user.getUsuario().getUsername();
	}

	/*@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}*/

	/*@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}*/

}