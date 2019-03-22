import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Arquivo
{
	private File file;
	private String nome;
	
	private FileWriter arq = null;	
	PrintWriter gravar = null;
	
	private int algoritmo;
	private int quantidade;
	private int max;		
	private int heuristica = 1;
	
	Arquivo(String nome)
	{
		this.nome = nome;
	}
	
	//sobreposicao de construtor
	Arquivo()
	{
		this.pegaNome();
	}				
	
	void pegaNome(){
		System.out.println("Digite o nome do arquivo para ser lido ou 0 para sair:");
		Scanner sc = new Scanner(System.in);
		String nome = "";
		while(nome == "")
		{
			nome = sc.next();
			if(nome.equals("0")) System.exit(0);
			if(!nome.endsWith(".txt"))
			{
				nome = "";
				System.out.println("Digite o nome de arquivo válido para ser lido ou 0 para sair:");
			}
		}
		sc.close();
		this.nome = nome;
	}
	
	int getAlgoritmo()
	{
		return this.algoritmo;
	}
	
	int getMax()
	{
		return this.max;
	}
	
	int getQuant()
	{
		return this.quantidade;
	}

	int getHeuristica()
	{
		return this.heuristica;
	}
	
	int[] leArquivo()
	{
		int vetor[];
		file = new File(nome);
		 Scanner scanner = null;
		 
		try 
		{
		    scanner = new Scanner(file);
		    scanner.nextLine();
		    System.out.println("Abrindo arquivo '"+nome+"'\n");
		    
		    this.algoritmo = scanner.nextInt();
		    if(this.algoritmo == 5 || this.algoritmo == 6) //busca gulosa ou heuristica
		    	this.heuristica = scanner.nextInt();	   //le o proximo int referente a heuristica escolhida (1, 2 , 3)
		    if(this.heuristica > 3){						   //modo bem simples de escolher gerenciar o default
		    	System.err.println("heuristica nao pode ser maior que 3");
		    	System.exit(0);
		    }
		    this.quantidade = scanner.nextInt();
		    this.max = scanner.nextInt();

		    vetor = new int[quantidade];		 
		    for(int i = 0; i < quantidade; i++)
		    	vetor[i] = scanner.nextInt();
		    
		    return vetor;
		}
		catch(java.util.NoSuchElementException e)
		{
			System.err.println("A quantidade de elementos passado como parametro é maior do que o escrito no arquivo");
		}
		catch (FileNotFoundException e) 
		{
			System.err.println("Nao existe um arquivo com o nome: "+this.nome);
		} finally 
		{
		    if (scanner != null) 
		    {
			    scanner.close();
			}
		}
		return null;		
	}
	
	void escreveArquivo(String st)
	{
		gravar.println(st);
	}
	
	void abreArquivo()
	{
		try 
		{
			arq = new FileWriter(nome);
			this.gravar = new PrintWriter(arq);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
			fechaArquivo();
		}
	}
	
	void fechaArquivo()
	{
		if (arq != null) 
		{
		    try
		    {
				arq.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
	    }
	}
}