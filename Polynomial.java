import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] nz_coefficients;
    int[] exponents;
    
    public Polynomial () {
        nz_coefficients = null;
        exponents = null;
    }
    
    public Polynomial (double[] nz_c, int[] exps) {
        nz_coefficients = new double[nz_c.length];
        exponents = new int[exps.length];

        for (int i = 0; i < nz_c.length; i++) {
            nz_coefficients[i] = nz_c[i];
            exponents[i] = exps[i];
        }
    }

    public Polynomial(File f) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(f));
            String line = input.readLine().replace("-", " -").replace("+", " ");
            input.close();

            String[] terms = line.split(" ");
            String[] term;

            nz_coefficients = new double[terms.length];
            exponents = new int[terms.length];

            for (int i = 0; i < terms.length; i++) {
                term = terms[i].split("x");
                if (term.length == 1) {
                    nz_coefficients[i] = Double.parseDouble(term[0]);
                    exponents[i] = 0;
                } else {
                    nz_coefficients[i] = Double.parseDouble(term[0]);
                    exponents[i] = Integer.parseInt(term[1]);
                }
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void saveToFile(String fname) {
        try {
            FileWriter output = new FileWriter(fname, false);
            String message = "";
            if (exponents == null) {
                message = "0";
            } else {
                for (int i = 0; i < exponents.length; i++) {
                    if (i != 0 && nz_coefficients[i] >= 0) {
                        message += "+";
                    }
                    if (exponents[i] == 0) {
                        message += nz_coefficients[i];
                    } else {
                        message += nz_coefficients[i] + "x" + exponents[i];
                    }
                }
            }
            output.write(message);
            output.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public Polynomial add(Polynomial p) {
        if (exponents == null && p.exponents == null) {
            return new Polynomial();
        } else if (exponents == null) {
            return new Polynomial(p.nz_coefficients, p.exponents);
        } else if (p.exponents == null) {
            return new Polynomial(nz_coefficients, exponents);
        }

        int max_terms = exponents.length + p.exponents.length;
        int num_unique = exponents.length;
        boolean found = false;

        double[] temp_nz_c = new double[max_terms];
        int[] temp_exps = new int[max_terms];

        for (int i = 0; i < exponents.length; i++) {
            temp_nz_c[i] = nz_coefficients[i];
            temp_exps[i] = exponents[i];
        }
        for (int j = 0; j < p.exponents.length; j++) {
            found = false;
            for (int k = 0; k < num_unique && !found; k++) {
                if (temp_exps[k] == p.exponents[j]) {
                    found = true;
                    temp_nz_c[k] += p.nz_coefficients[j];
                }
            }
            if (!found) {
                temp_nz_c[num_unique] = p.nz_coefficients[j];
                temp_exps[num_unique] = p.exponents[j];
                num_unique++;
            }
        }
        double[] new_nz_c = new double[num_unique];
        int[] new_exps = new int[num_unique];

        for (int l = 0; l < num_unique; l++) {
            new_nz_c[l] = temp_nz_c[l];
            new_exps[l] = temp_exps[l];
        }

        Polynomial result = new Polynomial(new_nz_c, new_exps);
        return result.noZero();
    }

    public double evaluate(double x) {
        if (exponents == null) {
            return 0;
        }
        double result = 0;
        for (int i = 0; i < exponents.length; i++) {
            result += nz_coefficients[i]*(Math.pow(x, exponents[i]));
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial p) {
        if (exponents == null || p.exponents == null) {
            return new Polynomial();
        }

        Polynomial result = new Polynomial();
        Polynomial intermediate = new Polynomial();

        for (int i = 0; i < exponents.length; i++) {
            for (int j = 0; j < p.exponents.length; j++) {
                intermediate.nz_coefficients = new double[] {nz_coefficients[i] * p.nz_coefficients[j]};
                intermediate.exponents = new int[] {exponents[i] + p.exponents[j]};
                result = result.add(intermediate);
            }
        }
        return result;
    }

    public Polynomial noZero() {
        int num_nz = 0;
        for (int i = 0; i < exponents.length; i++) {
            if (nz_coefficients[i] != 0) {
                num_nz++;
            }
        }
        if (num_nz == 0) {
            return new Polynomial();
        }
        double[] new_nz_c = new double[num_nz];
        int[] new_exps = new int[num_nz];
        int curr = 0;
        for (int j = 0; j < exponents.length; j++) {
            if (nz_coefficients[j] != 0) {
                new_nz_c[curr] = nz_coefficients[j];
                new_exps[curr] = exponents[j];
                curr++;
            }
        }
        return new Polynomial(new_nz_c, new_exps);
    }
}