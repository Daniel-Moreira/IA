package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;

public class Max extends Heuristica
{
	public Max(Domain d, Problem p)
	{
		domain = d;
		problem = p;
	}
	
	public int f(Estado x)
	{
		int count = 0;
		while(!problem.isObjective(x))
		{
			x = x.copy(false);
			domain.geraSucessores(x, true);
			count++;
		}
		return count;
	}
	
}