package players;
import java.util.Scanner;

import partita.Azione;
import partita.TicTacToeGame;

public class GiocatoreUmano implements Giocatore {

	private char simbolo;
	private Scanner scanner;

	public GiocatoreUmano(char simbolo)
	{
		//autocritica: non avevo voglia di creare un oggetto anche per il simbolo ma avrei
		//dovuto farlo. denti mi avrebbe massacrato a vedere una cosa del genere.
		if(simbolo != 'X' && simbolo != 'O')
			throw new IllegalArgumentException();
		
		this.simbolo=simbolo;
		scanner = new Scanner(System.in); //oggetto che legge dallo standard input
	}
	
	@Override
	public Azione getAction(TicTacToeGame game) {
		int riga;
		int colonna;
		String line=scanner.nextLine(); //è tipo la scanf
		
		//gli elementi sono dei char, quindi mi danno il valore ascii.
		//ad esempio 1 in ascii equivale all'intero 49, 0 equivale a 48
		//se sottraggo il valore del carattere 0 ottengo il numero corrispondente
		riga=line.toCharArray()[0]-'0'; 
		colonna=line.toCharArray()[1]-'0';
		return new Azione(riga,colonna,simbolo);
	}

	@Override
	public char getSymbol() {
		return simbolo;
	}

	
}
