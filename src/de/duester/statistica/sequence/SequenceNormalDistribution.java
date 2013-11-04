package de.duester.statistica.sequence;

// нормальное распределение
public class SequenceNormalDistribution extends SequenceDistribution {
	// мат. ожидание
	private String expectation = "0";
	// станд. отклонение
	private String standardDeviation = "1";

	@Override
	public void createForm() {
		form.addTextBox("txtExpectation", expectation);
		form.addTextBox("txtStandardDeviation", standardDeviation);
		form.addTextBox("txtCount", count);
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtExpectation")))
			return false;

		if (!isDouble(form.getTextBoxValue("txtStandardDeviation")))
			return false;

		// Стандартное отклонение должно быть >0
		double stdDev = Double.parseDouble(form.getTextBoxValue("txtStandardDeviation"));
		if (stdDev <= 0)
			return false;

		return super.validateValues();
	}

	@Override
	public double[] generate() {
		expectation = form.getTextBoxValue("txtExpectation");
		standardDeviation = form.getTextBoxValue("txtStandardDeviation");
		count = form.getTextBoxValue("txtCount");

		double exp = Double.parseDouble(expectation);
		double stdDev = Double.parseDouble(standardDeviation);
		int cnt = Integer.parseInt(count);
		double[] list = new double[cnt];
		for (int i = 0; i < cnt; i++) {
			double a, b, r;
			do {
				a = 2 * random.nextDouble() - 1;
				b = 2 * random.nextDouble() - 1;
				r = a * a + b * b;
			} while (r == 0 || r > 1);
			double sq = Math.sqrt(-2 * Math.log(r) / r);
			list[i] = exp + stdDev * a * sq;
		}
		return list;
	}
}
