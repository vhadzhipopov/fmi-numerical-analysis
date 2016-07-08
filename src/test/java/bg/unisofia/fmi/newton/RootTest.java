package bg.unisofia.fmi.newton;

import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.function.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by vhadzhipopov on 06.07.16.
 */
public class RootTest {
    @Test
    public void test1() {
        Function<Double, Double> poli = x -> x * x * x - x - 1;

        Root root = new Root(1.0e-4, 1.0, 10, 1.0e-6, poli.apply(2.0), poli);
        System.out.println("Result " + root.compute() + " because of " + root.getFlag());

        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
        double actual = solver.solve(10, new PolynomialFunction(new double[]{ -1.0, -1.0, 0, 1}), 1.0);
        System.out.println("Actual " + actual);
    }

    @Test
    public void test2() {
        Function<Double, Double> poli = x -> x - 0.2 * Math.sin(x) - 0.5;

        Root root = new Root(1.0e-4, 0.5, 10, 1.0e-6, poli.apply(1.0), poli);
        System.out.println("Result " + root.compute() + " because of " + root.getFlag());


        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
        double actual = solver.solve(
                10,
                FunctionUtils.add(
                        new Identity(),
                        FunctionUtils.multiply(new Constant(-0.2), (UnivariateDifferentiableFunction)new Sin()),
                        new Constant(-0.5)
                ),
                0.0);
        System.out.println("Actual " + actual);
    }

    @Test
    public void test3() {
        Function<Double, Double> poli = x -> Math.exp(-x * x) - Math.cos(x);

        Root root = new Root(1.0e-4, 1.4, 10, 1.0e-6, poli.apply(2.0), poli);
        System.out.println("Result " + root.compute() + " because of " + root.getFlag());

        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
        double actual = solver.solve(
                10,
                FunctionUtils.add(
                        FunctionUtils.compose(
                                new Exp(),
                                FunctionUtils.multiply(new Constant(-1.0), (UnivariateDifferentiableFunction) new Identity(), new Identity())
                        ),
                        FunctionUtils.multiply(new Constant(-1.0), (UnivariateDifferentiableFunction) new Cos())
                ),
                1.4);
        System.out.println("Actual " + actual);
    }
}