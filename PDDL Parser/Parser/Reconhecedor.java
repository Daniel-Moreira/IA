package Parser;

import Parser.Excecoes.*;
import Parser.Dominio.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public abstract class Reconhecedor
{
	public Domain domain;
	protected String texto;
	protected int indexTexto;
	
	protected abstract void body() throws Exception;
	protected abstract String getName();
	protected abstract void setName(String nome);
	protected abstract VarConst checkVarConst(String nome) throws Exception;
	
	// Expressao exp
	public void reconhecer()
	{
		try
		{
			clause("(");
			clause("define");
			clause("(");
			clause(getName());
			
			String nome = name();
			if (nome.length() == 0) throw new InvalidName(nome, indexTexto);
			setName(nome);
			
			clause(")");
			body();
			clause(")");
		} catch (SymbolNotExpected sNE)
		{
			System.err.println(sNE.getMessage());
		} catch (InvalidName iN)
		{
			System.err.println(iN.getMessage());
		} catch (TypeError tE)
		{
			System.err.println(tE.getMessage());
		} catch (ConstantVariableError cVE)
		{
			System.err.println(cVE.getMessage());
		} catch (PredicateError pE)
		{
			System.err.println(pE.getMessage());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Reconhece clausulas constantes da linguagem
	protected void clause(String constant) throws SymbolNotExpected
	{
		descarta();
		int tamanho = constant.length();
		String clause = texto.substring(indexTexto, indexTexto+tamanho);
		if (!clause.equals(constant)) throw new SymbolNotExpected(clause, constant);
		indexTexto += tamanho;
	}
	
	// Reconhecimento de nomes
	protected String name()
	{
		descarta();
		String nome = "";
		char c = texto.charAt(indexTexto);
		// Primeira caracter eh uma letra
		if (!((c  >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '?')) return nome;
		for(; ((c  >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '-' || c == '_' || (c >= '0' && c <= '9') || c == '?'); c = texto.charAt(++indexTexto)) nome += c;
		
		return nome;
	}
	
	// Reconhece variaveis/constantes - tipo
	protected Map<Integer, VarConst> getVarConstTipo(boolean isVariable) throws Exception
	{
		Map<Integer, VarConst> v = new HashMap<Integer, VarConst>();
		// Le no minimo uma tupla (variaveis, tipo) 
		do
		{
			LinkedList<String> variaveis = getVarConst(isVariable);
			
			// Verifica se possui o divisor entre variavel e tipo
			clause("-");
			
			String tipo = getType();
			while(variaveis.size() > 0)
			{
				VarConst novaV = new VarConst(variaveis.remove(), tipo);
				v.put(novaV.hashCode(), novaV);
			}
			descarta();
		} while(texto.charAt(indexTexto) != ')');
		
		return v;
	}
	
	// Le variaveis (exemplo: ?x) ou objetos
	protected LinkedList<String> getVarConst(boolean isVariable) throws Exception
	{
		LinkedList<String> variaveis = new LinkedList<String>();
		String variavel;
		// Le no minimo uma variavel 
		for(variavel = name(); 	!variavel.equals(""); variavel = name()) 
		{	
			if(isVariable && variavel.charAt(0) != '?') throw new SymbolNotExpected();
			variaveis.add(variavel);
		}
		if (variaveis.size() == 0) throw new ConstantVariableError("It's necessary to declare at least one constant/variable!");
		
		return variaveis;
	}
	
	// Le o tipo definido para as constantes/variaveis lidas e verifica se o tipo foi declarado 
	protected String getType() throws Exception
	{
		String tipo = name();
		
		if (tipo.equals("")) throw new ConstantVariableError("It's necessary to declare at least one type for the constants/variables!");
		if (!domain.hasType(tipo)) throw new TypeError("Type (" + tipo + ") not declare at the clause :types!");
		
		return tipo;
	}
	
	protected LinkedList<Predicado> pre(int positivo, boolean var) throws Exception
	{
		clause("(");
		String lookAhead = name();
		LinkedList<Predicado> formulas = new LinkedList<Predicado>();
		if(lookAhead.equals("and"))
		{
			do
			{
				formulas.add(pre(positivo, var).remove());
				descarta();
			} while(texto.charAt(indexTexto) != ')');
		}
		else if(lookAhead.equals("not")) formulas.add(pre(Predicado.NOT, var).remove());
		else 
		{
			//if (!domain.hasPredicado(lookAhead)) throw new PredicateError(lookAhead);
			
			// Le variaveis
			LinkedList<String> variaveis = getVarConst(var);
			Map<Integer, VarConst> hashV = new HashMap<Integer, VarConst>();
			while(variaveis.size() > 0)
			{
				VarConst v;
				String nomeV = variaveis.remove();
				// Checa se as variaveis/constantes foram declaradas
				v = checkVarConst(nomeV);
				
				hashV.put(v.hashCode(), v);
			}
			Predicado p = new Predicado(lookAhead, hashV, positivo);
			formulas.add(p);
		}
		
		clause(")");
		
		return formulas;
	}
	
	// Descarta comentarios e caracteres inuteis para a linguagem
	protected void descarta()
	{
		char c;
		boolean commentary = false;
		while(((c = texto.charAt(indexTexto)) == ' ' || c == '\t' || c == '\n'|| c == ';' || commentary))
		{
			if(commentary && c == '\n') commentary = false;
			if(c == ';') commentary = true;
			indexTexto++;
		}
	}
}