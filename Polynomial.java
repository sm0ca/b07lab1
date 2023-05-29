public class Polynomial {
    double[] coefficients;
    
    public Polynomial () {
        coefficients = new double[1];
        coefficients[0] = 0;
    }
    
    public Polynomial (double[] nums) {
        coefficients = new double[nums.length];
        for (int i = 0; i < nums.length; i++){
            coefficients[i] = nums[i];
        }
    }

    public Polynomial add(Polynomial p) {
        double[] sumPolynomial;
        int smallerLen = Math.min(coefficients.length, p.coefficients.length);

        if (coefficients.length == smallerLen) {
            sumPolynomial = p.coefficients;
        } else {
            sumPolynomial = coefficients;
        }

        for (int i = 0; i < smallerLen; i++) {
            sumPolynomial[i] = coefficients[i] + p.coefficients[i];
        }

        return new Polynomial(sumPolynomial);

    }
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i]*(Math.pow(x, i));
        }
        return result;
    }
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}