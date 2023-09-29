package QL_Chua.Security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = "select * from token " +
            "where phattuid = :id and (expired = false or revoked = false )", nativeQuery = true)
    List<Token> findAllValidTokenByUser(@Param("id") Integer id);

    @Query(value = "select * from token where stoken = :token", nativeQuery = true)
    Optional<Token> findByStoken(@Param("token") String token);


}
