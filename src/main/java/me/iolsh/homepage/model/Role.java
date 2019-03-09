package me.iolsh.homepage.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users"})
@ToString(exclude = {"users"})
@Entity
public class Role extends BaseEntity {

    @Column
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
