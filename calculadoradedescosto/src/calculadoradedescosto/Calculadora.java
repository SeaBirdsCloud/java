package calculadoradedescosto;

import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        double valor = 0;
        double desconto = 0;

        System.out.println("\nDigite o valor bruto:");
        if (teclado.hasNextDouble()) {
            valor = teclado.nextDouble();
        } else {
            System.out.println("Valor inválido. Não foi possível calcular.");
            teclado.close();
            return;
        }

        System.out.println("\nDigite o desconto (em %):");
        if (teclado.hasNextDouble()) {
            desconto = teclado.nextDouble();
        } else {
            System.out.println("Desconto inválido. Não foi possível calcular.");
            teclado.close();
            return;
        }

        double total;

        if (desconto == 0) {
            total = valor;
            System.out.println("\nTotal a pagar (sem desconto): R$ " + total);
        } else if (desconto > 0 && desconto <= 100) {
            total = (1 - (desconto / 100)) * valor;
            System.out.println("\nTotal com desconto: R$ " + total);
        } else {
            System.out.println("\nDesconto inválido. Não foi possível calcular.");
        }

        teclado.close();
    }
}
