package br.com.triadworks.issuetracker.dao;

import java.util.List;

import br.com.triadworks.issuetracker.model.Comentario;
import br.com.triadworks.issuetracker.model.Issue;

public interface IssueDao {

	public void salva(Issue issue);

	public void atualiza(Issue issue);

	public Issue carrega(Long id);

	public void remove(Issue issue);

	public void comenta(Long idDaIssue, Comentario comentario);

	public void fecha(Long idDaIssue, Comentario comentario);

	public List<Issue> listaTudo();

	public List<Issue> getIssuesComComentarios();

	public List<Comentario> getComentarios(Long idDaIssue);

	public List<Issue> buscaPorSumario(String sumario);

}