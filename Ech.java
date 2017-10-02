import java.util.*;

public class Ech
{
	private LinkedList<Evt> echeancier = new LinkedList<Evt>();
	private double dateDepartDernierClient=0;
	private int indexCourant = 0;
	private int nbClient = 0;
	private double lambda;
	private double mu;

	public void Ech(double lambda, double mu /*XXX temps*/)
	{
		this.lambda = lambda;
		this.mu = mu;

		echeancier.addFirst(new Evt(0,0));
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
				return (dateDepartDernierClient + /*loi exp mu*/);
			}
		}
	}

	private void ajoutEvt(Evt evtCourant,int type)
	{
		double dateNouvElt = creationNouvDate(evtCourant.date,type);

		int indexTmp  = indexCourant;
		//XXX parcour avec NEXT?
		while(dateNouvElt > echeancier.get(indexTmp).date && indexTmp < echeancier.size())
		{
			indexTmp +=1;
		}

		if(type == 0)
		{
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient+1,date));
		}
		else
		{
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient,date));
		}


		if(dateNouvElt > dateDepartDernierClient)
		{
			dateDepartDernierClient = dateNouvElt;
		}
	}

	private void traitementEvt()
	{
		Evt evtCourant = echeancier.get(indexCourant);

		if(evtCourant.type == 0)
		{
			ajoutEvt(evtCourant, 0);
			ajoutEvt(evtCourant, 1);

			nbClient +=1;
		}
		else
		{
			nbClient -=1;
		}
		
		indexCourant +=1;
	}
}
