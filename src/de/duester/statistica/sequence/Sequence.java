package de.duester.statistica.sequence;

import de.duester.statistica.CancelPressedException;
import de.duester.statistica.InputForm;

// ������� ����� ��� �������� �����
public abstract class Sequence {
	// ���������� ����� ��� ����� ����������
	protected InputForm form = new InputForm();

	// �������� ���������� �����
	public abstract void createForm();

	// �������� �������� �������� ����������
	public abstract boolean validateValues();

	// ��������� ��������� ����
	public abstract double[] generate();

	public final void check() throws CancelPressedException {
		do {
			if (form.showDialog() != 0)
				throw new CancelPressedException();
		} while (!validateValues());
	}

	public final double[] execute() throws CancelPressedException {
		createForm();
		check();
		return generate();
	}
}
