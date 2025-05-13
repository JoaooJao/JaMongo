package estacionamento;
public interface IEstacionamento {
    public abstract void cadastrarMensalista();
    public abstract void cancelarMensalista();
    public abstract void pagarMesalidade();
    public abstract void pagarHora(); //Caso ele não for mesnalista tem que pagar as horas * valor;
    public abstract void gerarExtrato(); // Registro de quantos carros, valores recebidos.
    public abstract void vagasDisponiveis();
}
