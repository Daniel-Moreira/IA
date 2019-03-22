package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;

public class Largura extends Heuristica
{
	public Largura(Domain d, Problem p)
	{
		domain = d;
		problem = p;
	}
	
	public int f(Estado x)
	{
		if(problem.isObjective(x)) return 0;
		return 1;
	}
}