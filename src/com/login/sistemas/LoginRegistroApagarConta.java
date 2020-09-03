package com.login.sistemas;

import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class LoginRegistroApagarConta {

	public static FileOutputStream criarRegistro;
	public static DataOutputStream gravarRegistro;
	public static FileInputStream carregarRegistro;
	public static DataInputStream lerRegistro;
	public static File arquivo;

	public static boolean registrar(String informaUsuarioNovamente) throws IOException {

		boolean verificaUsuarioExistente = false;
		String informaUsuario = informaUsuarioNovamente;

		do {
			if (informaUsuarioNovamente.equals("")) {
				informaUsuario = JOptionPane.showInputDialog(null, "Digite um usuário", "Registrar",
						JOptionPane.QUESTION_MESSAGE);
				if (informaUsuario.trim().isEmpty()) {
					informaUsuarioNovamente = JOptionPane.showInputDialog(null,
							"Erro! Seu usário não pode ficar em branco! \nTente Novamente!", "Erro!",
							JOptionPane.ERROR_MESSAGE);
					registrar(informaUsuarioNovamente);
				}
			}

			boolean bancoDeLoginsExiste = (new File("src\\\\com\\\\BancoDeLogins").isDirectory());
			if (!bancoDeLoginsExiste) {
				new File("src\\com\\BancoDeLogins").mkdir();
			}
			if (!informaUsuario.trim().isEmpty()) {
				String usuario = "src\\com\\BancoDeLogins\\" + informaUsuario + ".dat";

				if (!(new File(usuario).exists())) {
					boolean senhaEmBrancoMenorOito = false;
					do {
						String informaSenha = JOptionPane.showInputDialog(null,
								"Bem-Vindo " + informaUsuario + "\nDigite uma senha", "Criação de Senha",
								JOptionPane.QUESTION_MESSAGE);
						if (!informaSenha.trim().isEmpty()) {
							senhaEmBrancoMenorOito = false;
							if (informaSenha.length() >= 8) {
								boolean senhaNaoCoincide = false;
								do {
									String confirmaSenha = JOptionPane.showInputDialog(null, "Confirme sua senha",
											"Confirmação de Senha", JOptionPane.QUESTION_MESSAGE);

									if (informaSenha.equals(confirmaSenha)) {
										JOptionPane.showMessageDialog(
												null, "Registrado com sucesso! \nUsuário: " + informaUsuario
														+ "\nSenha: " + informaSenha,
												"Sucesso!", JOptionPane.INFORMATION_MESSAGE);
										senhaNaoCoincide = false;

										arquivo = new File(usuario);
										criarRegistro = new FileOutputStream(usuario);
										gravarRegistro = new DataOutputStream(criarRegistro);

										gravarRegistro.writeUTF(informaUsuario);
										gravarRegistro.writeUTF(informaSenha);
										return true;
									} else {
										JOptionPane.showMessageDialog(null, "Erro! Senhas não coincidem!", "Erro!",
												JOptionPane.ERROR_MESSAGE);
										senhaNaoCoincide = true;
									}
								} while (senhaNaoCoincide);
							} else {
								JOptionPane.showMessageDialog(null, "Erro! Sua senha não pode ter menos que 8 digitos!",
										"Erro!", JOptionPane.ERROR_MESSAGE);
								senhaEmBrancoMenorOito = true;
							}
						} else {
							JOptionPane.showMessageDialog(null, "Erro! Sua senha não pode ficar em branco!", "Erro!",
									JOptionPane.ERROR_MESSAGE);
							senhaEmBrancoMenorOito = true;
						}
					} while (senhaEmBrancoMenorOito);
				} else {
					informaUsuarioNovamente = JOptionPane.showInputDialog(null,
							"Usuário já existente! \nTente Novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
					registrar(informaUsuarioNovamente);
				}
			}
		} while (verificaUsuarioExistente);

		return false;
	}

	public static boolean login(String usuarioInvalido) throws HeadlessException, IOException {

		boolean usuarioInexistente = false;
		String usuarioLogin = usuarioInvalido;

		do {
			usuarioLogin = JOptionPane.showInputDialog(null, "Digite seu usuário", "Login",
					JOptionPane.QUESTION_MESSAGE);

			String arquivoUsuario = "src\\com\\BancoDeLogins\\" + usuarioLogin + ".dat";

			if ((new File(arquivoUsuario).exists())) {
				carregarRegistro = new FileInputStream(arquivoUsuario);
				lerRegistro = new DataInputStream(carregarRegistro);
				boolean senhaIncorreta = false;
				String nome = lerRegistro.readUTF();
				String senha = lerRegistro.readUTF();
				do {
					String informaSenha = JOptionPane.showInputDialog(null, "Bem-Vindo " + nome + "\nDigite sua senha",
							"Senha", JOptionPane.QUESTION_MESSAGE);

					if (informaSenha.equals(senha)) {
						JOptionPane.showMessageDialog(null, "Logado com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						return true;
					} else {
						JOptionPane.showMessageDialog(null, "Senha Incorreta!", "Erro!", JOptionPane.ERROR_MESSAGE);
						senhaIncorreta = true;
					}
				} while (senhaIncorreta);
				usuarioInexistente = false;
			} else {
				JOptionPane.showMessageDialog(null, "Erro! Usuário inexistente!", "Erro!", JOptionPane.ERROR_MESSAGE);
				usuarioInexistente = true;
			}
		} while (usuarioInexistente);

		return false;
	}

	public static void apagarConta() throws IOException {
		boolean usuarioInexistente = false;
		String usuario;

		do {
			usuario = JOptionPane.showInputDialog(null, "Digite seu usuário", "Apagar Conta",
					JOptionPane.QUESTION_MESSAGE);

			String arquivoUsuario = "src\\com\\BancoDeLogins\\" + usuario + ".dat";

			if ((new File(arquivoUsuario).exists())) {
				carregarRegistro = new FileInputStream(arquivoUsuario);
				lerRegistro = new DataInputStream(carregarRegistro);
				boolean senhaIncorreta = false;
				String nome = lerRegistro.readUTF();
				String senha = lerRegistro.readUTF();
				do {
					String informaSenha = JOptionPane.showInputDialog(null, "Para apagar sua conta \nDigite sua senha",
							"Apagar Conta", JOptionPane.QUESTION_MESSAGE);

					if (informaSenha.equals(senha)) {
						senhaIncorreta = false;
						arquivo = new File(arquivoUsuario);
						
						lerRegistro.close();
						carregarRegistro.close();
						boolean loginApagado = arquivo.delete();
						
						if(loginApagado) {
							JOptionPane.showMessageDialog(null, "Conta apagada com sucesso! ;(", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao apagar a conta!", "Erro!", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Senha Incorreta!", "Erro!", JOptionPane.ERROR_MESSAGE);
						senhaIncorreta = true;
					}
				} while (senhaIncorreta);
				usuarioInexistente = false;
			} else {
				JOptionPane.showMessageDialog(null, "Erro! Usuário inexistente!", "Erro!", JOptionPane.ERROR_MESSAGE);
				usuarioInexistente = true;
			}
		} while (usuarioInexistente);
	}
}