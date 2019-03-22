import java.util.*;

class Main
{
	static public void printRota(LinkedList<Rota> rotas, Arquivo arq, String nome, double tempo)
	{
		int j = 1;
		int custoT = 0;
		System.out.println("\n Solucao: \n");
		arq.escreveArquivo(nome + "\n");
		while(rotas.size() != 0)
		{
			System.out.print("Rota  #" + j + ": ");
			arq.escreveArquivo("Rota  #" + j++ + ": ");
			Rota teste = (Rota) rotas.remove();
			for(int k = 0; k < teste.vertice.size(); k++)
			{
				System.out.print(teste.vertice.get(k).id + " ");
				arq.escreveArquivo(teste.vertice.get(k).id + " ");
			}
			System.out.print("custo: " + teste.custo);
			arq.escreveArquivo("custo: " + teste.custo);
			System.out.print(" demanda: " + teste.demandaSuprida + "\n");
			arq.escreveArquivo(" demanda: " + teste.demandaSuprida + "\n");
			custoT += teste.custo;
		}
		
		System.out.println("Custo Total: " + custoT);
		arq.escreveArquivo("Custo Total: " + custoT +"\n");
		System.out.println("Tempo " + tempo);
		arq.escreveArquivo("Tempo " + tempo + "\n\n");
		System.out.println("Pronto!");
		
	}
	
	private static String arquivos[] = {"A-n32-k5.vrp", "A-n33-k5.vrp", "A-n33-k6.vrp", "B-n31-k5.vrp", "B-n34-k5.vrp", "B-n35-k5.vrp", "P-n16-k8.vrp", "P-n19-k2.vrp", "P-n20-k2.vrp"};
	
    public static void main(String args[])
    {
		if(args.length != 2)
		{
			System.out.println("Dois parametros sao necessarios.");
			System.out.println("Ex.: Main [arquivo de entrada] [arquivo de saida]");
			return;
		}
		
		String arquivoEntrada = args[0];
		String arquivoSaida = args[1];
		Arquivo arqS = new Arquivo(arquivoSaida);
    	Grasp g = new Grasp();
		
		int tamanho = 1;
		if(arquivoEntrada.equals("all"))
		{
			arquivoEntrada = "Entradas\\";
			tamanho = arquivos.length;
		}
		arqS.abreArquivo();
		
		for(int i = 0; i < tamanho; i++)
		{
			Arquivo arq = new Arquivo(arquivoEntrada);
			if(tamanho > 1) arq = new Arquivo(arquivoEntrada+arquivos[i]);

			Grafo grafo = arq.leArquivo();	
			Tempo tp = new Tempo();	

			System.out.println("cap = " + grafo.capacidade);
			System.out.println("mtruck = " + grafo.nCaminhao);
			System.out.println("dimension = " + grafo.dimensao);
			System.out.println("dep = " + grafo.deposito+"\n");
			tp.start();
			
			LinkedList<Rota> solucao = g.executa(grafo, 530);
			
			tp.stop();
			printRota(solucao, arqS, arq.nome, tp.getTempo());
		}
		arqS.fechaArquivo();
    }
}
