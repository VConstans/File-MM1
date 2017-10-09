// Auteur CONSTANS Victor

/*
/ Classe permettant de repésenter un événément d'arriver ou de départ lors
/ de la simulation
*/

public class Evt
{
	/*
	/ Un événement de type 0 est considéré comme une arrivé
	/ Un évenement de type 1 est considéré comme un départ
	*/
	public int typeEvt;
	public int numClient;
	public double date;

	public Evt(int type, int num ,double date)
	{
		this.typeEvt = type;
		this.numClient = num;
		this.date = date;
	}
}
