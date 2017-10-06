//Auteur CONSTANS Victor
/*
/Classe servant d'echeancier pour la simulation d'une file M/M/1.
*/

import java.util.*;

public class Ech
{
	private LinkedList<Evt> echeancier = new LinkedList<Evt>();
	private double dateDepartDernierClient=0;
	private int nbClientEnAttente = 0;
	private double lambda;
	private double mu;
	private double tempsMax;
	private Stats statEch;
	private int debugMode;

	public Ech(double lambda, double mu, double TMax,int debugMode,Stats statEch)
	{
		this.lambda = lambda;
		this.mu = mu;
		this.tempsMax = TMax;
		this.debugMode = debugMode;
		this.statEch = statEch;

		//Initialisation de l'echeancier
		echeancier.addFirst(new Evt(0,0,0));
	}

	private double creationNouvDate(double dateEvtCourant,int type)
	{
		if(type == 0)
		{
			//Si l'on cree un evenement de type arrive
			return dateEvtCourant+Utile.interTemps(this.lambda);
		}
		else
		{
			//Si l'on cree un evenement de type depart
			if(nbClientEnAttente == 0)
			{
				statEch.ajoutClientSansAttente();
				return dateEvtCourant+Utile.interTemps(this.mu);
			}
			else
			{
				statEch.ajoutClientAvecAttente();
				return (dateDepartDernierClient + Utile.interTemps(this.mu));
			}
		}
	}

	private void ajoutEvt(Evt evtCourant,int type)
	{
		double dateNouvElt = creationNouvDate(evtCourant.date,type);

		if(dateNouvElt > tempsMax && type == 0)
		{
			//Si la date de l'arrive suivante est posterieur à la duree max de la simulation,
			//on arrete de generer des evenements
			return;
		}

		int indexTmp  = 0;

		Iterator<Evt> itEch = echeancier.iterator();

		while(itEch.hasNext() && dateNouvElt > itEch.next().date)
		{
			indexTmp +=1;
		}

		if(type == 0)
		{
			//Si l'on cree un evenement de type arrive pour le client suivant
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient+1,dateNouvElt));
		}
		else
		{
			//Si l'on cree un evenement de type depart pour le client actuel
			statEch.addSejourClient(evtCourant.date,dateNouvElt,evtCourant.numClient);
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient,dateNouvElt));
		}

		debug("\t"+dateNouvElt+"\t"+indexTmp+"/"+echeancier.size()+"\n",evtCourant.date);


		if(type == 1 && dateNouvElt > dateDepartDernierClient)
		{
			dateDepartDernierClient = dateNouvElt;
		}

	}

	private void traitementEvt()
	{
		if( echeancier.size() > 0)
		{
			Evt evtCourant = echeancier.getFirst();

			if(evtCourant.typeEvt == 0)
			{
				//Creation de l'evenement d'arrive du prochain client
				ajoutEvt(evtCourant, 0);
				
				//Creation de l'evenement de depart du client actuel
				ajoutEvt(evtCourant, 1);

				statEch.addNbClientSysteme(nbClientEnAttente,evtCourant.date);

				nbClientEnAttente +=1;

				statEch.ajoutClient();
			
			}
			else
			{
				statEch.addNbClientSysteme(nbClientEnAttente,evtCourant.date);

				nbClientEnAttente -=1;
			}
			
			echeancier.removeFirst();
		}
	}

	private boolean finEch()
	{
		return (echeancier.size() == 0);
	}

	public void startSimulation()
	{
		//Boucle de simulation
		while(!finEch())
		{
			traitementEvt();
		}

	}

	private void debug(String mess,double date)
	{
		if(debugMode == 1 && date > 6000000)
		{
			System.out.println(mess);
		}
	}

}
