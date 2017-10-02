public class MM1
{
	public static void main(String[] args)
	{
		Ech echeancier = new Ech(5,6,Integer.parseInt(args[0]));

		while(!echeancier.finEch())
		{
			echeancier.traitementEvt();
		}
	}
}
