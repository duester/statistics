package de.duester.statistica.sequence;

// арифметическая последовательность
public class SequenceArithmetic extends SequenceDouble {
	// начальное значение
	private String initialValue = "";
	// шаг приращения
	private String step = "1";

	@Override
	public void createForm() {
		form.addTextBox("txtInitialValue", initialValue);
		form.addTextBox("txtStep", step);
		form.addTextBox("txtCount", count);
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtInitialValue")))
			return false;

		if (!isDouble(form.getTextBoxValue("txtStep")))
			return false;

		return super.validateValues();
	}

	@Override
	public double[] generate() {
		initialValue = form.getTextBoxValue("txtInitialValue");
		step = form.getTextBoxValue("txtStep");
		count = form.getTextBoxValue("txtCount");

		double val = Double.parseDouble(initialValue);
		double stp = Double.parseDouble(step);
		int cnt = Integer.parseInt(count);
		double[] list = new double[cnt];
		for (int i = 0; i < cnt; i++) {
			list[i] = val;
			val += stp;
		}
		return list;
	}

}
