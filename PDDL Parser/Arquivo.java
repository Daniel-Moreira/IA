import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Arquivo
{
	public static final int LEITURA = 0;  
	public static final int ESCRITA = 1;  
	
	private FileInputStream fIS;
	private InputStreamReader iSR;
    private BufferedReader bR;
	private FileWriter fW;
	private PrintWriter pW;
	
	
	Arquivo(String nome, int tipo)
	{
		abreArquivo(nome, tipo);
	}
	
	public String leArquivo()
	{
		String linha = null;
		String answer = "";
		try
		{
			while ((linha = bR.readLine()) != null) answer += linha + '\n';
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return answer;
	}
	
	public void escreveArquivo(String texto)
	{
		pW.print(texto);
	}
	
	public void abreArquivo(String nome, int tipo)
	{
		try 
		{
			if (tipo == LEITURA)
			{
				fIS = new FileInputStream(nome);
				iSR = new InputStreamReader(fIS);
				bR = new BufferedReader(iSR);
			}
			else
			{
				fW = new FileWriter(nome);
				pW = new PrintWriter(fW);
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
			fechaArquivo();
		}
	}
	
	public void fechaArquivo()
	{
	    try
	    {
			if (fIS != null) fIS.close();
			if (iSR != null) iSR.close();
			if (bR != null) bR.close();
			if (fW != null) fW.close();
			if (pW != null) pW.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}