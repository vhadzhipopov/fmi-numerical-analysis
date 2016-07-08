package bg.unisofia.fmi.newton;

import java.util.function.Function;

/**
 * Created by vhadzhipopov on 06.07.16.
 */
public class Root {
    private double h;
    private double start;
    private int nLimit;
    private double xLimit;
    private double fLimit;
    private Function<Double, Double> f;
    private Function<Double, Double> d;
    private String flag;

    public Root(double h, double start, int nLimit, double xLimit, double fLimit, Function<Double, Double> f) {
        this.h = h;
        this.start = start;
        this.nLimit = nLimit;
        this.xLimit = xLimit;
        this.fLimit = fLimit;
        this.f = f;
        this.d = x -> (f.apply(x + h) - f.apply(x - h)) / (2 * h);
        this.flag = "Init";
    }

    public double compute() {
        double result = start;

        for (int i = 0; i < nLimit; i++) {
            if (Math.abs(f.apply(result)) > fLimit) {
                this.flag = "nLimit reached";
                return result;
            }
            if (Math.abs(d.apply(result)) < 1.0e-16) {
                this.flag = "derivative reached 0";
                return result;
            }

            double xn = result - f.apply(result) / d.apply(result);

            if (Math.abs(xn - result) < xLimit) {
                this.flag = "xLimit reached";
                return result;
            } else {
                result = xn;
            }
        }
        this.flag = "nLimit reached";

        return result;
    }

    public String getFlag() {
        return flag;
    }
}
