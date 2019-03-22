 _____                _ __  __      
|  __ \              | |  \/  |     
| |__) |___  __ _  __| | \  / | ___ 
|  _  // _ \/ _` |/ _` | |\/| |/ _ \
| | \ \  __/ (_| | (_| | |  | |  __/
|_|  \_\___|\__,_|\__,_|_|  |_|\___|

/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/
@autor  Daniel Augusto 	- NUSP 8122477
@autor	Felipe Brigatto	- NUSP 7972602 
@autor	Igor Borges	- NUSP 8122442
@autor	Marcelo Gaioso 	- NUSP 8061963

Arquivos:

	1. Main.java     	- Classe principal responsável pela execução do programa
	2. No.java       	- Classe que define um nó, inclui métodos para seu gerenciamento
	3. Tempo.java  		- Responsável por marcar o tempo total para rodar uma parte do algoritmo
	4. Arquivo.java 	- Responsável por realizar a leitura e escrita nos arquivos(entrada e saída)
	5. FuncoesH.java 	- Define e distribui as heurística que deve ser rodado em cada instância
	6. BuscaStrategy.java	- (Padrão de projeto Strategy) Interface ser implementada pelas buscas
	7. Busca%	 	- Classe responsável pela determinada busca (implementa Strategy)

Como Compilar e executar: 
			  
	Abrir a linha de comando e entrar com os seguintes comandos:
		  1. javac main.java
		  2. java main

Parâmetros:
	
	1. O arquivo a ser lido deve ser do formato ".txt".
	2. O arquivo deve conter os seguintes parâmetros na segunda linha: b, h*, n , max.
	2.1 O parâmetro b é o algoritmo de busca que deve usar para rodar
	2.1.1 Os valores admitidos para as buscas são:

		1 -  Busca em Largura
		2 -  Busca em Profundidade
		3 -  Busca em Profundidade Iterativa
		4 -  Busca em Custo Uniforme
		5 -  Busca Gulosa
		6 -  Busca A*
	
	2.2 O parametro h define a heurística que irá ser executada pelo algoritmo Guloso ou A*
	2.2.1 Os valores admitidos por esse parâmetro são:
		
		1 - Heuristica 1 (custo mínimo possível)
		2 - Heuristica 2 (sem limite de pessoas para levar a lanterna)
		3 - Heuristica 3 (várias lanternas)
	
	2.3 O n corresponde ao número de pessoas que irão ser lidos no arquivo
	2.3.1 Tem que ser um numero inteiro positivo
	2.3.2 Não pode possuir um valor maior que a quantidade de elementos posteriormente apresentados
	2.3.3 Se possuir um valor menor, o resultado pode não ser o esperado, pois o programa lerá menos valores
	2.4 O valor max serve para definir um valor máximo que a busca deve ser rodada ou nível máximo para ser atingido.
	
	3. Em seguida, deve-se apresentar, por linhas ou espaços, os custos correspondentes de tamanho n, definido anteriormente
	4. A primeira linha do programa pode ser usado para colocar qualquer coisa já que ela é ignorada pelo algoritmo.

	*O parâmetro h só é necessário caso o algoritmo seja guloso(5) ou A*(6), i.e. busca informada.
	

Observações:
	
	O programa retorna "Solução não encontrada" caso não tenha sido possível achar o valor dentro do max estabelecido.
	O programa escreve o valor tanto no arquivo "saida.txt" quanto no console de onde foi executado.
	O metodo gerador de filhos utilizado em todas as buscas para criar estados filhos de um no inicial, os gera na seguinte ordem:
Pega-se o primeiro elemento do conjunto que possui a lanterna e o transfere para o lado oposto, posteriormente gera filhos com as combinacoes do primeiro elemento.
Entao, pega-se o segundo elemento e realiza as mesmas operacoes aplicadas ao primeiro. E assim sucessivamente ate que todas as combinacoes tenham sido geradas.
	
Mais detalhes sobre a implementação, vide Relatório.