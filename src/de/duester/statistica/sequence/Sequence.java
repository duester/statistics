package de.duester.statistica.sequence;

import de.duester.statistica.CancelPressedException;
import de.duester.statistica.InputForm;

// базовый класс для числовых рядов
public abstract class Sequence {
	// диалоговая форма для ввода параметров
	protected InputForm form = new InputForm();

	// создание диалоговой формы
	public abstract void createForm();

	// проверка введённых значений параметров
	public abstract boolean validateValues();

	// генерация числового ряда
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
