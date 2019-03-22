import java.util.*;

public class Rota
{
	public LinkedList<No> vertice;
	public int demandaSuprida;
	public int custo;
	
	public Rota()
	{
		vertice = new LinkedList<No>();
		custo = 0;
		demandaSuprida = 0;
	}
	
	public void add(No x, int dist)
	{
		vertice.add(x);
		demandaSuprida += x.demanda;
		custo += dist;
	}
	
	public void recalcula()
	{
		demandaSuprida = 0;
		custo = 0;
		for(int i = 0; i < vertice.size(); i ++)
		{
			No x = vertice.get(i);
			demandaSuprida += x.demanda;
			if(i == vertice.size()-1) break;
			else 
				custo += x.distEucl(vertice.get(i+1));
		}
	}
	
	// Calcula a distancia entre dois vertices
	public int distEucl(No a, No b)
	{
		int dist = 0;
		int x = (b.x-a.x);
		int y = (b.y-a.y);
		dist = Math.round((float)Math.sqrt((x*x)+(y*y)));
		
		return dist;
	}
	
	public Rota clona()
	{
		Rota rota = new Rota();
		for(int j = 0; j < this.vertice.size() ; j++){
			No novo = new No(this.vertice.get(j).id,this.vertice.get(j).x,this.vertice.get(j).y);
			novo.demanda = this.vertice.get(j).demanda;
			rota.add(novo, 0);
		}
		rota.demandaSuprida = this.demandaSuprida;
		rota.custo = this.custo;
		return rota;
	}
}