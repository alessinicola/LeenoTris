package players;

import aima.core.search.adversarial.*;
import aima.core.search.framework.Metrics;
import partita.*;


public class GiocatoreAI  implements Giocatore{

	
	private char simbolo;
	private AdversarialSearch<Stato,Azione> search;

	public GiocatoreAI(char simbolo)
	{
		if(simbolo != 'X' && simbolo != 'O')
			throw new IllegalArgumentException();
		
		this.simbolo=simbolo;			
	}
	
	
	@Override
	public char getSymbol() {
		return simbolo;
	}


	@Override
	public Azione getAction(TicTacToeGame game) {
		
		Azione azione;	
		
		//algoritmo minmax senza ottimizzazioni. fa cagare
		//search = MinimaxSearch.createFor(game);
		//minmax con tagli alphabeta
		//search = AlphaBetaSearch.createFor(game);		
		
		
		
		//quello bellissimo, fa esplorazione in iterative deepening, ovvero una 
		//depth first con limite crescente e tagli alpha beta.
		//se esplora tutto o scatta il timeout applica minmax		
		//parametri: game, utilMin , utilMax, timeout(in secondi)
		//nb: utilMax e utilMin sono i valori max e min che può restituire getUtility()
		
		search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0, 1.0 , 5);
		//per la funzione euristica bisogna fare overriding di eval.
		
		//si scatena la bestia
		azione = search.makeDecision(game.getCurrentState());
		
		
		//le metriche ci dicono quanti nodi ha espanso e la profondità massima
		//forse è possibile anche estrarre altre informazioni
		Metrics searchMetrics = search.getMetrics();
		System.out.println(searchMetrics+"\n"+azione);		
		
		return azione;
	}
}
