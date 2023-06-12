public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1_nz_c = {6, 5};
        int [] c1_exps = {0, 3};
        Polynomial p1 = new Polynomial(c1_nz_c, c1_exps);
        double [] c2_nz_c = {-2, -9};
        int [] c2_exps = {1, 4};
        Polynomial p2 = new Polynomial(c2_nz_c, c2_exps);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
    }
}