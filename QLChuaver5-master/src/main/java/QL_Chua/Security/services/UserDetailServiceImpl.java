package QL_Chua.Security.services;

import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.PhatTuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    PhatTuRepository phatturepo;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // tìm kiếm thông tin về người dùng trong hệ thống dựa trên tên đăng nhập
        PhatTus phatTus = phatturepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
        return UserDetailImpl.build(phatTus);
    }
}
