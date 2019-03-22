import java.util.LinkedList;
import java.util.Random;

class IntraRoute extends BuscaLocal
{
	public IntraRoute(int cap)
	{
		super(cap);
	}
	
	// Move um no de um lugar para o outro
	protected void move(Rota r, int i, int j)
	{
		if(i == 0 || j == 0 || i >= r.vertice.size()-1 || j >= r.vertice.size()-1 || i == j) return;
		
		No no = r.vertice.remove(i);
		r.vertice.add(j, no);
		r.recalcula();
	}
	
	// Troca dois nos de lugar
	protected void swap(Rota r, int i, int j)
	{
		No no1 = r.vertice.get(i);		
		No no2 = r.vertice.get(j);
		
		r.vertice.set(j, no1);
		r.vertice.set(i,no2);
		r.recalcula();
	}
	
	// Inverte um intervalo desejado
	protected void inverte(Rota r, int i, int j)
	{
		if(i == 0 || j == 0 || i >= r.vertice.size()-1 || j >= r.vertice.size()-1 || i == j) return;
		
		LinkedList<No> v = new LinkedList<No>();
		while(i <= j) v.add(r.vertice.remove(j--));
		r.vertice.addAll(i, v);
		r.recalcula();
	}
	
	protected void twoOpt(Rota r)
	{
		int bestCost = r.custo;
		int length = r.vertice.size();
		boolean bestCostFound;
		do
		{
			bestCostFound = false;
			for(int i = 1; i < length-1 && !bestCostFound; i++)
			{
				for(int k = i+1; k < length-1 && !bestCostFound; k++)
				{
					Rota novaR = r.clona();
					inverte(novaR, i, k);
					if (novaR.custo < bestCost)
					{
						bestCost = novaR.custo;
						r.vertice = novaR.vertice;
						r.custo = bestCost;
						r.demandaSuprida = novaR.demandaSuprida;
						bestCostFound = true;
					}
				}
			}
		} while(bestCostFound);
	}
}