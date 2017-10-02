public class Utile
{
	public static double tirageAleatoire()
	{
		//TODO borne
		return Math.random();
	}

	public static double loiExp(double lambda, double va)
	{
		return ((-Math.log(1-va))/lambda);
	}

	public static double interTemps(double lambda)
	{
		double va = tirageAleatoire();
		return loiExp(lambda, va);
	}
}
