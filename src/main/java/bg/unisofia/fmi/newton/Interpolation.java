package bg.unisofia.fmi.newton;

import java.util.function.Function;

/**
 * Created by vhadzhipopov on 23.06.16.
 */
public class Interpolation {
    private double[] x;
    private double[] y;
    private double[] d;
    private int n;
    private Function<Double, Double> function;

    public Interpolation(double[] x, Function<Double, Double> function) {
        this.x = x;
        this.n = x.length - 1;
        this.y = new double[x.length];
        this.function = function;
        for (int i = 0; i <= n; i++) {
            this.y[i] = function.apply(x[i]);
        }
        calculateDiffs();
    }

    public Interpolation(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        this.n = x.length - 1;
        calculateDiffs();
    }

    protected void calculateDiffs() {
        d = y.clone();
        for (int k = 1; k <= n; k++) {
            for (int i = 0; i <= n - k; i++) {
                d[i] = (d[i + 1] - d[i]) / (x[i + k] - x[i]);
            }
        }
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public Function<Double, Double> getFunction() {
        return function;
    }

    public double value(double at) {
        double result = d[n];
        for (int i = n - 1; i >= 0; i--) {

            result = d[i] + (at - y[i]) * result;
        }
        return result;
    }

    public double maxError(double[] points) {
        double result = 0;
        for (double at : points) {
            result = Math.max(result, Math.abs(function.apply(at) - value(at)));
        }
        return result;
    }

}
