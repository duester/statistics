package de.duester.statistica;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// ��������� ������� ���������� ���� ��� ����� ���������� �������� �����
public class InputForm {
	private JPanel panel = new JPanel();

	// ��� ��� �������� �������� ��������
	private Map<String, JTextField> textFieldMap = new HashMap<>();
	private Map<String, JCheckBox> checkBoxMap = new HashMap<>();

	public InputForm() {
		panel.setLayout(new GridLayout(0, 1));
	}

	// ���������� ���������� ����
	public void addTextBox(String key, String value) {
		JLabel label = new JLabel(Resource.getString(key));
		panel.add(label);

		JTextField textField = new JTextField(value);
		panel.add(textField);
		textFieldMap.put(key, textField);
	}

	// ���������� ������
	public void addCheckBox(String key, boolean value) {
		JCheckBox checkBox = new JCheckBox(Resource.getString(key), value);
		panel.add(checkBox);
		checkBoxMap.put(key, checkBox);
	}

	public int showDialog() {
		return JOptionPane.showOptionDialog(
				Statistica.getInstance(),
				panel,
				null,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				new Object[] { Resource.getString("btnOk"),
						Resource.getString("btnCancel") }, Resource.getString("btnOk"));
	}

	public String getTextBoxValue(String key) {
		return textFieldMap.get(key).getText().trim();
	}

	public boolean getCheckBoxValue(String key) {
		return checkBoxMap.get(key).isSelected();
	}

}
