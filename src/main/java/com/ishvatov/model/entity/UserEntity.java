package com.ishvatov.model.entity;

import com.ishvatov.model.enums.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a basic user in the data base.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractEntity<String> {

    /**
     * String representation of the 'password'
     * column name in the table.
     */
    public static final String PASSWORD_FIELD = "password";

    /**
     * String representation of the 'authority'
     * column name in the table.
     */
    public static final String AUTHORITY_FIELD = "authority";

    /**
     * UserEntity login userPassword.
     */
    @Column(name = PASSWORD_FIELD)
    private String password;

    /**
     * User authority.
     */
    @Column(name = AUTHORITY_FIELD)
    @Enumerated(EnumType.STRING)
    private UserRoleType authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return getPassword().equals(that.getPassword()) && getAuthority() == that.getAuthority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPassword(), getAuthority());
    }
}
