package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
	
public class BuscaA
{
	private Domain domain;
	private Problem problem;
	private Map<Integer, Estado> explorado;
	private Map<Integer, Estado> borda;
	private PriorityQueue<Estado> fila;
	private Heuristica h;

	public BuscaA(Domain d, Problem p)
	{
		h = new Soma(d, p);
		domain = d;
		problem = p;
		explorado = new HashMap<Integer, Estado>();
		borda = new HashMap<Integer, Estado>();
		fila = new PriorityQueue<Estado>(32, new Comparator<Estado>()
		{
        	public int compare(Estado x, Estado y)
			{
				if (x.getCost()+x.estimado <= y.getCost()+y.estimado) return -1;
				else return 1;
			}
		});
	}
	
	public Estado busca()
	{
		Estado estadoCorrente = problem.geraEstadoInicial();
		//System.out.println(h.f(estadoCorrente));
		adicionaEstado(estadoCorrente, estadoCorrente.hashCode());

		do
		{
			estadoCorrente = (Estado) fila.remove();
			int hash = estadoCorrente.hashCode();
			borda.remove(hash);
			explorado.put(hash, estadoCorrente);
			
			// Quantidade de estados a n passos
			/*if(estadoCorrente.getCost() == 20)
			{
				System.out.println(fila.size()+1);
				return problem.geraEstadoInicial();
			}*/

			if(problem.isObjective(estadoCorrente)) return estadoCorrente;
			
			LinkedList<Estado> filhos = domain.geraSucessores(estadoCorrente, false);
			while(filhos.size() > 0)
			{
				Estado e = filhos.remove();
				hash = e.hashCode();
				if(borda.containsKey(hash)) borda.get(hash).atualizaCusto(e);
				else if(!explorado.containsKey(hash)) adicionaEstado(e, hash);
			}
		} while(fila.size() > 0);
		
		return null;
	}
	
	public void adicionaEstado(Estado x, int key)
	{
		x.estimado = h.f(x);
		//System.out.println(x.estimado);
		fila.add(x);
		borda.put(key, x);
	}
	
	public int estadosGerados()
	{
		return explorado.size()+borda.size();
	}
	
	public int estadosVisitados()
	{
		return explorado.size();
	}
}
