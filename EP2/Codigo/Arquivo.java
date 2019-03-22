import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Arquivo
{
	private File file;
	public String nome;
	
	private FileWriter arq = null;	
	PrintWriter gravar = null;
	
	Arquivo(String nome)
	{
		this.nome = nome;
	}
	
	Grafo leArquivo()
	{
		Grafo g = null;
		No grafo[];
		int mtruck, dimension, capacity, deposito;
		file = new File(nome);
		Scanner scanner = null;
		
		try 
		{
		    scanner = new Scanner(file);
		    System.out.println("Abrindo arquivo '" + nome + "'\n");
		    
		    System.out.println(scanner.nextLine());
		    
		    String st = scanner.nextLine();
		    
		    String a[] = st.split(":");
		    a = a[2].split(",");
		    st = a[0].replace(" ", "");
		    
			mtruck = Integer.parseInt(st);
		    scanner.nextLine();	
		    
		    st = scanner.nextLine();
		    a = st.split(":");
		    st = a[1].replace(" ","");
		   
		    dimension = Integer.parseInt(st);	
		    scanner.nextLine();
		    
		    st = scanner.nextLine();
		    a = st.split(":");
		    st = a[1].replace(" ","");
		   
		    capacity = Integer.parseInt(st);	
		    scanner.nextLine();
		    
			int id, x, y;
		    grafo = new No[dimension];
		    for(int i = 0; i < dimension; i++)
			{
		    	id = scanner.nextInt();
		    	x = scanner.nextInt();
				y = scanner.nextInt();
				grafo[i] = new No(id, x, y);
		    }
		    scanner.nextLine();
		    scanner.nextLine();
		    
		    for(int i = 0; scanner.hasNextInt(); i++)
			{
		    	id = scanner.nextInt();
				grafo[id-1].demanda = scanner.nextInt();
		    }
		    
		    scanner.nextLine();
		    scanner.nextLine();
			deposito = scanner.nextInt()-1;
			
			g = new Grafo(mtruck, dimension, capacity, deposito, grafo);
			
			return g;
		}
		catch(java.util.InputMismatchException e)
		{
			System.err.println(e.toString());
		}
		catch (FileNotFoundException e) 
		{
			System.err.println("Nao existe um arquivo com o nome: ["+this.nome+"]");
		}finally
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
		gravar.print(st);
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