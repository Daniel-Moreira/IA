import Parser.*;
import Parser.Problema.*;
import Parser.Dominio.*;
import Busca.*;

import java.util.Arrays;

public class Main
{	
    public static void main(String args[])
    {
		if(args.length != 2)
		{
			System.out.println("Dois parametros sao necessarios.");
			System.out.println("Ex.: Main [arquivo de dominio] [arquivo de problema]");
			return;
		}
		
		Arquivo arquivoDominio = new Arquivo(args[0], Arquivo.LEITURA);
		Arquivo arquivoProblema = new Arquivo(args[1], Arquivo.LEITURA);
		
		Cronometro cT = new Cronometro();	
	
		String texto = arquivoDominio.leArquivo();
		
		cT.start();
			
		ReconheceDominio rD = new ReconheceDominio(texto);
		rD.reconhecer();
		Domain d = rD.domain;
		texto = arquivoProblema.leArquivo();
		ReconheceProblema rP = new ReconheceProblema(d, texto);
		rP.reconhecer();
		Problem p = rP.problem;
		d.geraInstancias(p);
		
		cT.stop();
		
		
		System.out.println("Nome do dominio: " + d.getNome());
		System.out.println("Qtd Tipos: " + d.sizeTipos());
		System.out.println("Qtd Constantes: " + d.sizeConstantes());
		System.out.println("Qtd Predicados: " + d.sizePredicados());
		System.out.println("Qtd Acoes: " + d.sizeAcoes());
		
		System.out.println("\nNome do problema: " + p.getNome());
		System.out.println("Qtd Objetos: " + p.sizeObjetos());
		System.out.println("Tempo de execucao: " + cT.getTempo() + " ms");
		
		
		cT.start();
		
		BuscaA b = new BuscaA(d, p);
		Estado e = b.busca();
		
		cT.stop();
		
		System.out.println("\nPlano:");
		print(e);
		System.out.println("\nTamanho do plano: " + e.getCost());
		System.out.println("Quantidade de estados visitados: " + b.estadosVisitados());
		System.out.println("Quantidade de estados gerados: " + b.estadosGerados());
		System.out.println("Fator medio de ramificacao: " + (double)b.estadosGerados()/b.estadosVisitados());
		System.out.println("Tempo de execucao: " + cT.getTempo() + " ms");
		
		arquivoDominio.fechaArquivo();
		arquivoProblema.fechaArquivo();
    }
	
	public static void print(Estado e)
	{
		if(e.pai != null) print(e.pai);
		if(e.acao != null) System.out.println(e.acao);
	}
}
