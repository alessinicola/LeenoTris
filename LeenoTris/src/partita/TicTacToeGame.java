package partita;
import java.util.List;

import aima.core.search.adversarial.Game;
import players.*;

//le interfacce con il generico sono state introdotte nelle ultime versioni di 
//aima. è tutto più leggibile ed evita cast orribili (come avveniva nella 
//versione vecchia)
public class TicTacToeGame implements Game<Stato, Azione, Giocatore> {

	private Stato statoIniziale;
	private Stato currentState;
	private Giocatore []players;	
	
	public TicTacToeGame(Giocatore player1, Giocatore player2) {
		
		if(player1== null || player2==null || player1.getSymbol() == player2.getSymbol())
			throw new IllegalArgumentException();
		
		this.players= new Giocatore[2];	
		this.players[0]=player1;
		this.players[1]=player2;	
		//lo stato iniziale è quello che serve agli algoritmi per lavorare. non subisce modifiche
		this.statoIniziale= new Stato(player1,player2);			
		//il currentstate invece è lo stato attuale, è quindi quello che subisce le modifiche
		this.currentState= new Stato(player1,player2);		
	}
		
	
	public void run()
	{
		//loop di gioco.
		//finchè non c'è un game over:
		//chiedi ai giocatori di dire un'azione
		//applica l'azione
		
		Azione azione;
		System.out.println(currentState);	
		while(!currentState.isGameOver()){
			System.out.println("Tocca a "+currentState.getCurrentPlayer());
			azione= currentState.getCurrentPlayer().getAction(this);		
			currentState=Stato.apply(currentState, azione);
			System.out.println(currentState);			
		}
		
		if(currentState.getWinner()!= null)
			System.out.println("Partita terminata. Ha vinto: " + currentState.getWinner());
		else
			System.out.println("Partita terminata. Pareggio");
	}
	
	public Stato getCurrentState(){
		return currentState;
	}
	
	//da qui in poi sono tutte override. 
	//Sono metodi da implementare per far funzionare gli algoritmi di ricerca
	//per implementare la funzione euristica bisogna fare override di eval()
	//per info guardare i commenti su
	//https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/adversarial/IterativeDeepeningAlphaBetaSearch.java
	
	@Override
	public boolean isTerminal(Stato stato) {
		//per stato terminale intendono se il gioco è finito. 
		//secondo me si capisce di più game over però vabbe...
		return stato.isGameOver();
	}
	
	@Override
	public double getUtility(Stato stato, Giocatore player) {
		//se siamo in uno stato terminale (game over), questa funzione mi dice
		//se il giocatore 'player' ha vinto (1), ha perso (0),ha pareggiato (1/2)
		//(il tris è un gioco a somma zero)
		
		Giocatore winner= stato.getWinner();
		if( winner == null ) //draw			
			return 0.5;
		
		if( winner.equals(player))			
			return 1;			
		else
			return 0;
	}

	@Override
	public Giocatore getPlayer(Stato stato) {
		return stato.getCurrentPlayer();
	}
		
	@Override
	public Giocatore[] getPlayers() {
		return players;
	}
	
	@Override
	public Stato getInitialState() {
		return statoIniziale;
	}
	
	@Override
	public Stato getResult(Stato stato, Azione azione) {
		return Stato.apply(stato, azione);
	}

	@Override
	public List<Azione> getActions(Stato stato) {
		return stato.getActions();
	}
	
}
