package QL_Chua.Repository;

import QL_Chua.Models.DonDangKys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonDangKyRepository extends JpaRepository<DonDangKys, Integer> {
    @Query(value = "select * from dondangkys where phattuid = :phattuid", nativeQuery = true)
    List<DonDangKys> timKiemDonTheoPhatTu(@Param("phattuid") Integer phatTuId);

}
