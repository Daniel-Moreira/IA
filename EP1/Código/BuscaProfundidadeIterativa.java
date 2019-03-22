import java.util.*;
	
public class BuscaProfundidadeIterativa extends BuscaStrategy
{
	private boolean completa;
	
	public BuscaProfundidadeIterativa(int v[], int len, int max)
	{
		this.v = v;
		this.len = len;
		MAX = max;
		completa = false;
		noInicial = new No(v, len);
	}
	
	public String getString()
	{
		return "*** Busca em Profundidade Iterativa ***";
	}
	
	public No busca()
	{
		No no = null;
		completa = false;
		for(int i = 0; !completa && no == null; i++)
		{
			completa = true;
			explorado.clear();
			No aux = new No(v, len);
			no = buscaRecursiva(aux, 0, i);
		}
		return no;
	}
	
	private No buscaRecursiva(No no, int profundidade, int limiteP)
	{
		No novo;
		No resp = null;
		int i, j, custo = no.custo, qtd;
		
		explorado.put(no.estadoRe, no);

		if(custo > MAX) return null;
		if(no != null) if(no.noObjetivo()) return no;
		if(profundidade == limiteP)
		{
			completa = false;
			return null;
		}
		
		if (no.lO == no.OESTE) qtd = no.qtdOeste;
		else qtd = no.qtdLeste;
			
		while(no.temFilho(qtd))
		{
			novo = no.geraProxFilho(qtd);
			if (!explorado.containsKey(novo.estadoRe)) resp = buscaRecursiva(novo, profundidade+1, limiteP);
			if(resp != null) if(resp.noObjetivo()) return resp; 
		}
		
		return null;
	}
}
