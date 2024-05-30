import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Algoritmos.*;

import Algoritmos.AVLTree;

public class main {
    public static void main(String[] args) throws IOException{

        int size = 1000;
       
        int[] array = generateRandomArray(size);

        // Executar o benchmark e obter o tempo de execução médio
        double totalTime = 0;
        for (int i = 0; i < 10; i++) {
            totalTime += benchmark(array);
        }

        double averageTime = totalTime / 10;
        System.out.println("Tempo médio de execução: " + averageTime + " segundos\n\n");

        String[] databaseNames = {
            "1M desordenado",
            "1M crescente",
            "1M decrescente",
            "1M desordem 10 finais",
            "1M desordem 10 iniciais",
            "2M desordenado",
            "2M crescente",
            "2M decrescente",
            "2M desordem 10 finais",
            "2M desordem 10 iniciais",
            "3M desordenado",
            "3M crescente",
            "3M decrescente",
            "3M desordem 10 finais",
            "3M desordem 10 iniciais"
        };

        ArrayList<double[]> vectors = processFiles.process_Files("./DataBases");

        // Primeira Análise
        for (double[] ds : vectors) {

            AVLTree arvore = new AVLTree();
            ArrayList<Algoritmo> algoritmos = new ArrayList<>();
            
            System.out.printf("%s=> \n", databaseNames[vectors.indexOf(ds)]);

            long startTime = System.currentTimeMillis();
            
            // Executar as operações com as árvores
            for (double data : ds) {
                arvore.insert(data);
            }

            long endTime = System.currentTimeMillis();

            int quantRotations = arvore.countRotations();

            algoritmos.add(new Algoritmo("", (endTime - startTime) / averageTime, (quantRotations), (arvore.getHeight()), databaseNames[vectors.indexOf(ds)]));

        }
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    public static int sumArray(int[] array, int index) {
        if (index >= array.length) {
            return 0;
        }
        return array[index] + sumArray(array, index + 1);
    }

    public static double benchmark(int[] array) {
        long startTime = System.nanoTime();
        int sum = sumArray(array, 0);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1000000000.0;
        System.out.println("Soma dos elementos do array: " + sum);
        return elapsedTime;
    }

    public static class Algoritmo {
        private String nome;
        private double tempo;
        private int rotations;
        private int heigth;
        private String dataBase;

        public int getHeigth() {
            return heigth;
        }

        public void setHeigth(int heigth) {
            this.heigth = heigth;
        }

        public String getDataBase() {
            return dataBase;
        }

        public void setDataBase(String dataBase) {
            this.dataBase = dataBase;
        }

        public Algoritmo(String nome, double tempo, int rotations, int heigth,String dataBase) {
            this.nome = nome;
            this.tempo = tempo;
            this.rotations = rotations;
            this.heigth = heigth;
            this.dataBase = dataBase;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getTempo() {
            return tempo;
        }

        public void setTempo(double tempo) {
            this.tempo = tempo;
        }

        public int getRotations() {
            return rotations;
        }

        public void setRotations(int Rotations) {
            this.rotations = Rotations;
        }
    }

    public static void gerarCsv(List<Algoritmo> algoritmos, String nomeArquivo) throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(nomeArquivo + ".csv", "rw")) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(raf.getFD()))) {

                if (raf.length() == 0) {
                    bufferedWriter.write("Algoritmo,Base de Dados,Tempo,Rotations");
                    bufferedWriter.newLine();
                }

                raf.seek(raf.length());

                for (Algoritmo algoritmo : algoritmos) {
                    bufferedWriter.write(
                            algoritmo.getNome() + "," + algoritmo.getDataBase() + "," + (int) algoritmo.getTempo() + "," + (int) algoritmo.getRotations());
                    bufferedWriter.newLine();
                }
            }
        }
    }
    
}
