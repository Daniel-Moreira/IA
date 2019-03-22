class Tempo
{
	
	private long tempoInicio = 0;
	
	Tempo()
	{
	    this.tempoInicio = System.currentTimeMillis();  
	}
	
	long getTempo()
	{
		 return (System.currentTimeMillis()-tempoInicio);   
	}

}