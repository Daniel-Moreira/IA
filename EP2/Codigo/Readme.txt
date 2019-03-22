 _____                _ __  __      
|  __ \              | |  \/  |     
| |__) |___  __ _  __| | \  / | ___ 
|  _  // _ \/ _` |/ _` | |\/| |/ _ \
| | \ \  __/ (_| | (_| | |  | |  __/
|_|  \_\___|\__,_|\__,_|_|  |_|\___|

/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/
@autor  Daniel Augusto 	- NUSP 8122477
@autor	Igor Borges	- NUSP 8122442
@autor	Marcelo Gaioso 	- NUSP 8061963

Arquivos:

	1. Main.java     	- Classe principal responsável pela execução do programa.
	2. No.java       	- Classe que define um vertice (cliente ou deposito), inclui métodos para seu gerenciamento.
	3. Tempo.java  		- Responsável por marcar o tempo total para rodar uma parte do algoritmo.
	4. Arquivo.java 	- Responsável por realizar a leitura e escrita nos arquivos(entrada e saída).
	5. Rota.java	 	- Define uma rota (sequencia de clientes) para um veiculo com demanda suprida e custo (distancia entre os vertices).
	6. Grafo.java		- Contém a definição inicial do problema.
	7. Grasp.java	 	- Classe que implementa o algoritmo proposto.
	8. BuscaLocal.java	- Classe que implementa a estratégia de busca local do GRASP.
	9. IntraRoute.java	- Classe que implementa os operadores intra rota utilizados na busca local.
	10. InterRoute.java	- Classe que implementa os operadores inter rota utilizados na busca local.

Como Compilar e executar: 
			  
	1. Executar o arquivo .bat "Compilar - Executar". Executa todas as 9 instancias da pasta Entradas.
	2. Abrir a linha de comando e entrar com os seguintes comandos:
		  2.1. javac Main.java
		  2.2. java Main [arquivo de entrada] [arquivo de saida]