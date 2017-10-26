package players;
import partita.Azione;
import partita.TicTacToeGame;

public interface Giocatore {
	public Azione getAction(TicTacToeGame game);
	public char getSymbol();
}
