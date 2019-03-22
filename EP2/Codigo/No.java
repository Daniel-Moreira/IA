public class No
{
	public int id;
	public int x;
	public int y;
	public int demanda;
	
	public No(int id, int x, int y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.demanda = 0;
	}	
	
	// Calcula a distancia entre esse e outro vertice
	public int distEucl(No b)
	{
		int dist = 0;
		int x = (b.x-this.x);
		int y = (b.y-this.y);
		dist = Math.round((float)Math.sqrt((x*x)+(y*y)));
		
		return dist;
	}
}