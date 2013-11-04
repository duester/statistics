package de.duester.statistica.sequence;

// биномиальное распределение
public class SequenceBinomialDistribution extends SequenceDistribution {
	// кол-во независимых экспериментов
	private String experimentsNumber = "";
	// вероятность успеха в каждом эксперименте
	private String successProbability = "";

	@Override
	public void createForm() {
		form.addTextBox("txtExperimentsNumber", experimentsNumber);
		form.addTextBox("txtSuccessProbability", successProbability);
		form.addTextBox("txtCount", count);
	}

	@Override
	public boolean validateValues() {
		if (!isDouble(form.getTextBoxValue("txtExperimentsNumber")))
			return false;

		// кол-во попыток должно быть целым и >0
		double expNum = Double.parseDouble(form.getTextBoxValue("txtExperimentsNumber"));
		if (Math.ceil(expNum) != expNum || expNum <= 0)
			return false;

		if (!isDouble(form.getTextBoxValue("txtSuccessProbability")))
			return false;

		// Вероятность успеха должна лежать между 0 и 1
		double succProb = Double.parseDouble(form
				.getTextBoxValue("txtSuccessProbability"));
		if (succProb < 0 || succProb > 1)
			return false;

		return super.validateValues();
	}

	@Override
	public double[] generate() {
		experimentsNumber = form.getTextBoxValue("txtExperimentsNumber");
		successProbability = form.getTextBoxValue("txtSuccessProbability");
		count = form.getTextBoxValue("txtCount");

		int expNum = Integer.parseInt(experimentsNumber);
		double succProb = Double.parseDouble(successProbability);
		int cnt = Integer.parseInt(count);
		double[] list = new double[cnt];
		for (int i = 0; i < cnt; i++) {
			list[i] = 0;
			for (int j = 0; j < expNum; j++)
				if (random.nextDouble() < succProb)
					list[i]++;
		}
		return list;
	}
}
