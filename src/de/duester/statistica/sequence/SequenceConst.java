package de.duester.statistica.sequence;

// последовательность из одного и того же числа
public class SequenceConst extends SequenceDouble {
	// постоянное значение
	private String constant = "";

	@Override
	public void createForm() {
		form.addTextBox("txtConstant", constant);
		form.addTextBox("txtCount", count);
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtConstant")))
			return false;

		return super.validateValues();
	}

	@Override
	public double[] generate() {
		constant = form.getTextBoxValue("txtConstant");
		count = form.getTextBoxValue("txtCount");

		double cnst = Double.parseDouble(constant);
		int cnt = Integer.parseInt(count);
		double[] list = new double[cnt];
		for (int i = 0; i < cnt; i++)
			list[i] = cnst;
		return list;
	}

}
