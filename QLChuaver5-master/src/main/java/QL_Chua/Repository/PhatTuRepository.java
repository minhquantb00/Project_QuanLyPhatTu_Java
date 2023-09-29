package QL_Chua.Repository;

import QL_Chua.Models.PhatTus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhatTuRepository extends JpaRepository<PhatTus, Integer> {
//    tim kiem theo ten trong danh sach phat tu
    @Query(value = "select * from phattu where ten like :name", nativeQuery = true)
    List<PhatTus> timKiemTheoTen(@Param("name") String name, Pageable pageable);
//    tim kiem theo nam sinh
    @Query(value = "select * from phattu where year(ngaysinh) = :namsinh ", nativeQuery = true)
    List<PhatTus> timKiemTheoNamSinh(@Param("namsinh") int namsinh, Pageable pageable);
//    tim kiem theo phap danh
    @Query(value = "select *  from phattu where phapdanh like :phapdanh", nativeQuery = true)
    List<PhatTus> timKiemTheoPhapDanh(@Param("phapdanh") String phapDanh, Pageable pageable);
//    tim kiem theo gioi tinh
    @Query(value = "select * from phattu where gioitinh = :gioitinh", nativeQuery = true)
    List<PhatTus> timKiemTheoGioiTinh(@Param("gioitinh") Integer gioiTinh, Pageable pageable);
//    tim kiem theo ten, gioi tinh, dia chi, trang thai
    @Query("select phattu from PhatTus phattu where (:ten is null or phattu.ten like concat('%',:ten,'%'))" +
            "and (:phapDanh is null or phattu.phapDanh like concat('%',:phapDanh,'%'))" +
            "and (:gioiTinh is null or phattu.gioiTinh = :gioiTinh)" +
            "and (:daHoanTuc is null or phattu.daHoanTuc = :daHoanTuc)")
    List<PhatTus> timKiemPhatTu(@Param("ten") String name,
                                @Param("phapDanh") String phapDanh,
                                @Param("gioiTinh") Integer gioiTinh,
                                @Param("daHoanTuc") Boolean daHoanTuc, Pageable pageable);
    boolean existsPhatTusByEmail(String email);
    boolean existsPhatTusBySoDienThoai(String soDienThoai);

    Optional<PhatTus> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);
}
