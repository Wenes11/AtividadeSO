import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class SimuladorSO {
    private static final int QUANTUM = 1000;
    private static final int NUM_PROCESSOS = 10;
    private static final int[] TEMPO_EXECUCAO = {10000, 5000, 7000, 3000, 3000, 8000, 2000, 5000, 4000, 10000};
    private static Processo[] tabelaDeProcessos = new Processo[NUM_PROCESSOS];
    private static Random random = new Random();

    public static void main(String[] args) {
        // Inicializar processos
        for (int i = 0; i < NUM_PROCESSOS; i++) {
            tabelaDeProcessos[i] = new Processo(i, TEMPO_EXECUCAO[i]);
        }

        // Executar a simulação
        try (PrintWriter writer = new PrintWriter(new FileWriter("TabelaDeProcessos.txt"))) {
            for (Processo processo : tabelaDeProcessos) {
                while (processo.tempoProcessamento < TEMPO_EXECUCAO[processo.pid]) {
                    executarProcesso(processo, writer);
                }
                // Imprime o estado final do processo
                writer.println("PID " + processo.pid + " terminou a execução: " + 
                               "TP=" + processo.tempoProcessamento + ", CP=" + processo.contadorPrograma + 
                               ", NES=" + processo.numES + ", N_CPU=" + processo.numCPU);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executarProcesso(Processo processo, PrintWriter writer) {
        processo.estado = "Executando";
        int ciclosRestantes = Math.min(QUANTUM, TEMPO_EXECUCAO[processo.pid] - processo.tempoProcessamento);
        for (int i = 0; i < ciclosRestantes; i++) {
            if (random.nextInt(100) < 1) { // 1% chance de realizar E/S
                processo.estado = "Bloqueado";
                processo.numES++;
                writer.println("PID " + processo.pid + " EXECUTANDO >>> BLOQUEADO");
                if (random.nextInt(100) < 30) { // 30% chance de sair de Bloqueado para Pronto
                    processo.estado = "Pronto";
                }
                break;
            }
            processo.tempoProcessamento++;
            processo.atualizarContadorPrograma();
        }

        // Se o quantum terminou sem E/S, faz a troca de contexto
        if (processo.estado.equals("Executando")) {
            processo.numCPU++;
            processo.estado = "Pronto";
            writer.println("PID " + processo.pid + " EXECUTANDO >>> PRONTO");
        }

        // Grava os dados na tabela de processos (arquivo)
        writer.println("PID=" + processo.pid + ", TP=" + processo.tempoProcessamento + 
                       ", CP=" + processo.contadorPrograma + ", NES=" + processo.numES + 
                       ", N_CPU=" + processo.numCPU + ", Estado=" + processo.estado);
    }
}
