package Parser.Excecoes;

public class SymbolNotExpected extends Exception
{
	public SymbolNotExpected ()
	{
		super("Symbol expected not found!");
	}
	
	public SymbolNotExpected (String symbolExpected)
	{
		super("Symbol expected (\"" + symbolExpected + "\") not found!") ;
	}
	
	public SymbolNotExpected (String symbolFound, String symbolExpected)
	{
		super("Symbol expected (\"" + symbolExpected + "\") not found! Instead, the symbol found was \"" + symbolFound + "\"");
	}
}