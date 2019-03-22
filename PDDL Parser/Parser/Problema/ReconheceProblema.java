package Parser.Problema;

import Parser.*;
import Parser.Excecoes.*;
import Parser.Dominio.Domain;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class ReconheceProblema extends Reconhecedor
{
	public Problem problem;
	
	public ReconheceProblema(Domain d, String menssagem)
	{
		domain = d;
		problem = new Problem();
		texto = menssagem;
		indexTexto = 0;
	}
	
	protected String getName()
	{
		return "problem";
	}
	
	protected void setName(String nome)
	{
		problem.setNome(nome);
	}
	
	// Expressao exp
	protected void body() throws Exception
	{
		domain_name();
		objects_def();
		init_def();
		goal_def();
	}
	
	private void domain_name() throws Exception
	{
		clause("(");
		clause(":domain");
		// Checar nome com dominio
		name();
		clause(")");
	}
	
	private void objects_def() throws Exception
	{
		clause("(");
		clause(":objects");
		problem.adicionaObjeto(getVarConstTipo(false));
		clause(")");
	}
	
	private void init_def() throws Exception
	{
		clause("(");
		clause(":init");
		do
		{
			problem.adicionaEstado(pre(Predicado.POS, false), problem.INICIAL);
			descarta();
		} while(texto.charAt(indexTexto) != ')');
		clause(")");
	}
	
	private void goal_def() throws Exception
	{
		clause("(");
		clause(":goal");
		problem.adicionaEstado(pre(Predicado.POS, false), problem.OBJETIVO);
		clause(")");
	}
	
	protected VarConst checkVarConst(String nome) throws Exception
	{
		if(!problem.hasObject(nome)) throw new ConstantVariableError("Object not declare at the objects clause!");
		return problem.getObject(nome);
	}
}