package de.duester.statistica.sequence;

import javax.swing.JOptionPane;

import de.duester.statistica.Resource;
import de.duester.statistica.Statistica;

// �������� ���, ����������� ���� �������� �� ������ ������ �����
public class SequenceExpression extends Sequence {
	// ������� ��� ����������
	private String expression = "";

	@Override
	public void createForm() {
		// TODO createForm
		JOptionPane.showMessageDialog(Statistica.getInstance(),
				Resource.getString("txtFunctionNotImplemented"));
	}

	@Override
	public boolean validateValues() {
		// TODO validateValues
		return false;
	}

	@Override
	public double[] generate() {
		// TODO generate
		return null;
	}

}
