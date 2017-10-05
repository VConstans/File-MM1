public class MM1
{
	public static void main(String[] args)
	{
		if(args.length <4)
		{
			System.err.println("Usage: java MM1 <lambda> <mu> <duree> <debug[0/1]>");
			System.exit(-1);
		}
		double lambda = Double.parseDouble(args[0]);
		double mu = Double.parseDouble(args[1]);
		double duree = Double.parseDouble(args[2]);
		int debug = Integer.parseInt(args[3]);

		Stats statSimu = new Stats(lambda,mu,duree);

		Ech echeancier = new Ech(lambda,mu,duree,debug,statSimu);

		echeancier.startSimulation();

		statSimu.resTheorique();
		statSimu.resSimulation();
	}
}
