package me.iolsh.homepage.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users"})
@ToString(exclude = {"users"})
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    public enum Roles {
        USER, ADMIN
    }

    @Column
    private String role;

    @JoinColumn(name = "userId")
    private Long userId;

    public Role(User user, Roles role) {
        this.userId = user.getId();
        this.role = role.name();
    }

}
