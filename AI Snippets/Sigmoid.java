public class Sigmoid implements Function
{
    public double apply(double x)
    {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}