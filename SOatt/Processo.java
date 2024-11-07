
class Processo {
    int pid;
    int tempoProcessamento; // TP: total de ciclos já executados
    int contadorPrograma;   // CP: próxima instrução (CP = TP + 1)
    String estado;          // EP: estado do processo
    int numES;              // NES: número de vezes que realizou E/S
    int numCPU;             // N_CPU: número de vezes que usou a CPU

    public Processo(int pid, int duracaoExecucao) {
        this.pid = pid;
        this.tempoProcessamento = 0;
        this.contadorPrograma = 1;
        this.estado = "Pronto";
        this.numES = 0;
        this.numCPU = 0;
    }

    public void atualizarContadorPrograma() {
        this.contadorPrograma = this.tempoProcessamento + 1;
    }
}
