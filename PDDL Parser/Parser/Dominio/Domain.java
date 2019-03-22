package Parser.Dominio;

import Parser.Predicado;
import Parser.VarConst;
import Busca.Estado;
import Parser.Problema.Problem;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class Domain
{
	private String nomeDominio;
	private Map<Integer, String> tipos;
	private Map<Integer, VarConst> constantes;
	private Map<Integer, Predicado> predicados;
	private Map<Integer, Acao> acoes;
	
	public Domain()
	{
		tipos = new HashMap<Integer, String>();
		constantes = new HashMap<Integer, VarConst>();
		predicados = new HashMap<Integer, Predicado>();
		acoes = new HashMap<Integer, Acao>();
	}
	
	public String getNome()
	{
		return nomeDominio;
	}
	
	public Acao getAction(String nome)
	{
		return acoes.get(nome.hashCode());
	}
	
	public void setNome(String nome)
	{
		nomeDominio = nome;
	}
	
	public void adicionaTipo(String tipo)
	{
		tipos.put(tipo.hashCode(), tipo);
	}
	
	public boolean hasType(String tipo)
	{
		return tipos.containsKey(tipo.hashCode());
	}
	
	public int sizeTipos()
	{
		return tipos.size();
	}
	
	public void adicionaConstante(String nome, String tipo)
	{
		VarConst c = new VarConst(nome, tipo);
		constantes.put(c.hashCode(), c);
	}
	
	public int sizeConstantes()
	{
		return constantes.size();
	}
	
	public void adicionaPredicado(String nome, Map<Integer, VarConst> v)
	{
		Predicado p = new Predicado(nome, v);
		predicados.put(p.hashCode(), p);
	}
	
	public int sizePredicados()
	{
		return predicados.size();
	}
		
	public Acao adicionaAcao(String nome, Map<Integer, VarConst> parame)
	{
		Acao a = new Acao(nome, parame);
		acoes.put(a.hashCode(), a);
		
		return a;
	}
	
	public void geraInstancias(Problem p)
	{
		Set<Integer> keys = acoes.keySet();
		
		for(Integer key : keys)
		{
			Acao a = acoes.get(key);
			a.instancia(p.getObjects());
		}
	}
	
	public LinkedList<Estado> geraSucessores(Estado estadoCorrente, boolean estadoUnico)
	{
		Set<Integer> keys = acoes.keySet();
		LinkedList<Estado> estados = new LinkedList<Estado>();
		
		for(Integer key : keys)
		{
			Acao a = acoes.get(key);
			if(estadoUnico) a.aplicaAll(estadoCorrente, estadoUnico);
			else estados.addAll(a.aplicaAll(estadoCorrente, estadoUnico));
		}
		
		return estados;
	}
	
	public int sizeAcoes()
	{
		return acoes.size();
	}
}