import java.util.*;

public class Ech
{
	private LinkedList<Evt> echeancier = new LinkedList<Evt>();
	private double dateDepartDernierClient=0;
	private int indexCourant = 0;
	private int nbClient = 0;
	private double lambda;
	private double mu;
	private double tempsMax;

	public Ech(double lambda, double mu, double TMax)
	{
		this.lambda = lambda;
		this.mu = mu;
		this.tempsMax = TMax;

		echeancier.addFirst(new Evt(0,0,0));
	}

	private double creationNouvDate(double dateEvtCourant,int type)
	{
		if(type == 0)
		{
			return dateEvtCourant+Utile.interTemps(this.lambda);
		}
		else
		{
			if(nbClient == 0)
			{
				return dateEvtCourant+Utile.interTemps(mu);
			}
			else
			{
				return (dateDepartDernierClient + Utile.interTemps(mu));
			}
		}
	}

	private boolean ajoutEvt(Evt evtCourant,int type)
	{
		double dateNouvElt = creationNouvDate(evtCourant.date,type);

		if(dateNouvElt > tempsMax)
		{
			return false;
		}

		int indexTmp  = indexCourant;
		//XXX parcour avec NEXT?
		while(indexTmp < echeancier.size() && dateNouvElt > echeancier.get(indexTmp).date)
		{
			indexTmp +=1;
		}

		if(type == 0)
		{
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient+1,dateNouvElt));
			System.out.print("Ajout arrivé client "+(evtCourant.numClient+1));
		}
		else
		{
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient,dateNouvElt));
			System.out.print("Ajout depart client"+(evtCourant.numClient));
		}

		System.out.print("\t"+dateNouvElt+"\n");


		if(dateNouvElt > dateDepartDernierClient)
		{
			dateDepartDernierClient = dateNouvElt;
		}

		return true;
	}

	public void traitementEvt()
	{
		if(indexCourant < echeancier.size())
		{
			Evt evtCourant = echeancier.get(indexCourant);

			if(evtCourant.typeEvt == 0)
			{
				if(ajoutEvt(evtCourant, 0))
				{
					ajoutEvt(evtCourant, 1);

					nbClient +=1;
				}
			}
			else
			{
				nbClient -=1;
			}
			
			indexCourant +=1;
		}
	}

	public boolean finEch()
	{
		return (indexCourant==echeancier.size());
	}
}
