package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum HelloMsgs {
	 SALUT("Salut! I'm great at multitasking; I can waste time in multiple ways."),
	    HELLO("Hello! Don't byte off more than you can process at once."),
	    HALLO("Hallo! Debugging is like being a detective in a crime drama."),
	    HOLA("Hola! A programmer's favorite hangout? The foo bar, of course."),
	    BONJOUR("Bonjour! Why was the computer cold? It left its Windows open."),
	    SZIA("Szia! I've got a joke on programming but it's still loading."),
	    HEJ("Hej! I would tell you a UDP joke, but you might not get it."),
	    CIAO("Ciao! Old programmers never die, they just can't C as well."),
	    OLA("Ola! I changed my password to 'incorrect'—now I never forget!"),
	    SHALOM("Shalom! Why do programmers prefer dark mode? Because light attracts bugs.");
	private final String helloMsg;

	HelloMsgs(String helloMsg) {
		this.helloMsg = helloMsg;
	}

	public String getHelloMsg() {
		return helloMsg;
	}

	private static final List<HelloMsgs> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static HelloMsgs randomHelloMsg() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
