package com.algaworks.financeiro.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
//import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.algaworks.financeiro.model.Autorizacao;

public class Autorizacoes implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	@Inject
	public Autorizacoes(EntityManager manager) {
		this.manager = manager;
	}

	public List<Autorizacao> total() {
		TypedQuery<Autorizacao> query = manager.createQuery("from Autorizacao",
				Autorizacao.class);
		return query.getResultList();
	}

}
