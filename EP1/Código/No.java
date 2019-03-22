import java.util.*;

public class No
{
	public static final int LESTE = 0;
	public static final int OESTE = 1;
	
	// Array de pessoas a oeste
		int oeste[];
		int qtdOeste;
	// Array de pessoas a leste
		int leste[];
		int qtdLeste;
	// Barco leste ou oeste
		int lO;
	
	// Resume o estado
		String estadoRe;
	
	// Pessoas transferidas
		String acao;
	
	int custo;
	int geraFilhoI;
	int geraFilhoJ;
	No pai;
	
	public No(int v[], int len)
	{
		inicializa(v, null, No.OESTE, len, 0, "", 0, null);
	}
	
	public No(int l[], int r[], int lO, int lenL, int lenR, String acao, int c, No p)
	{
		inicializa(l, r, lO, lenL, lenR, acao, c, p);
	}
	
	private void inicializa(int l[], int r[], int lO, int lenL, int lenR, String acao, int c, No p)
	{
		if(l != null) Arrays.sort(l, 0, lenL);
		if(r != null) Arrays.sort(r, 0, lenR);
		
		oeste = new int[lenL];
		leste = new int[lenR];
		for(int i = 0; i < lenL; i++) oeste[i] = l[i];
		for(int i = 0; i < lenR; i++) leste[i] = r[i];
		qtdOeste = lenL;
		qtdLeste = lenR;
		this.lO = lO;
		this.acao = acao;
		custo = c;
		pai = p;
		
		estadoRe = "";
		for(int i = 0; i < lenL; i++) estadoRe += String.valueOf(l[i]) + " ";
		estadoRe += "- ";
		for(int i = 0; i < lenR; i++) estadoRe += String.valueOf(r[i]) + " ";
		estadoRe += "- " + String.valueOf(lO);
		
		geraFilhoI = 0;
		geraFilhoJ = 0;
	}
	
	public boolean noObjetivo()
	{
		if(qtdOeste == 0) return true;
		return false;
	}
	
	public boolean temFilho(int qtd)
	{
		if(geraFilhoI == qtd) return false;
		return true;
	}

	public No geraProxFilho(int qtd)
	{
		No novo;
		String acao;
		int custo = 0, k, o = 0, l = 0, qtdT, qtdC, troca[], copia[];
		
		if (lO == OESTE)
		{
			troca = oeste;
			copia = leste;
			qtdT = qtdOeste;
			qtdC = qtdLeste;
		}
		else
		{
			troca = leste;
			copia = oeste;
			qtdT = qtdLeste;
			qtdC = qtdOeste;
		}
		
		int vetorT[] = new int[qtdT+qtdC];
		int vetorC[] = new int[qtdT+qtdC];
		
		for(k = 0; k < qtdT; k++)
		{
			if(k != geraFilhoI && k != geraFilhoJ) vetorT[o++] = troca[k];
			else
			{
				vetorC[l] = troca[k];
				if(vetorC[l] > custo) custo = vetorC[l];
				l++;
			}
		}
		for(k = 0; k < qtdC; k++) vetorC[l++] = copia[k];
		
		qtdT--;
		qtdC++;
		custo += this.custo;
		acao = String.valueOf(troca[geraFilhoI]) + " ";
		if (geraFilhoJ != geraFilhoI)
		{
			qtdT--;
			qtdC++;
			acao += String.valueOf(troca[geraFilhoJ]) + " ";
		}
		
		if (lO == OESTE) novo = new No(vetorT, vetorC, LESTE, qtdT, qtdC, acao + ">>", custo, this);
		else novo = new No(vetorC, vetorT, OESTE, qtdC, qtdT, acao + "<<", custo, this);
		
		calculaProximoFilho(qtd);
		
		return novo;
	}
	
	private void calculaProximoFilho(int qtd)
	{
		geraFilhoJ++;
		if(geraFilhoJ == qtd)
		{
			geraFilhoI++;
			// Gera filhos transferindo uma pessoa
			geraFilhoJ = geraFilhoI;
		}
	}
}