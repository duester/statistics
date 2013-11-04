package de.duester.statistica.caclulation;

import java.util.Arrays;

import de.duester.statistica.Resource;

// вычисление характеристик одного ряда
public class Calculation1Variable extends Calculation {
	private double min, max;
	private double mean, median;
	private double q25, q75;
	private double sum, sum2;
	private double variance, varianceN1;
	private double standardDeviation, standardDeviationN1;

	@Override
	public void calculate(double[][] data) {
		if (data.length == 0) {
			valid = false;
			return;
		}

		valid = true;

		double[] x = data[0];
		Arrays.sort(x);

		min = x[0];
		max = x[x.length - 1];

		sum = Statistics.getSum(x);
		sum2 = Statistics.getSumOfSquares(x);

		mean = Statistics.getMean(x);
		median = Statistics.getMedian(x);

		q25 = Statistics.getQ1(x);
		q75 = Statistics.getQ3(x);

		variance = Statistics.getVariance(x);
		varianceN1 = Statistics.getVarianceNMinus1(x);

		standardDeviation = Statistics.getStdDev(x);
		standardDeviationN1 = Statistics.getStdDevNMinus1(x);
	}

	@Override
	public String[] getStrings() {
		if (!valid)
			return new String[0];

		String[] list = new String[18];

		list[0] = Resource.getString("txtMinValue", min);
		list[1] = Resource.getString("txtMaxValue", max);
		list[2] = Resource.getString("txtMeanValue", mean);
		list[3] = Resource.getString("txtMedianValue", median);
		list[4] = Resource.getString("txtQuartiles");
		list[5] = "";
		list[6] = Resource.getString("txtQuartile25", q25);
		list[7] = Resource.getString("txtQuartile75", q75);
		list[8] = Resource.getString("txtSum", sum);
		list[9] = Resource.getString("txtSum2", sum2);
		list[10] = Resource.getString("txtSampleVariance");
		list[11] = "";
		list[12] = Resource.getString("txtVarianceN", variance);
		list[13] = Resource.getString("txtVarianceN-1", varianceN1);
		list[14] = Resource.getString("txtSampleStandardDeviation");
		list[15] = "";
		list[16] = Resource.getString("txtStandardDeviationN", standardDeviation);
		list[17] = Resource.getString("txtStandardDeviationN-1", standardDeviationN1);

		return list;
	}
}
