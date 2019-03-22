public class FuncoesH
{
	private static int funcao = 1;
	
	public static void setH(int funcao){
		FuncoesH.funcao = funcao;
	}
	
	public int h(No x){
		if(funcao == 2){
			return h2(x);
		}else if(funcao == 3)
			return h3(x);
		return h1(x);
	}
	
	public int h1(No x)
	{
		if(x.qtdOeste == 0) return 0;
		return x.oeste[0];
	}
	
	public int h2(No x)
	{
		if(x.qtdOeste == 0) return 0;
		return x.oeste[x.qtdOeste-1];
	}

	public int h3(No x)
	{
		int somaX = 0;
		for(int i = x.qtdOeste-1; i >= 0; i-=2) somaX += x.oeste[i];
		return somaX;
	}
}
