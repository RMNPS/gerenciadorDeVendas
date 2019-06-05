package gerenciadordevendas.conversao

import java.math.BigDecimal
import java.math.RoundingMode

class ExtensoPortugues {

    static final def SPECIAL_NAMES = [
        "",
        " mil",
        " milhão",
        " bilhão",
        " trilhão",
        " quadrilhão",
        " quintilhão"
    ];

    static final def SPECIAL_PLURAL_NAMES = [
        "",
        " mil",
        " milhões",
        " bilhões",
        " trilhões",
        " quadrilhões",
        " quintilhões"
    ];

    static final def CENTENAS = [
        "",
        " cento",
        " duzentos",
        " trezentos",
        " quatrocentos",
        " quinhentos",
        " seiscentos",
        " setecentos",
        " oitocentos",
        " novecentos"
    ];

    static final def DEZENAS = [
        "",
        " dez",
        " vinte",
        " trinta",
        " quarenta",
        " cinquenta",
        " sessenta",
        " setenta",
        " oitenta",
        " noventa"
    ];

    static final def UNIDADES = [
        "",
        " um",
        " dois",
        " três",
        " quatro",
        " cinco",
        " seis",
        " sete",
        " oito",
        " nove",
        " dez",
        " onze",
        " doze",
        " treze",
        " quatorze",
        " quinze",
        " dezesseis",
        " dezessete",
        " dezoito",
        " dezenove"
    ];

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = UNIDADES[number % 100];
            number /= 100;
        } else {
            current = UNIDADES[number % 10];
            number /= 10;
            if (current.isEmpty()) {
                current = DEZENAS[number % 10];
            } else {
                current = DEZENAS[number % 10] + " e" + current;
            }
            number /= 10;
        }
        if (number == 0) {
            return current;
        }
        if (current.isEmpty()) {
            if (number == 1) {
                return " e cem";
            } else {
                return " e" +CENTENAS[number];
            }
        }
        return CENTENAS[number] + " e" + current;
    }

    public String convert(long number) {
        if (number == 0) {
            return "zero";
        }

        String prefix = "";

        if (number < 0) {
            number = -number;
            prefix = "negativo";
        }

        String current = "";
        int place = 0;

        while (number > 0) {
            long n = number % 1000;
            if (n != 0) {
                String s = convertLessThanOneThousand((int) n);

                if (n > 1) {
                    current = s + SPECIAL_PLURAL_NAMES[place] + current;
                } else {
                    current = s + SPECIAL_NAMES[place] + current;
                }

            }
            place++;
            number /= 1000;
        };
        if (current.startsWith(" e cem")) {
            current = current.replaceFirst(" e cem", "cem");
        }
        return (prefix + current).trim();
    }

    public String convert(String decimal, boolean comCentavos) {
        System.out.print(decimal + " = ");
        String resultado = "";
        if (decimal.contains(".")) {

            String parteInteira = decimal.split("\\.")[0];
            String parteFracionaria = decimal.split("\\.")[1];

            if (parteInteira.length() > 0) {
                resultado = convert(Long.parseLong(parteInteira));
            }
            resultado += " reais";
            if (parteFracionaria.length() > 0) {
                
                String parteFracionariaConvertida = converteParteFracionaria(parteFracionaria, comCentavos);
                if (!parteFracionariaConvertida.isEmpty()) {
                    resultado += (resultado.length() > 0 ? " e " : "") + parteFracionariaConvertida;
                }

            }

        } else {
            resultado = convert(Long.parseLong(decimal));
        }
        if (resultado.isEmpty()) {
            resultado = "zero";
        }
        return resultado;
    }

    private String converteParteFracionaria(String partAfterDecimalPoint, boolean comCentavos) throws NumberFormatException {
        String resultado = "";
        BigDecimal centsArredondados = new BigDecimal("." + partAfterDecimalPoint).setScale(2, RoundingMode.HALF_EVEN);
        if (centsArredondados.compareTo(BigDecimal.ZERO) > 0) {
            partAfterDecimalPoint = centsArredondados.toPlainString().split("\\.")[1];
            int intCents = Integer.parseInt(partAfterDecimalPoint);

            resultado = convert(intCents);
            if (comCentavos) {
                resultado += (intCents > 1) ? " centavos" : " centavo";
            }
        }
        return resultado;
    }


    public String convert(String decimal) {
        return convert(decimal, false);
    }


    public String convertMoeda(String numero) {
        return convert(numero, true);
    }

    public static void main(String[] args) {
        ExtensoPortugues extensoPortugues = new ExtensoPortugues();
        System.out.println(extensoPortugues.convert("-1"));
        System.out.println(extensoPortugues.convert("-1500000"));
        System.out.println(extensoPortugues.convert("10000000"));
        System.out.println(extensoPortugues.convert("100000000"));
        System.out.println(extensoPortugues.convert("10"));
        System.out.println(extensoPortugues.convert("101"));
        System.out.println(extensoPortugues.convert("1000"));
        System.out.println(extensoPortugues.convert("10101"));
        System.out.println(extensoPortugues.convert("100101"));
        System.out.println(extensoPortugues.convert("1000000"));
        System.out.println("-------");

        System.out.println(extensoPortugues.convert("12.20"));
        System.out.println(extensoPortugues.convertMoeda("2100.99"));
        System.out.println(extensoPortugues.convert("-1000000000000000000"));
        System.out.println(extensoPortugues.convert("23450"));
        System.out.println(extensoPortugues.convert("15"));
        System.out.println(extensoPortugues.convert("15.100"));
        System.out.println(extensoPortugues.convert("1234567891.57"));
        System.out.println(extensoPortugues.convertMoeda("18457700.00"));

        System.out.println("-----");
        System.out.println(extensoPortugues.convert(".001"));
    }


    public String convert(double numero) {
        return convert(new BigDecimal(numero).toPlainString());
    }


    public String convertMoeda(double numero) {
        return convertMoeda(new BigDecimal(numero).toPlainString());
    }

}
