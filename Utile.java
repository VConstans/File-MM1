//Auteur CONSTANS Victor

/*
/ Classe regroupânt les méthodes utile pour la génération de date pour un
/ nouvel événement
*/

public class Utile
{
	/*
	/ Tirage aléatoire d'un nombre compris entre 0 et 1 exclus
	*/
	public static double tirageAleatoire()
	{
		double ran;
		do
		{
			ran = Math.random();
		}while(ran == 0 || ran == 1);

		return ran;
	}


	/*
	/ Implémentation de la loi exponentielle
	*/
	public static double loiExp(double lambda, double va)
	{
		return ((-Math.log(1-va))/lambda);
	}


	/*
	/ Génère un intertemps
	*/
	public static double interTemps(double lambda)
	{
		double va = tirageAleatoire();
		return loiExp(lambda, va);
	}
}
