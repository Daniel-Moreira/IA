package Parser.Excecoes;

public class PredicateError extends Exception
{
	public PredicateError()
	{
		super("Predicate not declare at the clause \":predicates\"!");
	}
	
	public PredicateError(String missingPredicate)
	{
		super("Predicate (" + missingPredicate + ") not declare at the clause \":predicates\"!");
	}
}