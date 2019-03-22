package Parser.Excecoes;

public class InvalidName extends Exception
{
	public InvalidName()
	{
		super("Invalid name!");
	}
	
	public InvalidName(String nome, int i)
	{
		super("Invalid name (\"" + nome + "\") at position " + i);
	}
}