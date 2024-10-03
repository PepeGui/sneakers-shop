package controller;

import dao.UsuarioDao;
import model.Usuario;

import javax.crypto.SecretKey;

import static controller.Criptografia.*;

public class CadastrarUsuario {
    Criptografia crip = new Criptografia();
    public Boolean CadastrarUser(Usuario pUser){
        try {
            UsuarioDao usuarioDao = new UsuarioDao();

            // Gerando uma chave AES de 128 bits
            SecretKey chaveAES = crip.gerarChaveAES(128);
            // Criptografando a senha
            String senhaCriptografada = crip.criptografar(pUser.getSenha(), chaveAES);
            pUser.setSenha(senhaCriptografada);
            pUser.setChaveAES(crip.converterChaveParaString(chaveAES));

            if(usuarioDao.VerificacaoEmail(pUser) && isCPFValido(String.valueOf(pUser.getCpf()))) {
                usuarioDao.createUsuario(pUser);
                return true;
            }
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isCPFValido(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^\\d]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int primeiroDigitoVerificador = calcularDigito(cpf.substring(0, 9), pesos);

            pesos = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            int segundoDigitoVerificador = calcularDigito(cpf.substring(0, 10), pesos);

            return cpf.equals(cpf.substring(0, 9) + primeiroDigitoVerificador + segundoDigitoVerificador);
        } catch (Exception e) {
            return false;
        }
    }

    private static int calcularDigito(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * pesos[i];
        }
        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
    }

}
