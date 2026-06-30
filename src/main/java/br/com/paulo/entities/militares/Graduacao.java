package br.com.paulo.entities.militares;

public enum Graduacao {
    OUTROS(0),
    SOLDADO(1),
    CABO(2),
    TERCEIRO_SARGENTO(3),
    SEGUNDO_SARGENTO(4),
    PRIMEIRO_SARGENTO(5),
    SUBTENENTE(6),
    TENENTE(7);

    private final int codigo;

    Graduacao(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Graduacao fromCodigo(int codigo) {
        for (Graduacao p : values()) {
            if (p.codigo == codigo) {
                return p;
            }
        }
        throw new IllegalArgumentException();
    }
}
