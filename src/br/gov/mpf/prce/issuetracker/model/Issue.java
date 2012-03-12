package br.gov.mpf.prce.issuetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Issue implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ISSUE")
	@SequenceGenerator(name="SEQ_ISSUE", sequenceName="SEQ_ISSUE", allocationSize=1)
	private Long id;
	private String sumario;
	@Column(length=1000)
	private String descricao;
	
	@ManyToOne
	private Projeto projeto;
	
	@Enumerated(EnumType.STRING)
	private TipoDaIssue tipo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reportadoEm;
	
	@ManyToOne
	@JoinColumn(name="USUARIO_REPORTADOR")
	private Usuario reportadoPor;
	@ManyToOne
	@JoinColumn(name="ASSINADO_PARA", 
		referencedColumnName="USU_LOGIN")
	private Usuario assinadoPara;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizadoEm;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	// EAGER e LAZY
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	@JoinTable(name="ISSUE_COMENTARIO", 
		joinColumns=@JoinColumn(name="ISSUE_ID"),
		inverseJoinColumns=@JoinColumn(name="COMENTARIO_ID"))
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSumario() {
		return sumario;
	}

	public void setSumario(String sumario) {
		this.sumario = sumario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public TipoDaIssue getTipo() {
		return tipo;
	}

	public void setTipo(TipoDaIssue tipo) {
		this.tipo = tipo;
	}

	public Date getReportadoEm() {
		return reportadoEm;
	}

	public void setReportadoEm(Date reportadoEm) {
		this.reportadoEm = reportadoEm;
	}

	public Usuario getReportadoPor() {
		return reportadoPor;
	}

	public void setReportadoPor(Usuario reportadoPor) {
		this.reportadoPor = reportadoPor;
	}

	public Usuario getAssinadoPara() {
		return assinadoPara;
	}

	public void setAssinadoPara(Usuario assinadoPara) {
		this.assinadoPara = assinadoPara;
	}

	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public List<Comentario> getComentarios() {
		return Collections.unmodifiableList(comentarios);
//		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void adicionaComentario(Comentario comentario) {
		
		if (comentario.getAutor() == null)
			throw new IllegalArgumentException("Autor nao pode ser vazio.");
		
		this.getComentarios().add(comentario);
	}

	public void fecha(Comentario comentario) {
		this.status = Status.FECHADA;
		this.adicionaComentario(comentario);
		this.atualizadoEm = new Date();
	}
	
}








