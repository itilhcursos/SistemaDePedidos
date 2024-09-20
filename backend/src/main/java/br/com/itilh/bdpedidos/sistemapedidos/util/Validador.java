package br.com.itilh.bdpedidos.sistemapedidos.util;

import java.util.InputMismatchException;

public class Validador {

  public static boolean isCpf(String cpf) {
    cpf = cpf.replace(".", "");
    cpf = cpf.replace("-", "");

    Long.parseLong(cpf);

    if ("00000000000".equals(cpf) || "11111111111".equals(cpf)
        || "22222222222".equals(cpf) || "33333333333".equals(cpf)
        || "44444444444".equals(cpf) || "55555555555".equals(cpf)
        || "66666666666".equals(cpf) || "77777777777".equals(cpf)
        || "88888888888".equals(cpf) || "99999999999".equals(cpf))
      return false;

    int d1, d2;
    int digito1, digito2, resto;
    int digitoCPF;
    String nDigResult;

    d1 = d2 = 0;
    digito1 = digito2 = resto = 0;

    for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
      digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

      d1 = d1 + (11 - nCount) * digitoCPF;

      d2 = d2 + (12 - nCount) * digitoCPF;
    }
    ;

    resto = (d1 % 11);

    if (resto < 2)
      digito1 = 0;
    else
      digito1 = 11 - resto;

    d2 += 2 * digito1;

    resto = (d2 % 11);

    if (resto < 2)
      digito2 = 0;
    else
      digito2 = 11 - resto;

    String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

    nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

    return nDigVerific.equals(nDigResult);
  }

  public static boolean isCnpj(String cnpj) {
    cnpj = cnpj.replace(".", "");
    cnpj = cnpj.replace("-", "");
    cnpj = cnpj.replace("/", "");

    Long.parseLong(cnpj);

    if ("00000000000000".equals(cnpj) || "11111111111111".equals(cnpj)
        || "22222222222222".equals(cnpj) || "33333333333333".equals(cnpj)
        || "44444444444444".equals(cnpj) || "55555555555555".equals(cnpj)
        || "66666666666666".equals(cnpj) || "77777777777777".equals(cnpj)
        || "88888888888888".equals(cnpj) || "99999999999999".equals(cnpj)
        || cnpj.length() != 14)
      return false;

    char dig13, dig14;
    int sm, i, r, num, peso;
    try {

      sm = 0;
      peso = 2;
      for (i = 11; i >= 0; i--) {

        num = (int) (cnpj.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
          peso = 2;
      }

      r = sm % 11;
      if ((r == 0) || (r == 1))
        dig13 = '0';
      else
        dig13 = (char) ((11 - r) + 48);

      sm = 0;
      peso = 2;
      for (i = 12; i >= 0; i--) {
        num = (int) (cnpj.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
          peso = 2;
      }
      r = sm % 11;
      if ((r == 0) || (r == 1))
        dig14 = '0';
      else
        dig14 = (char) ((11 - r) + 48);

      if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
        return (true);
      else
        return (false);
    } catch (InputMismatchException erro) {
      return (false);
    }
  }

}
