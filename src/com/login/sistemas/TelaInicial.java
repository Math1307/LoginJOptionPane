package com.login.sistemas;

import java.io.IOException;

import javax.swing.JOptionPane;

public class TelaInicial {

	public static void main(String[] args) throws IOException {
		Object[] opcoesLogin = { "Logar", "Registrar", "Apagar Conta", "Cancelar" };
		boolean menuInicial = false, sucesso = false;
		
		do {
		int opcaoEscolhida = JOptionPane.showOptionDialog(null, "Olá, o que você deseja?", "Olá :D",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesLogin, opcoesLogin[2]);
		
		if(opcaoEscolhida == 0) {
			sucesso = LoginRegistroApagarConta.login("");
			if(sucesso) {
				JOptionPane.showMessageDialog(null, "Bem-Vindo brodi");
			}
		} else if(opcaoEscolhida == 1) {
			sucesso = LoginRegistroApagarConta.registrar("");
			
			if(sucesso) {
				JOptionPane.showMessageDialog(null, "Bem-Vindo brodi");
			}
		} else if(opcaoEscolhida == 2) {
			LoginRegistroApagarConta.apagarConta();
		}
		
		} while(menuInicial);
	}
}
