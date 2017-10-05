import java.util.*;

public class Stats
{
	//Attribut partie theorqiue
	private double lambda;
	private double mu;
	private double ro;
	private double duree;

	//Attributs partie simulation
	private double nbClientTot=0;
	private double clientSansAttente=0;
	private double clientAvecAttente=0;
	private ArrayList<Double> tmpSejourClient = new ArrayList<Double>();
	private ArrayList<Double> nbClientSysteme = new ArrayList<Double>();
	private double derniereDate = 0;
	

	public Stats(double lambda, double mu, double duree)
	{
		this.lambda = lambda;
		this.mu = mu;
		this.duree = duree;
	}

	public void resTheorique()
	{
		System.out.println("------------------\nRESULTAT THEORIQUE\n------------------");

		if(lambda < mu)
		{
			ro = lambda/mu;

			System.out.println("lambda < mu : file stable");
			System.out.println("ro = "+ro);
			System.out.println("Nombre de clients attendus (lambda*duree) = "+(lambda*duree));
			System.out.println("Probabilite de service sans attente (1-ro) = "+(1-ro));
			System.out.println("Probabilite de file occupee (ro) = "+ro);
			System.out.println("Debit (lambda) = "+lambda);
			System.out.println("Esperance nombre de clients (ro/(1-ro))"+(ro/(1-ro)));
			System.out.println("Temps moyen de sejour (1/(mu*(1-ro)))"+(1/(mu*(1-ro))));
		}
		else
		{
			System.out.println("lambda > mu : file non stable");
		}
	}
	
	public void resSimulation()
	{

		double nbMoyenClientSysteme = 0;

		int i=0;

		int tailleNbClientSysteme = nbClientSysteme.size();

		for(i=0;i<tailleNbClientSysteme;i++)
		{
			nbMoyenClientSysteme += i*(nbClientSysteme.get(i).doubleValue());
		}

		double tmpMoyenSejour = 0;


		int tailleTmpSejourClient = tmpSejourClient.size();

		for(i=0;i<tailleTmpSejourClient;i++)
		{
			tmpMoyenSejour += tmpSejourClient.get(i).doubleValue();
		}
		
		System.out.println("\n\n\n-------------------\nRESULTAT SIMULATION\n-------------------");
		System.out.println("Nombre total de clients = "+nbClientTot);
		System.out.println("Proportion de client sans attente = "+(clientSansAttente/nbClientTot));
		System.out.println("Proportion de client avec attente = "+(clientAvecAttente/nbClientTot));
		System.out.println("Debit = "+(nbClientTot/duree));
		System.out.println("Nombre moyen de client dans le systeme = "+(nbMoyenClientSysteme/duree));
		System.out.println("Temps moyen de sejour = "+(tmpMoyenSejour/nbClientTot));

		System.out.println("\n\n\n");
	}


	public void addNbClientSysteme(int nb, double date)
	{
		double dureeCalc = date - derniereDate;

		if(nb < nbClientSysteme.size())
		{
			nbClientSysteme.set(nb,new Double(nbClientSysteme.get(nb).doubleValue()+dureeCalc));
		}
		else
		{
			nbClientSysteme.add(nb,new Double(dureeCalc));
		}

		derniereDate = date;
	}

	public void addSejourClient(double dateCourant,double nouvDate,int client)
	{
		tmpSejourClient.add(client,new Double(nouvDate-dateCourant));
	}

	public void ajoutClient()
	{
		nbClientTot +=1;
	}

	public void ajoutClientSansAttente()
	{
		clientSansAttente += 1;
	}

	public void ajoutClientAvecAttente()
	{
		clientAvecAttente += 1;
	}

}

