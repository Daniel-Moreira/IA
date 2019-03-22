import java.util.*;
	
public class BuscaProfundidade extends BuscaStrategy
{
	public BuscaProfundidade(int v[], int len, int max)
	{
		this.v = v;
		this.len = len;
		MAX = max;
		noInicial = new No(v, len);
	}
	
	public String getString()
	{
		return "*** Busca em Profundidade ***";
	}
	
	public No busca()
	{
		explorado.clear();
		return buscaRecursiva(noInicial);
	}
	
	private No buscaRecursiva(No no)
	{
		No novo;
		No resp = null;
		int i, j, custo = no.custo, qtd;
		
		explorado.put(no.estadoRe, no);

		if(custo > MAX) return null;
		if(no != null) if(no.noObjetivo()) return no;
		
		if (no.lO == no.OESTE) qtd = no.qtdOeste;
		else qtd = no.qtdLeste;
			
		while(no.temFilho(qtd))
		{
			novo = no.geraProxFilho(qtd);
			if (!explorado.containsKey(novo.estadoRe)) resp = buscaRecursiva(novo);
			if(resp != null) if(resp.noObjetivo()) return resp; 
		}
		
		return null;
	}
}
