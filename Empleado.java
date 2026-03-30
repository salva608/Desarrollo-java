

public class Empleado {
        public byte edadByte = 25;
    public short edadShort = 25;
    public int idEmpleado;
    public long salarioBase;
    public float bonoMensual;
    public double salarioTotal;
    public char genero;
    public boolean esActivo;
    public String nombre;

    public Empleado(int idEmpleado, long salarioBase, float bonoMensual, char genero, boolean esActivo, String nombre) {
        this.idEmpleado = idEmpleado;
        this.salarioBase = salarioBase;
        this.bonoMensual = bonoMensual;
        this.genero = genero;
        this.esActivo = esActivo;
        this.nombre = nombre;
    }
}
