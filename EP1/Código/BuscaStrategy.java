import java.util.*;
	
public abstract class BuscaStrategy
{ 
	public Hashtable<String, No> explorado = new Hashtable <String, No>();
	public Hashtable<String, No> borda = new Hashtable <String, No>();
	public No noInicial;
	public Queue<No> fila;
	public int v[];
	public int len;
	public int MAX;

	public abstract No busca();
	public abstract String getString();
}
