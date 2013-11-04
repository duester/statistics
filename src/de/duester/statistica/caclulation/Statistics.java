package de.duester.statistica.caclulation;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.statistics.BoxAndWhiskerCalculator;

// статистические функции для вычисления характеристик числовых рядов
// и при построении некоторых диаграмм
public abstract class Statistics {

	private static List<Number> getNumberList(double[] x) {
		List<Number> list = new ArrayList<>();
		for (double d : x)
			list.add(d);
		return list;
	}

	private static Number[] getNumberArray(double[] x) {
		Number[] array = new Number[x.length];
		for (int i = 0; i < x.length; i++)
			array[i] = x[i];
		return array;
	}

	public static double getSum(double[] x) {
		double sum = 0;
		for (double d : x)
			sum += d;
		return sum;
	}

	public static double getSumOfSquares(double[] x) {
		double sum2 = 0;
		for (double d : x)
			sum2 += d * d;
		return sum2;
	}

	public static double getSumOfProducts(double[] x, double[] y) {
		double sumXY = 0;
		for (int i = 0; i < x.length; i++)
			sumXY += x[i] * y[i];
		return sumXY;
	}

	public static double getMean(double[] x) {
		return org.jfree.data.statistics.Statistics.calculateMean(getNumberList(x));
	}

	public static double getMedian(double[] x) {
		return org.jfree.data.statistics.Statistics.calculateMedian(getNumberList(x),
				false);
	}

	public static double getQ1(double[] x) {
		return BoxAndWhiskerCalculator.calculateQ1(getNumberList(x));
	}

	public static double getQ3(double[] x) {
		return BoxAndWhiskerCalculator.calculateQ3(getNumberList(x));
	}

	public static double getVariance(double[] x) {
		return getVarianceNMinus1(x) * (x.length - 1) / x.length;
	}

	public static double getVarianceNMinus1(double[] x) {
		double stdDev = getStdDevNMinus1(x);
		return stdDev * stdDev;
	}

	public static double getStdDev(double[] x) {
		return Math.sqrt(getVariance(x));
	}

	public static double getStdDevNMinus1(double[] x) {
		return org.jfree.data.statistics.Statistics.getStdDev(getNumberArray(x));
	}

	public static double getCovariance(double[] x, double[] y) {
		return getSumOfProducts(x, y) / x.length - getMean(x) * getMean(y);
	}

	public static double getCorrelation(double[] x, double[] y) {
		return org.jfree.data.statistics.Statistics.getCorrelation(getNumberArray(x),
				getNumberArray(y));
	}

	public static double[] getLinearFit(double[] x, double[] y) {
		return org.jfree.data.statistics.Statistics.getLinearFit(getNumberArray(x),
				getNumberArray(y));
	}
}
