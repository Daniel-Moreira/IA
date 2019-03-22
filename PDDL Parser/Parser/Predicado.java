package Parser;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Iterator;
import java.util.Iterator;

public class Predicado
{
	public static final int POS = 0;
	public static final int NOT = 1;
	
	private String nome;
	private Map<Integer, VarConst> variavel;
	private int negacao;

	public Predicado(String nome, Map<Integer, VarConst> v)
	{
		this.nome = nome;
		variavel = v;
		negacao = POS;
	}
	
	public Predicado(String nome, Map<Integer, VarConst> v, int neg)
	{
		this.nome = nome;
		variavel = v;
		negacao = neg;
	}
	
	public String getName()
	{
		return nome;
	}
	
	public String getNameConst()
	{
		String nome = "";
		String[] nome2 = new String[variavel.size()]; 
		Set<Integer> keys = variavel.keySet();
		int i = 0;
		for(Integer key : keys)	nome2[i++] = variavel.get(key).getName();
		Arrays.sort(nome2);
		for(i = 0; i < variavel.size(); i++) nome += nome2[i];
		
		return nome;
	}
	
	public boolean isNegative()
	{
		return negacao==NOT;
	}
	
	// Altera o predicado variavel para um predicado de constantes
	public Predicado changeInstacia()
	{
		Set<Integer> keys = variavel.keySet();
		Map<Integer, VarConst> constantes = new HashMap<Integer, VarConst>();
		for(Integer key : keys)
		{
			VarConst v = variavel.get(key).invert();
			constantes.put(v.hashCode(), v);
		}
		return new Predicado(nome, constantes, negacao);
	}
	
	public int hashCode()
	{	
		/// Hash 1
		Integer[] keys = variavel.keySet().toArray(new Integer[0]);
		Arrays.sort(keys);
		
		return Objects.hash(nome, Arrays.toString(keys));
		
		/// Hash 2 (Prime)
		/*long hash = 1;
		Set<Integer> keys = variavel.keySet();
		for(Integer key : keys) hash *= key;
		
		return Objects.hash(nome, hash);*/
    }
}