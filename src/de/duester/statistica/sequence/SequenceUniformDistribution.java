package de.duester.statistica.sequence;

// равномерное распределение
public class SequenceUniformDistribution extends SequenceDistribution {
	// мин. значение
	private String minimalValue = "";
	// макс. значение
	private String maximalValue = "";
	// дискретное (целочисленные значения)?
	private boolean discrete = false;

	@Override
	public void createForm() {
		form.addTextBox("txtMinimalValue", minimalValue);
		form.addTextBox("txtMaximalValue", maximalValue);
		form.addCheckBox("txtDiscrete", discrete);
		form.addTextBox("txtCount", count);
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtMinimalValue")))
			return false;

		// мин. значение должно быть меньше макс.
		double min = Double.parseDouble(form.getTextBoxValue("txtMinimalValue"));

		if (!isDouble(form.getTextBoxValue("txtMaximalValue")))
			return false;

		double max = Double.parseDouble(form.getTextBoxValue("txtMaximalValue"));
		if (min >= max)
			return false;

		return super.validateValues();
	}

	@Override
	public double[] generate() {
		minimalValue = form.getTextBoxValue("txtMinimalValue");
		maximalValue = form.getTextBoxValue("txtMaximalValue");
		discrete = form.getCheckBoxValue("txtDiscrete");
		count = form.getTextBoxValue("txtCount");

		double min = Double.parseDouble(minimalValue);
		double max = Double.parseDouble(maximalValue);
		int cnt = Integer.parseInt(count);
		double[] list = new double[cnt];
		for (int i = 0; i < cnt; i++)
			if (discrete)
				list[i] = random.nextInt((int) (max - min)) + min;
			else
				list[i] = random.nextDouble() * (max - min) + min;
		return list;
	}
}
