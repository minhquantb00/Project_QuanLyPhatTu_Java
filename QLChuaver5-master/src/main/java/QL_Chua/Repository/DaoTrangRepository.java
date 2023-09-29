package QL_Chua.Repository;

import QL_Chua.Models.DaoTrangs;
import QL_Chua.Models.PhatTus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DaoTrangRepository extends JpaRepository<DaoTrangs, Integer> {
    @Query("select daotrang from DaoTrangs daotrang where (:noiToChuc is null or daotrang.noiToChuc like concat('%', :noiToChuc, '%')) " +
            "and (:soThanhVienThamGia is null or daotrang.soThanhVienThamGia = :soThanhVienThamGia)" +
            "and (:phatTu is null or daotrang.phatTu = :phatTu)" +
            "and (:thoiGianToChuc is null or daotrang.thoiGianToChuc = :thoiGianToChuc)" +
            "and (:noiDung is null or daotrang.noiDung like concat('%', :noiDung,'%') )")
    Page<DaoTrangs> timKiemDaoTrang(@Param("noiToChuc") String noiToChuc,
                                    @Param("soThanhVienThamGia") Integer soThanhVienThamGia,
                                    @Param("phatTu") Integer phatTu,
                                    @Param("thoiGianToChuc") LocalDateTime thoiGianToChuc,
                                    @Param("noiDung") String noiDung,
                                    Pageable pageable);

    @Query(value = "select * from daotrangs where thoigiantochuc >= :thoiGianToChuc", nativeQuery = true)
    Page<DaoTrangs> phanTrangTheoNgayToChuc(@Param("thoiGianToChuc") LocalDateTime thoiGianToChuc, Pageable pageable);

    List<DaoTrangs> findByPhatTu(PhatTus phatTus);
}
