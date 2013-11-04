package de.duester.statistica.caclulation;

import de.duester.statistica.Resource;

//вычисление характеристик двух рядов
public class Calculation2Variables extends Calculation {
	private double sum;
	private double covariance;
	private double correlation;

	@Override
	public void calculate(double[][] data) {
		if (data.length == 0) {
			valid = false;
			return;
		}

		valid = true;

		double[] x = data[0];
		double[] y = data[1];

		sum = Statistics.getSumOfProducts(x, y);

		covariance = Statistics.getCovariance(x, y);

		correlation = Statistics.getCorrelation(x, y);
	}

	@Override
	public String[] getStrings() {
		if (!valid)
			return new String[0];

		String[] list = new String[10];

		list[0] = Resource.getString("txtSumXY", sum);
		list[1] = " ";
		list[2] = Resource.getString("txtSampleCovariance");
		list[3] = "";
		list[4] = Resource.getString("txtCovariance", covariance);
		list[5] = " ";
		list[6] = Resource.getString("txtSampleCorrelationCoefficient");
		list[7] = "";
		list[8] = Resource.getString("txtCorrelationCoefficient", correlation);
		list[9] = " ";

		return list;
	}

}
