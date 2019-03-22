public class Grafo
{
	public No vertices[];
	public int dimensao;
	public int capacidade;
	public int deposito;
	public int nCaminhao;
	
	public Grafo(int n, int d, int c, int dep, No v[])
	{
		this.dimensao = d;
		this.capacidade = c;
		this.deposito = dep;
		this.nCaminhao = n;
		this.vertices = v;
	}	
}