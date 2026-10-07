package br.com.paulo.escalas.entities.usuarios;

import br.com.paulo.escalas.entities.militares.Militar;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Nome de exibição no app (ex.: nome completo). Definido pelo militar no primeiro acesso. */
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    /** Nulo enquanto o primeiro acesso não foi concluído. */
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean ativo;

    /** Militar vinculado ao login. Obrigatório para USER, opcional para ADMIN. */
    @OneToOne
    @JoinColumn(name = "militar_id", unique = true)
    private Militar militar;

    public boolean isPrimeiroAcessoPendente() {
        return password == null;
    }

    /** Nome para exibir no app; antes do primeiro acesso usa o nome de guerra do militar. */
    public String getNomeExibicao() {
        if (nome != null) return nome;
        return militar != null ? militar.getNome() : email;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario outro)) return false;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClass());
    }

    @Override
    public String toString(){
        return "Usuario{id=%s, email=%s, role=%s}".formatted(id,email,role);
    }
}
