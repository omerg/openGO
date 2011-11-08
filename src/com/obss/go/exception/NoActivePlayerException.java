package com.obss.go.exception;

public class NoActivePlayerException extends GoException {

	public NoActivePlayerException() {
		super("No active player found. Game is not active.");
	}

}
