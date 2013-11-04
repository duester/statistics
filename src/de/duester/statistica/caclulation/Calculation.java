package de.duester.statistica.caclulation;

// ������� ����� ��� ���������� ������������� �������� �����
public abstract class Calculation {
	protected boolean valid;

	public abstract void calculate(double[][] data);

	public abstract String[] getStrings();
}
