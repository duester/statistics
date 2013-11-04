package de.duester.statistica.sequence;

// базовый класс для рядов с числовыми параметрами (все, кроме SequenceExpression)
public abstract class SequenceDouble extends Sequence {
	// кол-во элементов в ряду
	protected String count = "";

	protected static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtCount")))
			return false;

		// кол-во должно быть целым и >0
		double cnt = Double.parseDouble(form.getTextBoxValue("txtCount"));
		if (Math.ceil(cnt) != cnt || cnt <= 0)
			return false;

		return true;
	}

}
