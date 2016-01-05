package com.algaworks.financeiro.controller;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.financeiro.model.Autorizacao;
import com.algaworks.financeiro.repository.Autorizacoes;
import com.algaworks.financeiro.util.JpaUtil;

@ManagedBean
@SessionScoped
public class ConsultaAutorizacaoBean {

	@Inject
	private Autorizacoes role;

	private List<Autorizacao> autorizacao;
	
	
	
	public void consultar() {
	EntityManager manager = JpaUtil.getEntityManager();
	this.autorizacao = role.total();
	manager.close();
	}
	
	
	
	
	public List<Autorizacao> getAutorizacoes() {
	return autorizacao;
	}
	
	/*
	 public void consultar() {
		this.lancamentos = lancamentosRepository.todosLanc(); 
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	 */
	
	
	
}