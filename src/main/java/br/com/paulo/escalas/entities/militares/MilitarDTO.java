package br.com.paulo.escalas.entities.militares;

/**
 * Dados cadastrais do militar. O acesso ao app (e-mail/role) é responsabilidade do Usuario — ver /usuarios/acesso.
 */
public record MilitarDTO(String nome, boolean stAtivo, Graduacao graduacao) {
}
