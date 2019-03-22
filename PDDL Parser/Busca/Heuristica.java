package Busca;

import Parser.Dominio.Domain;
import Parser.Problema.Problem;
import Parser.Predicado;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Heuristica
{
	protected Domain domain;
	protected Problem problem;
	protected Map<Integer, Predicado> estadoInicial;
	
	public abstract int f(Estado x);	
}