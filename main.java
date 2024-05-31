import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Algoritmos.*;

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

        String[] dataBaseNames = {
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
        String[] dataBaseSearchName = {
            "1M não repetidos",
            "1M repetidos"
        };

        ArrayList<double[]> vectors = processFiles.process_Files("./DataBases");
        ArrayList<double[]> vectorsToSearch = processFiles.process_Files("./DataBasesToSearch");

        for (double[] ds : vectors) {

            // Instanciando a arvore
            SplayTree arvore = new SplayTree();
            ArrayList<Algoritmo> algoritmos = new ArrayList<>();
            
            System.out.printf("%s=> \n", dataBaseNames[vectors.indexOf(ds)]);

            long startTime = System.currentTimeMillis();
            
            for (double data : ds) {
                arvore.insert(data);
            }

            long endTime = System.currentTimeMillis();

            int quantRotations = arvore.countRotations();

            // Mude o nome da arvore conforme a instanciada
            algoritmos.add(new Algoritmo("Red Black ", (endTime - startTime) / averageTime, (quantRotations), (arvore.getHeight()), dataBaseNames[vectors.indexOf(ds)]));

            gerarCsv(algoritmos, "ResultsInsertion");

            ArrayList<Algoritmo> algoritmosSearch = new ArrayList<>();

            // Executar so se a base de dados for de 3 milhoes
            if(vectors.indexOf(ds) > 9){

                vectorsToSearch.stream().forEach(data -> {
                    
                    Long startTimeSearch = System.currentTimeMillis();
                    for (double dado : data) {
                        
                        arvore.search(dado);

                    }
                    Long endTimeSearch = System.currentTimeMillis();
                   
                    // Mude o nome da arvore conforme a instanciada
                    algoritmosSearch.add(new Algoritmo("Red Black", dataBaseNames[vectors.indexOf(ds)], dataBaseSearchName[vectorsToSearch.indexOf(data)], (endTimeSearch - startTimeSearch) / averageTime));

                });
                try {
                    gerarCsvBusca(algoritmosSearch, "ResultsSearch");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        private String nomeBaseBusca;
        private String nomeBaseDaArvore;
        private double tempo;
        private int rotations;
        private int height;
        private String dataBase;

        
        public String getNomeBaseDaArvore() {
            return nomeBaseDaArvore;
        }

        public void setNomeBaseDaArvore(String nomeBaseDaArvore) {
            this.nomeBaseDaArvore = nomeBaseDaArvore;
        }

        public String getNomeBaseBusca() {
            return nomeBaseBusca;
        }

        public void setNomeBaseBusca(String nomeBaseBusca) {
            this.nomeBaseBusca = nomeBaseBusca;
        }
        
        public int getHeight() {
            return height;
        }
    
        public void setHeight(int height) {
            this.height = height;
        }
    
        public String getDataBase() {
            return dataBase;
        }
    
        public void setDataBase(String dataBase) {
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
    
        public void setRotations(int rotations) {
            this.rotations = rotations;
        }

        public Algoritmo(String nome, double tempo, int rotations, int height, String dataBase) {
            this.nome = nome;
            this.tempo = tempo;
            this.rotations = rotations;
            this.height = height;
            this.dataBase = dataBase;
        }

        public Algoritmo(String nome, String nomeBaseDaArvore,String nomeBaseBusca, double tempo) {
            this.nome = nome;
            this.nomeBaseDaArvore = nomeBaseDaArvore;
            this.nomeBaseBusca = nomeBaseBusca;
            this.tempo = tempo;
        }
    }
    
    public static void gerarCsv(List<Algoritmo> algoritmos, String nomeArquivo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(nomeArquivo + ".csv", "rw")) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(raf.getFD()))) {
    
                if (raf.length() == 0) {
                    bufferedWriter.write("Algoritmo,Base de Dados,Tempo,Rotations,Height");
                    bufferedWriter.newLine();
                }
    
                raf.seek(raf.length());
    
                for (Algoritmo algoritmo : algoritmos) {
                    bufferedWriter.write(
                            algoritmo.getNome() + "," + algoritmo.getDataBase() + "," + (int) algoritmo.getTempo() + "," + algoritmo.getRotations() + "," + algoritmo.getHeight());
                    bufferedWriter.newLine();
                }
            }
        }
    }
    
    public static void gerarCsvBusca(List<Algoritmo> algoritmos, String nomeArquivo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(nomeArquivo + ".csv", "rw")) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(raf.getFD()))) {
    
                if (raf.length() == 0) {
                    bufferedWriter.write("Algoritmo,Base de Dados,Base de Dados Pesquisa,Tempo");
                    bufferedWriter.newLine();
                }
    
                raf.seek(raf.length());
    
                for (Algoritmo algoritmo : algoritmos) {
                    bufferedWriter.write(
                            algoritmo.getNome() + "," + algoritmo.getNomeBaseDaArvore() + "," + algoritmo.getNomeBaseBusca() + "," + algoritmo.getTempo());
                    bufferedWriter.newLine();
                }
            }
        }
    }
    
}
