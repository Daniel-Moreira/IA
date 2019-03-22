import java.util.LinkedList;
import java.util.Random;

class BuscaLocal
{
	public int CAPACIDADE;
	
	public BuscaLocal(int cap)
	{
		CAPACIDADE = cap;
	}
	
	public LinkedList<Rota> SA(LinkedList<Rota> r)
	{
		double Temperatura = 20;
		double bestTemp = 0.05;
		
		int melhorCusto = calcCusto(r);
		LinkedList<Rota> melhorSolucaoGlobal = clona(r);
		LinkedList<Rota> melhorSolucaoLocal = clona(r);
		while(Temperatura > 1)
		{
			LinkedList<Rota> solucaoAtual = clona(melhorSolucaoLocal);
			solucaoAtual = escolheAlgoritmo(solucaoAtual, 0);
			int custo = calcCusto(solucaoAtual);
			
			if(custo < melhorCusto)
			{
				melhorSolucaoLocal = clona(solucaoAtual);
				melhorSolucaoGlobal = clona(solucaoAtual);
				melhorCusto = calcCusto(melhorSolucaoLocal);
				IntraRoute ir = new IntraRoute(CAPACIDADE);
				for(int i = 0; i < melhorSolucaoLocal.size(); i++) ir.twoOpt(melhorSolucaoLocal.get(i));
			}else if(Math.random() < Math.exp(custo-melhorCusto/Temperatura)) melhorSolucaoLocal = clona(solucaoAtual);
			
			Temperatura *= 1-bestTemp;
		}
		return melhorSolucaoGlobal;
	}

	//escolhe o algoritmo que será rodado na solucao s
	public LinkedList<Rota> escolheAlgoritmo(LinkedList<Rota> rotas, int alg)
	{
		Random rd = new Random();
		int a = rd.nextInt(2);
		int b = 0;
		
		//intra-rotas
		if(a == 0)
		{		
			b = rd.nextInt(rotas.size());
			int size = rotas.get(b).vertice.size();
			if(size <= 3) return rotas;
			
			//i e j serão usados como invervalos nessa rota
    		int i = rd.nextInt(size-2)+1;
    		int j = rd.nextInt(size-2)+1;
    		while(i == j) j = rd.nextInt(size-2)+1;
    		
    		//agora eh qual dentre os algoritmos intra-rotas que sera executado

    		a = rd.nextInt(4);
    		IntraRoute ir = new IntraRoute(CAPACIDADE);
    		if(alg == 3) a = 3;
			switch(a)
			{
				case 0:
					int melhorCusto = calcCusto(rotas);
					LinkedList<Rota> melhorRota = rotas;
					boolean bestCostFound;
					do
					{
						bestCostFound = false;
						for(i = 1; i < rotas.get(b).vertice.size()-2 && !bestCostFound; i++)
						{
							for(j = i+1; j < rotas.get(b).vertice.size()-1 && !bestCostFound; j++)
							{
								LinkedList<Rota> r = clona(melhorRota);
								ir.move(r.get(b), i , j);
								int custo = calcCusto(r);
								if(custo < melhorCusto)
								{
									melhorRota = r;
									bestCostFound = true;
									melhorCusto = custo;
								}
							}
						}
					} while(bestCostFound);
					rotas = melhorRota;
					break;
				case 1:
					ir.swap(rotas.get(b), i, j); 
					break;
				case 2:
					ir.inverte(rotas.get(b), i , j);
					break;
				case 3:
					ir.twoOpt(rotas.get(b));
					break;
			}
		}
		else
		//inter-rotas
		{
			//i e j nesse caso serao quais rotas sao modificadas
			int i;
	    	int j;
			
			a = rd.nextInt(3);
			InterRoute ir = new InterRoute(CAPACIDADE);
			int melhorCusto = calcCusto(rotas);
			LinkedList<Rota> melhorRota = rotas;
			boolean bestCostFound;
			do
			{
				bestCostFound = false;
				for(i = 0; i < rotas.size() && !bestCostFound; i++)
				{
					for(j = 0; j < rotas.size() && !bestCostFound; j++)
					{
						LinkedList<Rota> r = clona(melhorRota);
						switch(a)
						{
							case 0:
								if(i != j) ir.oneZero(r.get(i), r.get(j));
								break;
							case 1:
								if(i != j) ir.oneOne(r.get(i), r.get(j));
								break;
							case 2:
								if(i != j) ir.twoOpt(r.get(i), r.get(j));
								break;
						}
						int custo = calcCusto(r);
						if(custo < melhorCusto)
						{
							melhorRota = r;
							bestCostFound = true;
							melhorCusto = custo;
						}
					}
				}
			} while(bestCostFound);
			rotas = melhorRota;
		}
		return rotas;
	}
	
	/// Metodos Auxiliares
	
	// Encontra o melhor lugar para se colocar um nó
	public int indiceMelhorLugar(No no, Rota r)
	{
		int indice = 0;	
		int distMin = Integer.MAX_VALUE;
		
		for (int i = 1; i < r.vertice.size(); i++)
		{
			int distTotal = r.custo;
			No anterior = r.vertice.get(i-1);
			No atual = r.vertice.get(i);
			
			distTotal += no.distEucl(anterior);
			distTotal += no.distEucl(atual);
			distTotal -= atual.distEucl(anterior);
			
			if(distTotal < distMin)
			{
				distMin = distTotal;
				indice = i;
			}
		}
		return indice;
	}
	
	public LinkedList<Rota> clona(LinkedList<Rota> r)
	{
		LinkedList<Rota> r2 = new LinkedList<Rota>();
		for(int i = 0; i < r.size() ; i++)
		{
			Rota rota = r.get(i).clona();
			r2.add(rota);
		}
		return r2;
	}
	
	public int calcCusto(LinkedList<Rota> r)
	{
		int custo = 0;
		for(int i= 0; i < r.size(); i++) custo += r.get(i).custo;
		return custo;
	}
}