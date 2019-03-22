import java.util.*;
import java.io.*;

public class Grasp
{
	private int density[][];

	public LinkedList<Rota> executa(Grafo grafo, int NUM_MAX)
	{
		// Cada rota tera uma sequencia de nos visitados e teremos n rotas no final (numero de veiculos utilizados)
		LinkedList<Rota> melhorSolucao = null;
		int melhorCusto = Integer.MAX_VALUE;
		BuscaLocal bs = new BuscaLocal(grafo.capacidade);
		
		for(int i = 0; i < NUM_MAX; i++)
		{
			// fase de construcao
				calculaMatrizDensidade(grafo);
				LinkedList<Rota> solucao = new LinkedList<Rota>();
				Rota r = constroiRota(grafo);
				solucao = dividiRota(r, grafo.capacidade, grafo.dimensao);
				solucao = VMP(solucao, grafo.vertices[grafo.deposito], grafo.capacidade);

			// busca local
				solucao = bs.SA(solucao);
				int custo = bs.calcCusto(solucao);
				if(custo < melhorCusto)
				{
					melhorSolucao = solucao;
					melhorCusto = custo;
				}
		}

		return melhorSolucao;
	}
	
	private LinkedList<Rota> dividiRota(Rota r, int capacidade, int length)
	{
		LinkedList<Rota> rotas = new LinkedList<Rota>();
		LinkedList<No> v = r.vertice;

		while(v.size() != 0)
		{
			Rota rCriada = new Rota();
			int cap = capacidade;
			for(int i = 0; i < v.size(); i++)
			{  
				No atual = (No) v.get(i);
				if(cap >= atual.demanda)
				{
					rCriada.add(atual, 0);
					v.remove(atual);
					cap -= atual.demanda;
					i--;
				}
			}
			rotas.add(rCriada);
		}
		
		return rotas;
	}
	
	// Constroi uma rota contendo todos os vertices ordenados pela densidade
	private Rota constroiRota(Grafo g)
	{
		LinkedList<No> v = new LinkedList<No>(Arrays.<No>asList(g.vertices));
		int length = g.dimensao;
		No depo = (No) v.remove(g.deposito);
		Rota r = new Rota();
		
		int ultimoVertice = g.deposito;
		for(int i = 1; i < length; i++)
		{
			double p = Math.random();
			
			// Probabilidade de adicionar algum vertice sem contar o criterio de densidade
			if(p <= 0.05)
			{
				int n = (int) (Math.random()*(length-(++i)));
				r.add((No) v.remove(n), 0);
				ultimoVertice = n;
			}
			if(v.size() == 0) break;
			
			int maiorDensidade = -1;
			int index = 0;
			for(int j = 0; j < length-i; j++)
			{
				No atual = (No) v.get(j);
				int id = atual.id-1;
				if(density[ultimoVertice][id] > maiorDensidade)
				{
					maiorDensidade = density[ultimoVertice][id];
					index = j; 
				}
			}

			r.add((No) v.remove(index), 0);
			ultimoVertice = index;
		}
		
		return r;
	}

	
	private void calculaMatrizDensidade(Grafo g)
	{
		int p = 2, k = 4;
		No v[] = g.vertices;
		int depot = g.deposito;
		int cap = g.capacidade;
		int dimension = g.dimensao;
		density = new int[dimension][dimension];
		
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				if(i == j) density[i][j] = 0;
				else density[i][j] = Math.round((float) (Math.pow(Math.abs(cap-(v[i].demanda + v[j].demanda)), p)/(Math.pow(v[i].distEucl(v[j]), k))));
			}
		}
	}
	
	// Heuristica Vizinho Mais Proximo
	private LinkedList<Rota> VMP(LinkedList<Rota> rotas, No deposito, int capacidade)
	{
		LinkedList<Rota> resultado = new LinkedList<Rota>();
		
		while(rotas.size() != 0)
		{
			Rota r = (Rota) rotas.remove();
			LinkedList<No> v = r.vertice;
			while(v.size() != 0)
			{
				Rota novaRota = new Rota();
				int cap = capacidade;
				No anterior = deposito;
				novaRota.add(deposito, 0);
				
				while(v.size() != 0)
				{
					No atual = verticeMaisProximo(v, anterior);
					if(cap >= atual.demanda)
					{
						cap -= atual.demanda;
						novaRota.add(atual, anterior.distEucl(atual));
						anterior = atual;
						v.remove(atual);
					}
					else break;
				}
				
				novaRota.add(deposito, anterior.distEucl(deposito));
				
				resultado.add(novaRota);
			}
		}
		
		return resultado;
	}
	
	// Encontra o vertice mais proximo de um dado vertice x
	private No verticeMaisProximo(LinkedList<No> v, No x)
	{
		No resp = v.get(0);
		int menorD = resp.distEucl(x);
		for(int i = 1; i < v.size(); i++)
		{
			int d = v.get(i).distEucl(x);
			if(d < menorD)
			{
				menorD = d;
				resp = v.get(i);
			}
		}
		
		return resp;
	}
}