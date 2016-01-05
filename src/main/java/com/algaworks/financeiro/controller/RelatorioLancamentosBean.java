package com.algaworks.financeiro.controller;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.algaworks.financeiro.repository.Lancamentos;
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
	
	private BigDecimal total;
	
    private BigDecimal saldoNegativos;
	
	private BigDecimal lucro;
	
	@Inject
	private ConsultaLancamentosBean lancamentosbean;
	
	
	@Inject
	private Lancamentos lancamentos;

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
		parametros.put("saldoNegativos",this.saldoNegativos);
		parametros.put("lucro",this.lucro);
		parametros.put("total",this.total);
		
		System.out.println("saldoNegativos=>"+lancamentos.saldoNegativo()+" LUCRO:"+lancamentosbean.getLucro()
		+" Total de Receitas:"+ lancamentosbean.getTotal());				
		
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
	ConsultaLancamentosBean luc = new ConsultaLancamentosBean();
	public BigDecimal getLucro() {
		return lucro;
	}

	public void setLucro(BigDecimal lucro) {
		this.lucro = lancamentosbean.getLucro();
	}
	
	@NotNull
	ConsultaLancamentosBean tot = new ConsultaLancamentosBean();
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = lancamentosbean.getTotal();
	}	
	
	//lancamentos.Lucro(15)
	
	
	@NotNull
	ConsultaLancamentosBean sneg = new ConsultaLancamentosBean();
	public BigDecimal getSaldoNegativos() {
		return saldoNegativos;
	}

	public void setSaldoNegativos(BigDecimal saldoNegativos) {
		this.saldoNegativos = lancamentos.saldoNegativo();
	}	
	


	
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