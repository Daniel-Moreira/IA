package Parser.Dominio;

import Parser.Predicado;
import Parser.VarConst;
import Busca.Estado;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

public class Acao
{
	private String nome;
	private Map<Integer, VarConst> parametros;
	private Map<Integer, Predicado> precondicoes;
	private Map<Integer, Predicado> efeitos;
	
	// Define todas os efeitos e precondicoes possiveis
	private LinkedList<VarConst[]> instancias;
	private LinkedList<Integer[]> hashPrecondicoes;
	private LinkedList<Map<Integer, Predicado>> listaEfeitos;

	public Acao(String nome, Map<Integer, VarConst> p)
	{
		this.nome = nome;
		parametros = p;
		instancias = new LinkedList<VarConst[]>();
		hashPrecondicoes = new LinkedList<Integer[]>();
		listaEfeitos = new LinkedList<Map<Integer, Predicado>>();
	}
	
	public Acao(String nome, Map<Integer, VarConst> parametros, Map<Integer, Predicado> precondicoes, Map<Integer, Predicado> efeitos)
	{
		this.nome = nome;
		this.parametros = parametros;
		this.precondicoes = precondicoes;
		this.efeitos = efeitos;
	}
	
	public void setPreconditions(Map<Integer, Predicado> p)
	{
		precondicoes = p;
	}
	
	public void setEffects(Map<Integer, Predicado> e)
	{
		efeitos = e;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public Map<Integer, VarConst> getParametros()
	{
		return parametros;
	}
	
	public boolean hasVariable(String nome)
	{
		return parametros.containsKey(new VarConst(nome, "1").hashCode());
	}
	
	public VarConst getVariable(String nome)
	{
		return parametros.get(new VarConst(nome, "1").hashCode());
	}
	
	public Map<Integer, Predicado> getPrecondicoes()
	{
		return precondicoes;
	}
	
	public Map<Integer, Predicado> getEfeitos()
	{
		return efeitos;
	}
	
	// Gera todas as instancias
	public void instancia(Map<Integer, VarConst> objetos)
	{
		Integer[] pKeys = parametros.keySet().toArray(new Integer[0]);
		Integer[] oKeys = objetos.keySet().toArray(new Integer[0]);
		int[] iKey = new int[sizeParametros()+1];
		for(int i = 0; i < sizeParametros()+1; i++) iKey[i] = 0;
		
		// Percorre todas as permutacoes
		while(iKey[sizeParametros()] == 0)
		{	
			VarConst[] instancias = new VarConst[sizeParametros()];
			int count = 0;
			// Percorre todos os parametros
			for(int i = 0; i < sizeParametros(); i++)
			{
				// I-esimo parametro
				VarConst parametroI = parametros.get(pKeys[i]);
				// Para cada parametro acha um objeto
				for(; iKey[i] < oKeys.length; iKey[i]++)
				{
					VarConst objeto = objetos.get(oKeys[iKey[i]]);
					if(objeto.equalType(parametroI))
					{
						parametroI.setInstancia(objeto);
						instancias[i] = objeto;
						count++;
						break;
					}
				}
				if(iKey[i] == oKeys.length) iKey[i]--;
			}
			
			for(int i = 0; i < sizeParametros()-1; i++)
				for(int j = i+1; j < sizeParametros(); j++)
					if(instancias[i] == instancias[j]) count--;
				
			// Se achou a qtd certa de parametros e a acao eh aplicavel ao estado
			if(count == sizeParametros())
			{
				this.instancias.add(instancias);
				calculaHash();
				calculaEfeitos();
			}
			
			// Proxima permutacao
			iKey[0]++;
			for(int i = 0; i < sizeParametros(); i++)
			{
				if(iKey[i] == oKeys.length)
				{
					iKey[i] = 0;
					iKey[i+1]++;
				}
			}
		}
	}
	
	// Aplica todas as instancias da acao para o estado
	public LinkedList<Estado> aplicaAll(Estado estado, boolean unicoEstado)
	{
		LinkedList<Estado> answer = new LinkedList<Estado>();
		
		Estado estadoBase = estado;
		if(unicoEstado) estadoBase = estado.pai;
		Set<Integer> keys = parametros.keySet();
		Iterator<Integer[]> it = hashPrecondicoes.iterator();
		Iterator<Map<Integer, Predicado>> itEfeito = listaEfeitos.iterator();
		for(Iterator<VarConst[]> iterator = instancias.iterator(); iterator.hasNext(); )
		{
			int i = 0;
			VarConst[] objetos = iterator.next();
			Integer[] precondicao = it.next();
			Map<Integer, Predicado> efeito = itEfeito.next();
			String acaoAplicada = this.nome;
			
			if(!unicoEstado)
			{				
				for(Integer key : keys)
				{
					VarConst parametroI = parametros.get(key);
					acaoAplicada += " " + objetos[i].getName() + "(" + parametroI.getName() + ")";
					i++;
				}
			}
			
			if(acaoAplicavel(estadoBase.getPredicados(), precondicao))
			{
				if(unicoEstado)	estado.addPredicados(estado.getPredicados(), precondicao, efeito, false);
				else answer.add(estado.aplicaAcao(efeito, acaoAplicada));
			}
		}
		
		return answer;
	}
	
	public void calculaEfeitos()
	{
		Set<Integer> keys = efeitos.keySet();
		Map<Integer, Predicado> e = new HashMap<Integer, Predicado>();
		for(Integer key : keys)
		{
			Predicado p = efeitos.get(key).changeInstacia();
			e.put(p.hashCode(), p);
		}
		
		listaEfeitos.add(e);
	}
	
	public void calculaHash()
	{
		Set<Integer> keys = precondicoes.keySet();
		Integer[] vetor = new Integer[precondicoes.size()];
		int i = 0;
		for(Integer key : keys)
			vetor[i++] = precondicoes.get(key).changeInstacia().hashCode();
		
		hashPrecondicoes.add(vetor);
	}
	
	public boolean acaoAplicavel(Map<Integer, Predicado> estado, Integer[] keys)
	{
		for(int i = 0; i < precondicoes.size(); i++)
			if(!estado.containsKey(keys[i])) return false;	

		return true;
	}
	
	public int sizeParametros()
	{
		return parametros.size();
	}
	
	public int hashCode()
	{
		return nome.hashCode();
    }
}