package QL_Chua.Repository;

import QL_Chua.Models.KieuThanhViens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuThanhVienRepository extends JpaRepository<KieuThanhViens, Integer> {
}
