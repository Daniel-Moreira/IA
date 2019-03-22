package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;
import Parser.Predicado;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class Soma extends Heuristica
{
	public Soma(Domain d, Problem p)
	{
		domain = d;
		problem = p;
	}
	
	public int f(Estado x)
	{
		Map<Integer, Predicado> objetivo = problem.getObjetivo();
		int nivel = 1;
		int answer = 0;

		problem.removeSubObjective(objetivo, x);
		while(objetivo.size() > 0)
		{
			x = x.copy(false);
			domain.geraSucessores(x, true);
			int qtdPredicados = problem.removeSubObjective(objetivo, x);
			answer += nivel*qtdPredicados;
			nivel++;
		}
		return answer;
	}
}