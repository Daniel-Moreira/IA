package Parser.Excecoes;

public class TypeError extends Exception
{
	public TypeError()
	{
		super("It's necessary to declare at least one type!");
	}
	
	public TypeError(String menssagem)
	{
		super(menssagem);
	}
}