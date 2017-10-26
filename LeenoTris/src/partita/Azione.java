package partita;

public class Azione {

	private int riga;
	private int colonna;
	private char simbolo;

	public Azione(int riga,int colonna,char simbolo)
	{
		//System.out.println(riga+" "+colonna+" "+simbolo);
		if (riga < 0 || riga > 3 || colonna <0 || colonna >3 || (simbolo!='X' && simbolo != 'O'))
			throw new IllegalArgumentException();
		
		this.riga=riga;
		this.colonna=colonna;
		this.simbolo=simbolo;		
	}

	public int getRiga() {
		return riga;
	}

	public int getColonna() {
		return colonna;
	}

	public char getSimbolo() {
		return simbolo;
	}

	@Override
	public String toString(){
		return "Azione: ("+riga+","+colonna+","+simbolo+")";
	}
	
}
