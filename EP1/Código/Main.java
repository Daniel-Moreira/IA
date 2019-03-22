import java.util.*;
	
class Main
{

	public static BuscaStrategy escolheAlgoritmo(int algoritmo, int[] vetor, int quant, int max)
	{
		switch(algoritmo)
		{
			case 1:
				return (new BuscaLargura(vetor, quant, max));
			case 2:
				return (new BuscaProfundidade(vetor, quant, max));
			case 3:
				return (new BuscaProfundidadeIterativa(vetor, quant, max));
			case 4:
				return (new BuscaCustoUniforme(vetor, quant, max));
			case 5:
				return (new BuscaGulosa(vetor, quant, max));
			case 6:
				return (new BuscaA(vetor, quant, max));	
			default:
				System.out.println("Algoritmo invalido");
		}		
		return null;	
	}
	public static int imprimeTravessia(No no, int i, Arquivo saida)
	{
		int p = 0;
		
		if(no != null)
		{
			p = imprimeTravessia(no.pai, i+1, saida);
			System.out.println(no.acao);
			saida.escreveArquivo(no.acao);
		}
		else return i-1;

		return p;
	}
	
	public static void imprimeSolucao(No no, BuscaStrategy bs, long tempo)
	{
		Arquivo saida = new Arquivo("saida.txt");
		saida.abreArquivo();
		int p;
		if (no == null){
			System.out.println("Solucao Nao Encontrada\n");
			saida.escreveArquivo("Solucao Nao Encontrada\n");
		}
		else
		{
			saida.escreveArquivo(bs.getString());
			System.out.println("\n" + bs.getString());
			p = imprimeTravessia(no, 0, saida);
			System.out.printf("\n%d %d %d %d\n", no.custo, p, tempo, bs.explorado.size());
			saida.escreveArquivo(no.custo + "  " + p + "  " + tempo + "  " + bs.explorado.size());
		}
		saida.fechaArquivo();
	}
	
    public static void main(String argv[])
    {
		Arquivo arq = new Arquivo(); 	
    	int vetor[] = arq.leArquivo();	
    	
		Tempo tp = new Tempo();	
		if(arq.getHeuristica() != 1) 
			FuncoesH.setH(arq.getHeuristica());

		BuscaStrategy bs = escolheAlgoritmo(arq.getAlgoritmo(), vetor, arq.getQuant(), arq.getMax());

		if(bs != null)
		{
			No no = bs.busca();
			imprimeSolucao(no, bs, tp.getTempo());
		}
    }
}
