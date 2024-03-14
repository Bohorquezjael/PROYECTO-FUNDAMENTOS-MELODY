import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Tablas {
    public static void main(String[] args) throws IOException {
        String tabla[][] = new String[140][4];
        File archivo = new File("Paises.txt");
        FileReader lectorArchivo = new FileReader(archivo);
        BufferedReader buff = new BufferedReader(lectorArchivo);
        tabla = cargaDatosEnTabla(buff, tabla);
        menu(tabla);
    }

    private static void menu(String tabla[][]) {
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null,
                "\t\tMENU\n Selecciona una opcion a continuacion:\n 1.Imprime los paises dado un continente\n 2. Imprimir todos los paises por cada continente\n 3. Imprimir el PIB ordenado en forma descendente\n 4. Imprimir Tabla de comparacion entre BRICS y G7\n 5. Imprimir los paises con PIB debajo del promedio\n 6. Ordenar los paises en forma descendente de acuerdo al numero de habitantes\n 7. Imprimir el porcentaje del PIB por continente",
                "Menu de Opciones", 2));

        switch (opc) {
            case 1:
                String continente = JOptionPane.showInputDialog(null, "Ingresa un continente",
                        "Imprimir paises dado un continente", 1);
                ImprimirPorContinente(continente, tabla);
                break;
            case 2:
                String continentes[][] = OrganizaPorContinente(tabla);
                ImprimePorContinenteFormateado(continentes);
                break;
            case 3:
                String pibOrdenado[][] = OrdenaPIB(tabla);
                JTextArea text = new JTextArea(30, 40);
                JScrollPane scroll = new JScrollPane(text);
                for (int i = pibOrdenado.length - 1; i >= 0 ; i--) {
                    text.append(pibOrdenado[i][0] + " " + pibOrdenado[i][1] + "\n");
                }
                JOptionPane.showMessageDialog(null, scroll);
                break;
            case 4:
                //TODO
                break;
            case 5:
                double promedio = calculaPromedioPIB(tabla);
                mostrarMenoresPromedio(promedio, tabla);
                break;
            case 6:
                String paises[][] = OrdenaPaisesPorHabitantes(tabla);
                JTextArea text2 = new JTextArea(30, 40);
                JScrollPane scrol = new JScrollPane(text2);
                for (int i = 0; i < paises.length; i++) {
                    text2.append(paises[i][0] + " " + paises[i][1] + "\n");
                }
                JOptionPane.showMessageDialog(null, scrol);
                break;
            case 7:
                //TODO
                break;

            default:
                JOptionPane.showMessageDialog(null, "No existe la opcion seleccionada");
                break;
        }
    }

    private static String[][] OrdenaPaisesPorHabitantes(String[][] tabla) {
        String paisesH[][] = new String[140][2];
        for (int i = 0; i < paisesH.length; i++) {
            paisesH[i][0] = tabla[i][0];
            paisesH[i][1] = tabla[i][2];
        }
        return OrdenaArregloBurbujaEnteros(paisesH);
    }

    private static void mostrarMenoresPromedio(double promedio, String[][] tabla) {
        JTextArea text = new JTextArea(30, 40);
        JScrollPane scrooll = new JScrollPane(text);
        text.append("El promedio es: " + promedio + "\n");
        for (int i = 0; i < tabla.length; i++) {
            if(Double.parseDouble(tabla[i][3]) < promedio){
                text.append(tabla[i][0] + "\n");
            }
        }
        JOptionPane.showMessageDialog(null, scrooll, "Paises con PIB menor al promedio", 3);
    }

    private static double calculaPromedioPIB(String[][] tabla) {
        String PIBS[][] = OrdenaPIB(tabla);
        double suma = 0.0;
        for (int i = 0; i < PIBS.length; i++) {
            suma += Double.parseDouble(PIBS[i][1]);
        }
        return suma / 140.0;
    }

    private static String[][] OrdenaPIB(String[][] tabla) {
        String PIB[][] = new String[140][2];
        for (int i = 0; i < PIB.length; i++) {
            PIB[i][0] = tabla[i][0];
            PIB[i][1] = tabla[i][3];
        }
        return OrdenaArregloBurbujaDouble(PIB);
    }
    
    private static String[][] OrdenaArregloBurbujaEnteros(String[][] paisesH) {
        String tmp[][] = new String[1][2];
        for (int i = 0; i < paisesH.length; i++) {
            boolean swap = false;
            for (int j = 0; j < paisesH.length - 1; j++) {
                if (Integer.parseInt(paisesH[j][1]) > Integer.parseInt(paisesH[j + 1][1])) {
                    tmp[0][0] = paisesH[j][0];
                    tmp[0][1] = paisesH[j][1];
                    paisesH[j][1] = paisesH[j + 1][1];
                    paisesH[j + 1][1] = tmp[0][1];
                    paisesH[j][0] = paisesH[j + 1][0];
                    paisesH[j + 1][0] = tmp[0][0];
                    swap = true;
                }
            }
            if (!swap) break;
        }
        return paisesH;
    }

    private static String[][] OrdenaArregloBurbujaDouble(String[][] PIB) {
        String tmp[][] = new String[1][2];
        for (int i = 0; i < PIB.length; i++) {
            boolean swap = false;
            for (int j = 0; j < PIB.length - 1; j++) {
                if (Double.parseDouble(PIB[j][1]) > Double.parseDouble(PIB[j + 1][1])) {
                    tmp[0][0] = PIB[j][0];
                    tmp[0][1] = PIB[j][1];
                    PIB[j][1] = PIB[j + 1][1];
                    PIB[j + 1][1] = tmp[0][1];
                    PIB[j][0] = PIB[j + 1][0];
                    PIB[j + 1][0] = tmp[0][0];
                    swap = true;
                }
            }
            if (!swap)
                break;
        }
        return PIB;
    }

    private static void ImprimePorContinenteFormateado(String[][] continentes) {
        JTextArea texto = new JTextArea(60, 40);
        JScrollPane scroll = new JScrollPane(texto);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < continentes.length; j++) {
                if (i == 0 && j == 0)
                    texto.append("\t\tAmerica\n");
                else if (i == 1 && j == 0)
                    texto.append("\t\tEuropa\n");
                else if (i == 2 && j == 0)
                    texto.append("\t\tAsia\n");
                else if (i == 3 && j == 0)
                    texto.append("\t\tAfrica\n");
                else if (i == 4 && j == 0)
                    texto.append("\t\tOceania\n");
                texto.append(continentes[j][i] != null ? continentes[j][i] + "\n" : "");
            }
        }
        JOptionPane.showMessageDialog(null, scroll);
    }

    private static String[][] OrganizaPorContinente(String[][] tabla) {
        String continentes[][] = new String[140][5];
        for (int i = 0; i < 130; i++) {
            if (tabla[i][1].equalsIgnoreCase("Europa")) {
                continentes[i][1] = tabla[i][0];
            } else if (tabla[i][1].equalsIgnoreCase("America")) {
                continentes[i][0] = tabla[i][0];
            } else if (tabla[i][1].equalsIgnoreCase("Asia")) {
                continentes[i][2] = tabla[i][0];
            } else if (tabla[i][1].equalsIgnoreCase("Africa")) {
                continentes[i][3] = tabla[i][0];
            } else if (tabla[i][1].equalsIgnoreCase("Oceania")) {
                continentes[i][4] = tabla[i][0];
            }
        }
        return continentes;

    }

    private static void ImprimirPorContinente(String continente, String tabla[][]) {
        int i = 0;
        JTextArea texto = new JTextArea(20, 30);
        JScrollPane panel = new JScrollPane(texto);
        // * Posible mejora añadir una flag en caso que no haya paises de ese continente
        // en los datos y mostrarle al usuario que no se encuentra ningun pais
        while (i < 140) {
            if (tabla[i][1].equalsIgnoreCase(continente)) {
                // ! Falta añadir formato para mejorar la presentacion
                texto.append(tabla[i][0] + "\n");
                // posiblemente podriamos reutilizar parte de este metodo para la opcion
                // 2 e inclusive se puede generar un archivo de texto para posteriormente
                // solo leerlo e imprimirlo en el scrollpane
            }
            i++;
        }
        JOptionPane.showMessageDialog(null, panel);
    }

    private static String[][] cargaDatosEnTabla(BufferedReader buf, String tabla[][]) throws IOException {
        String linea = buf.readLine();
        String partes[];
        int i = 0;
        while (linea != null) {
            partes = linea.split(", ");
            tabla[i][0] = partes[0]; // Nombre del pais
            tabla[i][1] = partes[1]; // Habitantes
            tabla[i][2] = partes[2]; // Continente
            tabla[i][3] = partes[3]; // PIB
            i++;
            linea = buf.readLine();
        }
        return tabla;
    }

}
