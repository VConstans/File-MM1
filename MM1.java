public class MM1
{
	public static void main(String[] args)
	{
		Stats statSimu = new Stats(5,6,Integer.parseInt(args[0]));
		Ech echeancier = new Ech(5,6,Integer.parseInt(args[0]),statSimu);

		while(!echeancier.finEch())
		{
			echeancier.traitementEvt();
		}
		statSimu.resTheorique();
		statSimu.resSimulation();
	}
}
