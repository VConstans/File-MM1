import java.util.*;

public class Ech
{
	private LinkedList<Evt> echeancier = new LinkedList<Evt>();
	private double dateDepartDernierClient=0;
	private int indexCourant = 0;
	private int nbClient = 0;

	public void initEch()
	{
		echeancier.addFirst(new Evt(0,0));
	}

	private double creationNouvDate(int type)
	{
		if(type == 0)
		{
			//TODO loi exp lambda
		}
		else
		{
			if(nbClient == 0)
			{
			//TODO loi exp mu	
			}
			else
			{
				return (dateDepartDernierClient + /*loi exp mu*/);
			}
		}
	}

	private void ajoutEvt(int numClient,int type)
	{
		double dateNouvElt = creationNouvDate(type);

		int indexTmp  = indexCourant;
		while(dateNouvElt > echeancier.get(indexTmp).date && indexTmp < echeancier.size())
		{
			indexTmp +=1;
		}
		echeancier.add(indexTmp,new Evt(type,numClient,date));
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
			ajoutEvt(evtCourant.numClient+1, 0);
			ajoutEvt(evtCourant.numClient, 1);

			nbClient +=1;
		}
		else
		{
			nbClient -=1;
		}
		
		indexCourant +=1;
	}
}
