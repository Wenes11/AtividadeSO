public class Peterson {
    private static volatile int turn;       // Variável compartilhada para controle da vez
    private static volatile boolean[] flag = new boolean[2];  // Flags para indicar a intenção de cada processo

    public static void main(String[] args) {
        Thread processo0 = new Thread(new Processo(0));
        Thread processo1 = new Thread(new Processo(1));

        processo0.start();
        processo1.start();
    }

    static class Processo implements Runnable {
        private int id;

        public Processo(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int other = 1 - id;  // Identifica o outro processo (0 para 1 e 1 para 0)

            while (true) {
                // Entrando na seção crítica
                flag[id] = true;    // Sinaliza que este processo quer entrar
                turn = other;       // Dá a vez para o outro processo
                while (flag[other] && turn == other) {
                    // Aguarda enquanto o outro processo quer entrar e tem a vez
                }

                // Seção crítica
                System.out.println("Processo " + id + " está na seção crítica.");
                try {
                    Thread.sleep(500);  // Simula operações na seção crítica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Saindo da seção crítica
                flag[id] = false;   // Indica que este processo saiu da seção crítica

                // Seção não crítica
                System.out.println("Processo " + id + " está na seção não crítica.");
                try {
                    Thread.sleep(500);  // Simula operações na seção não crítica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
