package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;
import Parser.Predicado;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public class FastForward extends Heuristica
{
	public FastForward(Domain d, Problem p)
	{
		domain = d;
		problem = p;
	}
	
	public int f(Estado x)
	{
		if(problem.isObjective(x)) return 0;
		Map<Integer, Predicado> objetivo = problem.getObjetivo();
		int count = 0;
		estadoInicial = x.getPredicados();
		int foundInitial = 0;
		while(foundInitial == 0)
		{
			do
			{
				x = x.copy(true);
				domain.geraSucessores(x, true);
				count++;
			} while(!problem.isObjective(x));
			
			if(x.equals(x.pai)) return 0;
			Set<Integer> keys = objetivo.keySet();
			Map<Integer, Predicado> teste = new HashMap<Integer, Predicado>();
			for(Integer key : keys) teste.put(key, x.getPredicado(key));
			foundInitial = buscaRegressiva(teste, x, count, 0);
		}
		
		return foundInitial;
	}
	
	public int buscaRegressiva(Map<Integer, Predicado> x, Estado estadoAtual, int contadorRegressivo, int tamanhoDoPlano)
	{
		if(contadorRegressivo == 0 && equals(estadoInicial, x)) return tamanhoDoPlano;
		if(contadorRegressivo == 0) return 0;

		Integer[] iPredicado = new Integer[x.size()+1];
		for(int i = 0; i < x.size()+1; i++) iPredicado[i] = 0;
		
		Integer[] keys = x.keySet().toArray(new Integer[0]);
		ArrayList<Integer[]>[] listas = new ArrayList[x.size()];
		for(int i = 0; i < x.size(); i++) listas[i] = estadoAtual.edge.get(keys[i]);
		
		while(iPredicado[x.size()] == 0)
		{
			int answer = tamanhoDoPlano;
			Map<Integer, Predicado> y = new HashMap<Integer, Predicado>();
			for(int i = 0; i < x.size(); i++)
			{
				Integer[] precondicoes = listas[i].get(iPredicado[i]);
				// Nao noop
				if(precondicoes.length > 1 || !precondicoes[0].equals(keys[i])) answer++;

				for(int j = 0; j < precondicoes.length; j++) y.put(precondicoes[j], estadoAtual.getPredicado(precondicoes[j]));
			}
		
			if((answer = buscaRegressiva(y, estadoAtual.pai, contadorRegressivo-1, answer)) != 0) return answer;
			
			iPredicado[0]++;
			for(int i = 0; i < x.size(); i++)
			{
				if(iPredicado[i] == listas[i].size())
				{
					iPredicado[i] = 0;
					iPredicado[i+1]++;
				}
			}
		}
		
		return 0;
	}
	
	public void print(Map<Integer, Predicado> predicados)
	{
		Set<Integer> keys = predicados.keySet();
		for(Integer key : keys)
		{
			Predicado p = predicados.get(key);
			System.out.println(p.getName() + p.getNameConst());
		}
	}
	
	public boolean equals(Map<Integer, Predicado> x, Map<Integer, Predicado> y)
	{
		Set<Integer> keys = x.keySet();
		for(Integer key : keys)	if(!y.containsKey(key))	return false;
		
		return true;
	}
}