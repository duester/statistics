package de.duester.statistica.sequence;

import java.util.Random;

// базовый класс для распределений
public abstract class SequenceDistribution extends SequenceDouble {
	protected Random random = new Random();
}
