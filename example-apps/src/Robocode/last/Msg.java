/*******************************************************************************
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/

// Codigo padrão disponivel no robocode. Pequena alteração na mensagem. 

package last;

/**
 * Point - a serializable point class
 */
public class Msg implements java.io.Serializable {
	public final static int FOGO = 0;
	public final static int MORREU = 1;
	public final static int FODEO = 2;

	private static final long serialVersionUID = 1L;
	private int mensagem;
	private String nome = "";
	private double x;
	private double y;

	

	public Msg(int mensagem, String name, double x2, double y2) {
		this.mensagem = mensagem;
		this.nome = name;
		this.x = x2;
		this.y = y2;
	}

	public int getMensagem() {
		return mensagem;
	}

	public String getNome() {
		return nome;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}