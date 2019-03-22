import java.util.LinkedList;
import java.util.Random;

class InterRoute extends BuscaLocal
{
	private Random rd = new Random();
	
	public InterRoute(int cap)
	{
		super(cap);
	}
	
	// 1x0 pega um no de uma rota r1 e coloca na melhor posicao da rota r2
	protected void oneZero(Rota r1, Rota r2)
	{
		// retira um vertice da rota 1	
		int r1Length = r1.vertice.size();
		if(r1Length <= 2) return;
		
		Rota melhorR1 = r1;
		Rota melhorR2 = r2;
		int melhorCusto = Integer.MAX_VALUE;
		for(int i = 1; i < r1Length-1; i++)
		{
			Rota copiaR1 = r1.clona();
			Rota copiaR2 = r2.clona();
			No troca = copiaR1.vertice.get(i);
			
			if(copiaR2.demandaSuprida+troca.demanda > CAPACIDADE) continue;
			
			copiaR1.vertice.remove(i);
			copiaR2.vertice.add(indiceMelhorLugar(troca, copiaR2), troca);
			
			copiaR1.recalcula();
			copiaR2.recalcula();
			
			int custo = copiaR1.custo + copiaR2.custo;
			
			if(melhorCusto > custo)
			{
				melhorR1 = copiaR1;
				melhorR2 = copiaR2;
				melhorCusto = custo;
			}
		}
		
		r1.custo = melhorR1.custo;
		r1.demandaSuprida = melhorR1.demandaSuprida;
		r1.vertice = melhorR1.vertice;
		r2.custo = melhorR2.custo;
		r2.demandaSuprida = melhorR2.demandaSuprida;
		r2.vertice = melhorR2.vertice;
	}
	
	// oneOne retira o no de uma rota e coloca na outra, para as duas rotas.
	protected void oneOne(Rota r1, Rota r2)
	{
		// primeiro temos que saber os nos de maior impactos nas duas rotas
		int r1Length = r1.vertice.size();
		int r2Length = r2.vertice.size();
		if(r1Length <= 2 || r2Length <= 2) return;
		
		// retiramos esses nos das rotas
		Rota melhorR1 = r1;
		Rota melhorR2 = r2;
		int melhorCusto = Integer.MAX_VALUE;
		for(int i = 1; i < r1Length-1; i++)
		{
			for(int j = 1; j < r2Length-1; j++)
			{
				Rota copiaR1 = r1.clona();
				Rota copiaR2 = r2.clona();
				No trocar1 = copiaR1.vertice.get(i);
				No trocar2 = copiaR2.vertice.get(j);
				
				if(copiaR1.demandaSuprida+trocar2.demanda > CAPACIDADE || copiaR2.demandaSuprida+trocar1.demanda > CAPACIDADE) continue;
				
				copiaR1.vertice.remove(i);
				copiaR2.vertice.remove(j);
				// achamos o melhor lugar para colocarmos eles
				int indice1 = indiceMelhorLugar(trocar1, copiaR1);
				int indice2 = indiceMelhorLugar(trocar2, copiaR2);
				
				// colocamos eles nas rotas
				copiaR1.vertice.add(indice1, trocar2);
				copiaR2.vertice.add(indice2, trocar1);
				
				copiaR1.recalcula();
				copiaR2.recalcula();
				
				int custo = copiaR1.custo + copiaR2.custo;
				
				if(melhorCusto > custo)
				{
					melhorR1 = copiaR1;
					melhorR2 = copiaR2;
					melhorCusto = custo;
				}
			}
		}
		
		r1.custo = melhorR1.custo;
		r1.demandaSuprida = melhorR1.demandaSuprida;
		r1.vertice = melhorR1.vertice;
		r2.custo = melhorR2.custo;
		r2.demandaSuprida = melhorR2.demandaSuprida;
		r2.vertice = melhorR2.vertice;
	}
	
	protected void twoOpt(Rota r1, Rota r2)
	{
		int r1Length = r1.vertice.size();
		int r2Length = r2.vertice.size();
		if(r1Length <= 2 || r2Length <= 2) return;
		
		int i = 1 + rd.nextInt(r1Length-2);
		int j = 1 + rd.nextInt(r2Length-2);
		
		LinkedList<No> lista1 = new LinkedList<No>(r1.vertice.subList(i, r1Length));
		LinkedList<No> lista2 = new LinkedList<No>(r2.vertice.subList(j, r2Length));
		
		int demanda1 = 0;
		int demanda2 = 0;
		for(int k = 0; k < lista1.size(); k++) demanda1 += lista1.get(k).demanda;
		for(int k = 0; k < lista2.size(); k++) demanda2 += lista2.get(k).demanda;
		
		if(r1.demandaSuprida-demanda1+demanda2 > CAPACIDADE) return;
		if(r2.demandaSuprida-demanda2+demanda1 > CAPACIDADE) return;
		
		r1.vertice.subList(i, r1Length).clear();
		r2.vertice.subList(j, r2Length).clear();
		
		r2.vertice.addAll(lista1);
		r1.vertice.addAll(lista2);
		
		r1.recalcula();
		r2.recalcula();
	}
}