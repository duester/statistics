package de.duester.statistica.sequence;

// ������� ����� ��� ����� � ��������� ����������� (���, ����� SequenceExpression)
public abstract class SequenceDouble extends Sequence {
	// ���-�� ��������� � ����
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

		// ���-�� ������ ���� ����� � >0
		double cnt = Double.parseDouble(form.getTextBoxValue("txtCount"));
		if (Math.ceil(cnt) != cnt || cnt <= 0)
			return false;

		return true;
	}

}
