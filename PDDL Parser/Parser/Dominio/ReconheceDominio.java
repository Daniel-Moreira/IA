package Parser.Dominio;

import Parser.*;
import Parser.Dominio.*;
import Parser.Excecoes.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ReconheceDominio extends Reconhecedor
{
	private Acao acao;
	
	public ReconheceDominio(String menssagem)
	{
		domain = new Domain();
		texto = menssagem;
		indexTexto = 0;
	}
	
	protected String getName()
	{
		return "domain";
	}
	
	protected void setName(String nome)
	{
		domain.setNome(nome);
	}
	
	// Expressao exp
	protected void body() throws Exception
	{
		require_def();
		type_def();
		constants_def();
		predicates_def();
		actions_def();
	}
	
	// Aceita os requerimentos strips, typing e equality
	private void require_def() throws SymbolNotExpected
	{
		clause("(");
		clause(":requirements");
		
		do
		{
			clause(":");
			String req = name();
			if (!(req.equals("strips") || req.equals("typing") || req.equals("equality"))) throw new SymbolNotExpected();
			descarta();
		} while (texto.charAt(indexTexto) != ')');
		
		clause(")");
	}

	// Define os tipos
	private void type_def() throws Exception
	{
		clause("(");
		clause(":types");
		for(String nome = name(); !nome.equals(""); nome = name()) domain.adicionaTipo(nome);
		if (domain.sizeTipos() == 0) throw new TypeError();
		clause(")");
	}
	
	// Define as constantes
	private void constants_def() throws Exception
	{
		clause("(");
		if (!texto.substring(indexTexto, indexTexto+10).equals(":constants")) return;
		clause(":constants");
		
		LinkedList<String> constantes = new LinkedList<String>();
		do
		{
			// Le no minimo um nome de constante 
			for(String constante = name(); !constante.equals(""); constante = name()) constantes.add(constante);
			if (constantes.size() == 0) throw new ConstantVariableError("Name of the constant missing!");
			
			// Verifica se possui o divisor entre constante e tipo
			clause("-");
			
			String tipo = getType();
			while(constantes.size() > 0) domain.adicionaConstante(constantes.remove(), tipo);
			descarta();
		} while(texto.charAt(indexTexto) != ')');
		clause(")");
		
		// Parenteses do predicado
		clause("(");
	}
	
	// Define os predicados
	private void predicates_def() throws Exception
	{
		clause(":predicates");
		formula();
		clause(")");
	}
		
	// Define parte do predicado
	private void formula() throws Exception
	{
		do
		{
			clause("(");
			String nome = name();
			
			Map<Integer, VarConst> v = getVarConstTipo(true);
			domain.adicionaPredicado(nome, v);
			clause(")");
			descarta();
		} while(texto.charAt(indexTexto) == '(');
	}
	
	// Define as acoes
	private void actions_def() throws Exception
	{
		do
		{
			clause("(");
			clause(":action");
			String nome = name();
			clause(":parameters");
			clause("(");
			
			Map<Integer, VarConst> parameters = getVarConstTipo(true);
			
			acao = domain.adicionaAcao(nome, parameters);
			clause(")");
			clause(":precondition");
			acao.setPreconditions(getPredicados(pre(Predicado.POS, true)));
			clause(":effect");
			acao.setEffects(getPredicados(pre(Predicado.POS, true)));
			clause(")");
			descarta();
		} while(texto.charAt(indexTexto) != ')');
	}
	
	private Map<Integer, Predicado> getPredicados(LinkedList<Predicado> predicado)
	{
		Map<Integer, Predicado> map = new HashMap<Integer, Predicado>();
		for(Iterator<Predicado> it = predicado.iterator(); it.hasNext(); )
		{
			Predicado p = it.next();
			map.put(p.hashCode(), p);
		}
		return map;
	}
	
	protected VarConst checkVarConst(String nome) throws Exception
	{
		if(!acao.hasVariable(nome)) throw new ConstantVariableError("Variable not declare at the precondition clause!");
		return acao.getVariable(nome);
	}
}