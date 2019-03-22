package Parser.Problema;

import Parser.VarConst;
import Parser.Predicado;
import Busca.Estado;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Problem
{
	public static final int INICIAL = 0;
	public static final int OBJETIVO = 1;
	
	private String nomeDominio;
	private Map<Integer, VarConst> objetos;
	private Map<Integer, Predicado> estadoInicial;
	private HashMap<Integer, Predicado> estadoObjetivo;
	
	public Problem()
	{
		objetos = new HashMap<Integer, VarConst>();
		estadoInicial = new HashMap<Integer, Predicado>(32, (float)0.1);
		estadoObjetivo = new HashMap<Integer, Predicado>(32, (float)0.1);
	}
	
	public Map<Integer, Predicado> getObjetivo()
	{
		return (HashMap<Integer, Predicado>) estadoObjetivo.clone();
	}
	
	public String getNome()
	{
		return nomeDominio;
	}
	
	public void setNome(String nome)
	{
		nomeDominio = nome;
	}
	
	public void adicionaObjeto(Map<Integer, VarConst> o)
	{
		objetos = o;
	}
	
	public int sizeObjetos()
	{
		return objetos.size();
	}
	
	public boolean hasObject(String nome)
	{
		return objetos.containsKey(new VarConst(nome, "1").hashCode());
	}
	
	public VarConst getObject(String nome)
	{
		return objetos.get(new VarConst(nome, "1").hashCode());
	}
	
	public Map<Integer, VarConst> getObjects()
	{
		return objetos;
	}
	
	public Estado geraEstadoInicial()
	{
		return new Estado((HashMap<Integer, Predicado>)estadoInicial, null, 0, null);
	}
	
	public boolean isObjective(Estado e)
	{
		Set<Integer> keys = estadoObjetivo.keySet();
		for(Integer key : keys)
			if(!e.hasPredicado(key)) return false; 
		
		return true;
	}
	
	public int removeSubObjective(Map<Integer, Predicado> estadoObjetivo, Estado e)
	{
		int count = 0;
		Set<Integer> keys = estadoObjetivo.keySet();
		for(Iterator<Integer> it = keys.iterator(); it.hasNext(); )
		{
			if(e.hasPredicado(it.next()))
			{
				it.remove();
				count++;	
			}
		}
		return count;
	}
	
	public void adicionaEstado(LinkedList<Predicado> predicados, int estado)
	{
		if(estado == INICIAL)
		{
			while(predicados.size() > 0)
			{
				Predicado p = predicados.remove();
				estadoInicial.put(p.hashCode(), p);
			}
		}	
		else
		{
			while(predicados.size() > 0)
			{
				Predicado p = predicados.remove();
				estadoObjetivo.put(p.hashCode(), p);
			}
		}	
	}
}