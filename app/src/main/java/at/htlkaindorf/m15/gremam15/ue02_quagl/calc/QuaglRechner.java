package at.htlkaindorf.m15.gremam15.ue02_quagl.calc;

public class QuaglRechner {
    private final TextProvider provider;
    private double a, b, c, x1, x2;
    private int numberOfResults;
    private boolean isC;
    private double discriminant;

    public QuaglRechner(TextProvider provider, double a, double b, double c) throws Exception {
        this.a = a;
        this.b = b;
        this.c = c;
        this.provider = provider;
        isC = false;
        rechner();
    }

    private void rechner() throws Exception {
        if(a == 0) {
            throw new Exception(getMessageAMustNotBeZero());
        }
        discriminant = (b*b - 4*a*c);
        if (discriminant < 0) {
            isC = true;
            numberOfResults = 2;
            discriminant = discriminant * (-1);
            x1 = (-b + Math.sqrt(discriminant))/(2*a);
            x2 = (-b - Math.sqrt(discriminant))/(2*a);
        } else if (discriminant == 0) {
            numberOfResults = 1;
            x1 = (-b)/(2*a);
            x2 = x1;
        } else {
            numberOfResults = 2;
            x1 = (-b + Math.sqrt(discriminant))/(2*a);
            x2 = (-b - Math.sqrt(discriminant))/(2*a);
        }
    }

    public double getX1() { return x1; }

    public double getX2() {
        return x2;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    private String getMessageAMustNotBeZero() {
        return "A must not be Zero";
    }

    public boolean isC() {
        return isC;
    }

    public double getImag() { return Math.sqrt(discriminant<0 ? discriminant*(-1): discriminant); }
}
