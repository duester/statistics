package de.duester.statistica.caclulation;

// базовый класс для вычисления характеристик числовых рядов
public abstract class Calculation {
	protected boolean valid;

	public abstract void calculate(double[][] data);

	public abstract String[] getStrings();
}
