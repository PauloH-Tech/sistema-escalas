package br.com.paulo.entities.militares;

public enum Patente {
    SOLDADO(1),
    CABO(2),
    TERCEIRO_SARGENTO(3),
    SEGUNDO_SARGENTO(4),
    PRIMEIRO_SARGENTO(5),
    SUBTENENTE(6),
    TENENTE(7);

    private final int codigo;

    Patente(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Patente fromCodigo(int codigo) {
        for (Patente p : values()) {
            if (p.codigo == codigo) {
                return p;
            }
        }
        throw new IllegalArgumentException();
    }
}
