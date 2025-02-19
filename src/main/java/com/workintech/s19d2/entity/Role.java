package com.workintech.s19d2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "bank")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* bu değişkenin adını authority yaparak ve @Data kullandığımız için
    GrantedAuthority Interface'i içindeki getAuthority() methodunu otomatik olarak
    override etmiş olduk, bu fieldın adı başka bir şey olsaydı getAuthority() methodunu
    override etmek zorundaydık !!!
    */

    @Column(name = "authority")
    private String authority;


}
