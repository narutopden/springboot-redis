package com.javaclient.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @author Blue_Sky 7/8/21
 */
@Data
@AllArgsConstructor
public class UserPointVO {
    private String username;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPointVO that = (UserPointVO) o;
        return phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
