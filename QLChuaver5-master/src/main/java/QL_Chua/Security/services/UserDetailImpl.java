package QL_Chua.Security.services;

import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailImpl implements UserDetails {

    private Integer idPhatTu;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities; // lưu trữ danh sách quyền hạn truy cập

    public UserDetailImpl(Integer idPhatTu, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.idPhatTu = idPhatTu;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //    chức năng tạo ra 1 đối tượng userdetail từ thông tin về người dùng lưu trữ trong hệ thống
    public static UserDetailImpl build(PhatTus phatTus) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(phatTus.getKieuThanhVien().getCode()));

        return new UserDetailImpl(
                phatTus.getPhatTuId(),
                phatTus.getEmail(),
                phatTus.getPassword(),
                authorities);
    }
}
