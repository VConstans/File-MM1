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
	private Stats statEch;

	public Ech(double lambda, double mu, double TMax,Stats statEch)
	{
		this.lambda = lambda;
		this.mu = mu;
		this.tempsMax = TMax;
		this.statEch = statEch;

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

	private boolean ajoutEvt(Evt evtCourant,int type)
	{
		double dateNouvElt = creationNouvDate(evtCourant.date,type);

		if(dateNouvElt > tempsMax && type == 0)
		{
			return false;
		}

		int indexTmp  = 0;

		Iterator<Evt> itEch = echeancier.iterator();

		//XXX parcour avec NEXT?
//		if(dateNouvElt > 6000000)
//	System.out.println("Commence recherche ");
		while(itEch.hasNext() && dateNouvElt > itEch.next().date)
		{
			indexTmp +=1;
		}

//		if(dateNouvElt > 6000000)
//	System.out.println("termine recherche ");
		if(type == 0)
		{
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient+1,dateNouvElt));
//		if(dateNouvElt > 6000000)
//			System.out.print("Ajout arrivé client "+(evtCourant.numClient+1));
		}
		else
		{
			statEch.addSejourClient(evtCourant.date,dateNouvElt,evtCourant.numClient);
			echeancier.add(indexTmp,new Evt(type,evtCourant.numClient,dateNouvElt));
//		if(dateNouvElt > 6000000)
//			System.out.print("Ajout depart client"+(evtCourant.numClient));
		}

//		if(dateNouvElt > 6000000)
		System.out.print("\t"+dateNouvElt+"\t"+indexTmp+"/"+echeancier.size()+"\n");


		if(type == 1 && dateNouvElt > dateDepartDernierClient)
		{
//		if(dateNouvElt > 6000000)
//		System.out.println("maj date");
			dateDepartDernierClient = dateNouvElt;
		}

		return true;
	}

	public void traitementEvt()
	{
		if( echeancier.size() > 0)
		{
			Evt evtCourant = echeancier.getFirst();

			if(evtCourant.typeEvt == 0)
			{
				ajoutEvt(evtCourant, 0);
				
				ajoutEvt(evtCourant, 1);

				statEch.addNbClientSysteme(nbClient,evtCourant.date);

				nbClient +=1;

				statEch.ajoutClient();
			
			}
			else
			{
				statEch.addNbClientSysteme(nbClient,evtCourant.date);

				nbClient -=1;
			}
			
			echeancier.removeFirst();
		}
	}

	public boolean finEch()
	{
		return (echeancier.size() == 0);
	}
}
