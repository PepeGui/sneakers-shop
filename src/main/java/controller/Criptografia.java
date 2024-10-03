package controller;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Criptografia {

    public static SecretKey gerarChaveAES(int tamanho) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(tamanho); // Tamanho da chave (128, 192 ou 256 bits)
        return keyGen.generateKey();
    }

    // Método para criptografar
    public static String criptografar(String texto, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] textoCriptografado = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoCriptografado);
    }

    // Método para descriptografar
    public static String descriptografar(String textoCriptografado, SecretKey chave) throws Exception {
        // Usando AES com modo ECB e padding PKCS5
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, chave);

        byte[] textoDescriptografado = cipher.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(textoDescriptografado, "UTF-8");
    }

    public static SecretKey converterStringParaChave(String chaveCodificada, String algoritmo) throws Exception {
        // Decodifica a string Base64 em bytes
        byte[] chaveDecodificada = Base64.getDecoder().decode(chaveCodificada);

        // Reconstrói a SecretKey a partir dos bytes decodificados
        return new SecretKeySpec(chaveDecodificada, 0, chaveDecodificada.length, algoritmo);
    }

    public static String converterChaveParaString(SecretKey chave) {
        // Converte os bytes da chave para uma string Base64
        return Base64.getEncoder().encodeToString(chave.getEncoded());
    }
    /*
    // Gerando uma chave AES de 128 bits
    SecretKey chaveAES = gerarChaveAES(128);

    // Criptografando a senha
    String senhaCriptografada = criptografar(senhaOriginal, chaveAES);

    // Descriptografando a senha
    String senhaDescriptografada = descriptografar(senhaCriptografada, chaveAES);
     */
}
