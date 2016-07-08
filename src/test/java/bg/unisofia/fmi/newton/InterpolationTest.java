package bg.unisofia.fmi.newton;

import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.junit.Test;

/**
 * Created by vhadzhipopov on 23.06.16.
 */
public class InterpolationTest {
    @Test
    public void test() {
        for (int i = 2; i <= 16; i += 2) {
            calculate(i);
        }
    }

    public void calculate(int n){
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            x[i] = 10.0 * i / n - 5;
        }

        Interpolation interpol = new Interpolation(x, at -> 1 / (1 + at * at));

        double[] points = new double[101];
        for (int i = 0; i < points.length; i++) {
            points[i] = 10.0 * i - 5;
        }

        double maxError = interpol.maxError(points);
        double actualMaxError = verifyMaxError(interpol, points);

        System.out.println("At " + n + " max error is " + maxError);
        System.out.println("At " + n + " max error is " + actualMaxError + " actual");
    }

    public double verifyMaxError(Interpolation interpol, double[] points) {
        DividedDifferenceInterpolator ddi = new DividedDifferenceInterpolator();
        PolynomialFunctionNewtonForm newtonForm = ddi.interpolate(interpol.getX(), interpol.getY());
        double result = 0;
        for (double at : points) {
            result = Math.max(result, Math.abs(interpol.getFunction().apply(at) - newtonForm.value(at)));
        }
        return result;
    }
}