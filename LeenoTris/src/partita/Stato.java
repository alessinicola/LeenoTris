package partita;

import java.util.ArrayList;
import java.util.List;
import players.Giocatore;

//se una classe è Cloneable è possibile creare delle copie esatte
//di un certo oggetto facendo oggetto.clone()
//bisogna fare attenzione però che l'oggetto clonato è esattamente identico
//e la clonazione non è una deepcopy. se l'oggetto ha a sua volta dei riferimenti
//a degli altri oggetti, viene copiato il riferimento.
//per questo motivo, quando si clona uno stato è necessario sdoppiare a mano la griglia
//(altrimenti l'oggetto vecchio e il nuovo scrivono sulla stessa griglia)
public class Stato implements Cloneable {
	
	private char [][] grid;
	private int gridSymbolCount;
	private Giocatore player1;
	private Giocatore player2;
	private Giocatore currentPlayer;
	
	public Stato(Giocatore player1, Giocatore player2){
	grid = new char [3][3];			
	int i,j;
	for(i=0;i<3;i++)
		{
		for(j=0;j<3;j++)
		grid[i][j]='-';
		}
	this.player1=player1;
	this.player2=player2;
	//per convenzione ho fatto che il primo player che viene passato è anche quello che inizia
	//in alternativa si potrebbe fare un random
	this.currentPlayer=player1;
	this.gridSymbolCount=0;
	}
	
	//due metodi privati per clonare la griglia
	private char [][]getGrid(){
		return grid;
	}
	private void gridClone(char [][]old){
		this.grid= new char [3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				this.grid[i][j]=old[i][j];		
	}
	
	//questa serve a TicTacToeGame
	public Giocatore getCurrentPlayer()
	{
		return currentPlayer;
	}
	public List<Azione> getActions() {
		List<Azione> result = new ArrayList<Azione>();
		int i,j;
		char simbolo= currentPlayer.getSymbol();
		
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				if(grid[i][j]=='-')
					result.add(new Azione(i,j,simbolo));
		return result;
	}	
	public Giocatore getWinner()
		{
		int i;
		//controllo tris sulle righe
		for(i=0;i<3;i++)
			if(grid[i][0] == grid [i][1] && grid[i][1] == grid [i][2] && grid[i][1] != '-' )
				if(player1.getSymbol() == grid[i][0])
					return player1;
				else
					return player2;
								
		//controllo tris sulle colonne
		for(i=0;i<3;i++)
			if(grid[0][i] == grid [1][i] && grid[1][i] == grid [2][i] && grid[1][i] != '-' )
				if(player1.getSymbol() == grid[0][i])
					return player1;
				else
					return player2;
			
		//controllo tris sulle diagonali
		if(grid[0][0] == grid [1][1] && grid[1][1] == grid [2][2] && grid[0][0] != '-' )
			if(player1.getSymbol() == grid[0][0])
				return player1;
			else
				return player2;
			
		if(grid[0][2] == grid [1][1] && grid[1][1] == grid [2][0] && grid[0][2] != '-' )
			if(player1.getSymbol() == grid[0][2])
				return player1;
			else
				return player2;
			
			return null; // (pareggio oppure la partita non è finita)
		}
		
	public boolean isGameOver(){		
		//se c'è un vincitore o la è griglia piena (pareggio) allora la partita è finita
		return getWinner()!=null || gridSymbolCount==9;
	}
	
	private void applyAction(Azione azione)
	{
		int riga= azione.getRiga();
		int colonna= azione.getColonna();
		char simbolo= azione.getSimbolo();
		
		if(isGameOver())
			throw new IllegalStateException();
		
		//non si può scrivere sopra una casella già scritta
		if (grid[riga][colonna] != '-') 
			throw new IllegalArgumentException();
			
		grid[riga][colonna]=simbolo;
		gridSymbolCount++;
		
		//switch di turno
		if (currentPlayer==player1)
			currentPlayer=player2;
		else
			currentPlayer=player1;
		
	}
	public static Stato apply(Stato stato,Azione azione) 
	{
		//da notare la differenza con il metodo applyAction:
		//apply viene dato uno stato e un'azione e restituisce un nuovo stato
		//in cui è stata applicata l'azione rispetto al vecchio.
		//applyAction è un metodo dell'oggetto ed applica l'azione su se stesso
		
		Stato result=null;
		try {
			//lo stato che restituisce questa funzione non deve alterare quello
			//precedente. per questo motivo viene clonato
			//bisogna stare attenti al fatto che i riferimenti sono uguali
			//e quindi la griglia dell'oggetto clonato è la stessa dell'oggetto 
			//originale. per questo motivo bisogna clonare la griglia elemento per
			//elemento dopo aver fatto una new
			result = (Stato) stato.clone();
			result.gridClone(stato.getGrid());
			result.applyAction(azione);			
		} catch (CloneNotSupportedException e) {
			//questo try catch è obbligatorio.
			//dovrebbe scattare solo nel caso in cui uno si dimentica di implementare
			//cloneable
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public String toString(){		
		String result="";		
		int i,j;
		for(i=0;i<3;i++) {
			result+=i;
			for(j=0;j<3;j++)
				result+="\t"+grid[i][j] ;
			result+="\n";
			}
		
		result+="\t";
		for(i=0;i<3;i++){
		result+=i+"\t";
		}		
		return result;
	}
	
}
